package hr.fer.gymtracker.filter;

import hr.fer.gymtracker.dao.DAOProvider;
import hr.fer.gymtracker.models.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

/**
 * Filter that checks if user is logged in.
 * Checks if user is logged in by checking if user attribute is set in session.
 * If user is not logged in, redirects user to login page.
 * If user is an admin, redirects user to admin page.
 * Mapped to /*
 */
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Set<String> allowedPaths = Set.of("/login", "/register", "/logout", "/error");

        if (allowedPaths.contains(req.getServletPath()) ||
                req.getServletPath().startsWith("/css/") ||
                req.getServletPath().startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        if (req.getSession().getAttribute("userId") == null) {
            res.sendRedirect("/login");
            return;
        }

        User user = DAOProvider.getDAO().getUserById((int) req.getSession().getAttribute("userId"));
        if (user.getIsAdmin() == 1) {
            if (!req.getServletPath().startsWith("/admin") && !req.getServletPath().equals("/logout")) {
                res.sendRedirect("/admin");
                return;
            }
        }

        if (Set.of("/sessionDetails", "/editSession", "/updateSession", "/deleteSession").contains(req.getServletPath())) {
            int userId = (int) req.getSession().getAttribute("userId");
            int trainingSessionId = Integer.parseInt(req.getParameter("id"));
            if (!DAOProvider.getDAO().userAllowedToSeeTrainingSession(userId, trainingSessionId)) {
                res.sendRedirect("/");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
