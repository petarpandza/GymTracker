package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.*;
import hr.fer.gymtracker.util.EmailPasswordUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping({"/", ""})
    public String homepage(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);
        model.addAttribute("user", user);
        return "homepage";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("gender") int gender,
            HttpSession session,
            Model model) {

        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);

        if (!EmailPasswordUtil.checkEmailPattern(email)) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Invalid email format.");
            return "profile/profile";
        }

        if (DAOProvider.getDAO().getUserByEmail(email) != null && !user.getEmail().equals(email)) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Email already in use.");
            return "profile/profile";
        }

        user.setEmail(email);
        user.setUsername(username);
        user.setGender(gender == 0 ? null : gender);

        return "redirect:/profile";
    }

    @GetMapping("/deleteAccount")
    public String deleteAccount() {
        return "profile/delete_account";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);
        if (!EmailPasswordUtil.checkPassword(password, user.getPasswordHash(), user.getPasswordSalt())) {
            model.addAttribute("error", "Invalid password.");
            return "profile/delete_account";
        }

        DAOProvider.getDAO().deleteUser(userId);
        session.invalidate();
        return "redirect:/login";
    }

}
