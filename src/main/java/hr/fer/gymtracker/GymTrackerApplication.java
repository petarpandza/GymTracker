package hr.fer.gymtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GymTrackerApplication {

    public static void main(String[] args) {

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading sqlite driver.");
            throw new RuntimeException(e);
        }

        SpringApplication.run(GymTrackerApplication.class, args);
    }

}
