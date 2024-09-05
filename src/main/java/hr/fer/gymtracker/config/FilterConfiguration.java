package hr.fer.gymtracker.config;

import hr.fer.gymtracker.filter.AdminFilter;
import hr.fer.gymtracker.filter.ConnectionFilter;
import hr.fer.gymtracker.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for filters.
 */
@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<ConnectionFilter> loggingFilter(){
        FilterRegistrationBean<ConnectionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new ConnectionFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> userAuthFilter(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;
    }
}
