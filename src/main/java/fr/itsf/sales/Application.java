package fr.itsf.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Class of the project used to launch spring boot
 */
@SpringBootApplication
public class Application {

    /**
     * Main Method of the project used to launch spring boot
     *
     * @param args arguments passed to application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
