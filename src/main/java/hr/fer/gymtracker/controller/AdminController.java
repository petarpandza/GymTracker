package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.Exercise;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"/", ""})
    public String adminHomepage(Model model) {
        model.addAttribute("exercises", DAOProvider.getDAO().getAllExercises());
        model.addAttribute("exerciseTypes", DAOProvider.getDAO().getAllExerciseTypes());
        return "admin/homepage";
    }

    @PostMapping("/deleteExercise")
    public String deleteExercise(
            @RequestParam("exerciseId") int exerciseId) {
        DAOProvider.getDAO().deleteExercise(exerciseId);
        return "redirect:/admin/";
    }

    @PostMapping("/createExercise")
    public String createExercise(
            @RequestParam("exerciseName") String name,
            @RequestParam("exerciseDescription") String description,
            @RequestParam("exerciseType") int exerciseTypeId) {
        DAOProvider.getDAO().save(new Exercise(name, description, DAOProvider.getDAO().getExerciseTypeById(exerciseTypeId)));
        return "redirect:/admin/";
    }

}
