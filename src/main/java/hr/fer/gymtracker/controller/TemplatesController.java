package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.TrainingTemplate;
import hr.fer.gymtracker.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class TemplatesController {

    @GetMapping("/templates")
    public String templates(Model model, HttpSession session) {
        User user = DAOProvider.getDAO().getUserById((int) session.getAttribute("userId"));
        model.addAttribute("user", user);
        return "template/templates";
    }

    @GetMapping("/createTemplate")
    public String createTemplate(Model model) {
        model.addAttribute("exercises", DAOProvider.getDAO().getAllExercises());
        return "template/create_template";
    }

    @PostMapping("/saveTemplate")
    public String saveTemplate(@RequestParam Map<String, String> params, HttpSession session) {
        User user = DAOProvider.getDAO().getUserById((int) session.getAttribute("userId"));

        var iter = params.entrySet().iterator();
        var empty = true;
        String templateName = iter.next().getValue();
        TrainingTemplate trainingTemplate = new TrainingTemplate();
        trainingTemplate.setName(templateName);
        trainingTemplate.setUser(user);
        trainingTemplate.setExercises(new ArrayList<>());

        while (iter.hasNext()) {
            var entry = iter.next();
            if (entry.getKey().startsWith("exercise")) {
                empty = false;
                int exerciseId = Integer.parseInt(entry.getValue());
                trainingTemplate.addExercise(DAOProvider.getDAO().getExerciseById(exerciseId));
            }
        }

        if (!empty) {
            DAOProvider.getDAO().save(trainingTemplate);
        }

        return "redirect:/templates";
    }

    @PostMapping("/deleteTemplate")
    public String deleteTemplate(@RequestParam("id") int templateId, HttpSession session) {
        if (DAOProvider.getDAO().userHasTemplate((int) session.getAttribute("userId"), templateId)) {
            DAOProvider.getDAO().deleteTrainingTemplate(templateId);
        }
        return "redirect:/templates";
    }

}
