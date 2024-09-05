package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.*;
import hr.fer.gymtracker.models.Set;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TrainingSessionController {

    @GetMapping("/session")
    public String session(@RequestParam(value = "templateId", required = false) Integer templateId, Model model, HttpSession session) {
        model.addAttribute("exercises", DAOProvider.getDAO().getAllExercises());
        session.setAttribute("sessionStartTime", new Date());
        if (templateId != null && DAOProvider.getDAO().userHasTemplate((int) session.getAttribute("userId"), templateId)) {
            model.addAttribute("template", DAOProvider.getDAO().getTrainingTemplateById(templateId));
        }
        return "session/session";
    }

    @PostMapping("/saveSession")
    public String saveSession(@RequestParam Map<String, String> params, HttpSession session) {
        User user = DAOProvider.getDAO().getUserById((int) session.getAttribute("userId"));
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setUser(user);

        var iter = params.entrySet().iterator();

        String sessionName = iter.next().getValue();
        trainingSession.setSessionName(sessionName);

        Date sessionStartTime = (Date) session.getAttribute("sessionStartTime");
        if (sessionStartTime == null) {
            return "redirect:/";
        }
        session.removeAttribute("sessionStartTime");
        trainingSession.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sessionStartTime));
        trainingSession.setDuration((int) (System.currentTimeMillis() - sessionStartTime.getTime()) / 1000 / 60);
        DAOProvider.getDAO().save(trainingSession);
        boolean empty = true;
        while (iter.hasNext()) {
            empty = false;
            var entry = iter.next();
            String key = entry.getKey();
            var parts = key.split("#");

            int exerciseId = Integer.parseInt(parts[0]);
            int setNumber = Integer.parseInt(parts[1]);

            double weight = Double.parseDouble(entry.getValue());
            int reps = Integer.parseInt(iter.next().getValue());
            SetType setType = DAOProvider.getDAO().getSetTypeById(Integer.parseInt(iter.next().getValue()));

            Set set = new Set();
            set.setId(new Set.SetId(setNumber, exerciseId, trainingSession.getTrainingSessionId()));
            set.setWeight(weight);
            set.setRepetitions(reps);
            set.setSetType(setType);
            set.setTrainingSession(trainingSession);
            set.setExercise(DAOProvider.getDAO().getExerciseById(exerciseId));
            DAOProvider.getDAO().save(set);
            DAOProvider.getDAO().updatePersonalBests(set.getExercise().getExerciseId(), set.getTrainingSession().getUser().getUserId());
        }
        if (empty) {
            DAOProvider.getDAO().deleteTrainingSession(trainingSession);
        }
        return "redirect:/sessions";
    }

    @GetMapping("/sessions")
    public String sessions(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);
        model.addAttribute("user", user);
        return "session/sessions";
    }

    @GetMapping("/sessionDetails")
    public String sessionDetails(@RequestParam("id") int sessionId, Model model) {
        TrainingSession trainingSession = DAOProvider.getDAO().getTrainingSessionById(sessionId);
        if (trainingSession == null) {
            return "redirect:/";
        }
        var sortedMap = sortSetsByExercise(trainingSession);
        model.addAttribute("trainingSession", trainingSession);
        model.addAttribute("sortedSets", sortedMap);
        return "session/session_details";
    }

    @GetMapping("/editSession")
    public String editSession(@RequestParam("id") int sessionId, Model model) {
        TrainingSession trainingSession = DAOProvider.getDAO().getTrainingSessionById(sessionId);
        if (trainingSession == null) {
            return "redirect:/";
        }
        var sortedMap = sortSetsByExercise(trainingSession);
        model.addAttribute("trainingSession", trainingSession);
        model.addAttribute("sortedSets", sortedMap);
        model.addAttribute("exercises", DAOProvider.getDAO().getAllExercises());
        return "session/session_edit";
    }

    @PostMapping("/updateSession")
    public String updateSession(@RequestParam Map<String, String> params) {
        var iter = params.entrySet().iterator();
        int sessionId = Integer.parseInt(iter.next().getValue());
        TrainingSession trainingSession = DAOProvider.getDAO().getTrainingSessionById(sessionId);
        if (trainingSession == null) {
            return "redirect:/";
        }
        String sessionName = iter.next().getValue();
        trainingSession.setSessionName(sessionName);

        HashSet<Set> currentSets = new HashSet<>(trainingSession.getSets());

        while (iter.hasNext()) {
            var entry = iter.next();
            String key = entry.getKey();
            var parts = key.split("#");

            int exerciseId = Integer.parseInt(parts[0]);
            int setNumber = Integer.parseInt(parts[1]);

            double weight = Double.parseDouble(entry.getValue());
            int reps = Integer.parseInt(iter.next().getValue());
            SetType setType = DAOProvider.getDAO().getSetTypeById(Integer.parseInt(iter.next().getValue()));

            Set set = DAOProvider.getDAO().getSetById(new Set.SetId(setNumber, exerciseId, sessionId));
            if (set == null) {
                set = new Set();
                set.setId(new Set.SetId(setNumber, exerciseId, sessionId));
                set.setTrainingSession(trainingSession);
                set.setExercise(DAOProvider.getDAO().getExerciseById(exerciseId));
            }
            set.setWeight(weight);
            set.setRepetitions(reps);
            set.setSetType(setType);

            currentSets.remove(set);

            DAOProvider.getDAO().save(set);
            DAOProvider.getDAO().updatePersonalBests(set.getExercise().getExerciseId(), set.getTrainingSession().getUser().getUserId());
        }

        trainingSession.getSets().removeAll(currentSets);
        DAOProvider.getDAO().deleteSets(currentSets);

        if (trainingSession.getSets().isEmpty()) {
            DAOProvider.getDAO().deleteTrainingSession(trainingSession);
        }

        return "redirect:/sessions";
    }

    @PostMapping("/deleteSession")
    public String deleteSession(@RequestParam("id") int sessionId) {
        TrainingSession trainingSession = DAOProvider.getDAO().getTrainingSessionById(sessionId);
        if (trainingSession == null) {
            return "redirect:/";
        }
        DAOProvider.getDAO().deleteTrainingSession(trainingSession);
        return "redirect:/sessions";
    }

    private static LinkedHashMap<Exercise, List<Set>> sortSetsByExercise(TrainingSession trainingSession) {
        LinkedHashMap<Exercise, List<Set>> sortedMap = new LinkedHashMap<>();
        trainingSession.getSets().forEach(set -> {
            List<Set> sets = sortedMap.computeIfAbsent(set.getExercise(), _ -> new ArrayList<>());
            sets.add(set);
        });
        return sortedMap;
    }

}
