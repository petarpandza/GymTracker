package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.Exercise;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExercisesController {

    @GetMapping("/exercises")
    public String exercises(Model model) {
        model.addAttribute("allExercises", DAOProvider.getDAO().getAllExercises());
        model.addAttribute("sortedExercises", DAOProvider.getDAO().getSortedExercises());
        return "exercise/exercises";
    }

    @GetMapping("/exercise")
    public String exercise(@RequestParam("id") int id, Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        Exercise exercise = DAOProvider.getDAO().getExerciseById(id);
        if (exercise == null) {
            return "redirect:/exercises";
        }
        model.addAttribute("exercise", exercise);
        model.addAttribute("personalBests", DAOProvider.getDAO().getPersonalBests(id, userId));
        return "exercise/exercise_details";
    }

}
