package hr.fer.gymtracker.filter;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filter that protects domains that are only accessible by admins.
 * Checks if user is logged in and if user is admin.
 * If user is not logged in, redirects user to login page.
 * If user is not admin, redirects user to home page.
 * Mapped to /admin/*
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getSession().getAttribute("userId") == null) {
            res.sendRedirect("/login");
            return;
        }

        User user = DAOProvider.getDAO().getUserById((int) req.getSession().getAttribute("userId"));
        if (user.getIsAdmin() != 1) {
            res.sendRedirect("/");
            return;
        }

        chain.doFilter(request, response);
    }
}
