package hr.fer.gymtracker.filter;

import java.io.IOException;

import hr.fer.gymtracker.dao.jpa.JPAEMProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Filter that closes the connection to the database after the request is
 * processed.
 * Mapped to /*
 */
public class ConnectionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            JPAEMProvider.close();
        }
    }

}
