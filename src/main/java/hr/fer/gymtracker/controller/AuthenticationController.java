package hr.fer.gymtracker.controller;

import hr.fer.gymtracker.util.EmailPasswordUtil;
import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.User;
import jakarta.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        model.addAttribute("email", email);

        User user = DAOProvider.getDAO().getUserByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email or password incorrect.");
            return "auth/login";
        }
        String passwordHash = user.getPasswordHash();
        String passwordSalt = user.getPasswordSalt();
        if (!EmailPasswordUtil.checkPassword(password, passwordHash, passwordSalt)) {
            model.addAttribute("error", "Email or password incorrect.");
            return "auth/login";
        }

        session.setAttribute("userId", user.getUserId());
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("gender") int gender,
            Model model,
            HttpSession session) {

        model.addAttribute("username", username);
        model.addAttribute("email", email);

        if (!EmailPasswordUtil.checkEmailPattern(email)){
            model.addAttribute("error", "Invalid email format.");
            return "auth/register";
        }

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "auth/register";
        }

        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters long.");
            return "auth/register";
        }

        if (DAOProvider.getDAO().getUserByEmail(email) != null) {
            model.addAttribute("error", "User with that email already exists.");
            return "auth/register";
        }

        String salt = EmailPasswordUtil.generateSalt();
        String passwordHash = EmailPasswordUtil.hashPassword(password, salt);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);
        user.setIsAdmin(0);
        if (gender != 0)
            user.setGender(gender);

        DAOProvider.getDAO().save(user);

        session.setAttribute("userId", user.getUserId());

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutPost(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/updatePassword")
    public String updatePassword(HttpSession session) {
        return "profile/update_password";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model,
            HttpSession session) {
        int userId = (int) session.getAttribute("userId");
        User user = DAOProvider.getDAO().getUserById(userId);

        if (!EmailPasswordUtil.checkPassword(currentPassword, user.getPasswordHash(), user.getPasswordSalt())) {
            model.addAttribute("error", "Old password is incorrect.");
            return "profile/update_password";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            return "profile/update_password";
        }

        if (newPassword.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters long.");
            return "profile/update_password";
        }

        String salt = EmailPasswordUtil.generateSalt();
        String passwordHash = EmailPasswordUtil.hashPassword(newPassword, salt);

        user.setPasswordHash(passwordHash);
        user.setPasswordSalt(salt);

        return "redirect:/";
    }

}
