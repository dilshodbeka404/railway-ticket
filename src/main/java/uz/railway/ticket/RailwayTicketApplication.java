package uz.railway.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RailwayTicketApplication {

    static void main(String[] args) {
        SpringApplication.run(RailwayTicketApplication.class, args);
    }

}
