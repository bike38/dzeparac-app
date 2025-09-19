package rs.bike.dzeparac.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "rs.bike.dzeparac.controller.web",
        "rs.bike.dzeparac.controller.api",
        "rs.bike.dzeparac.repository",
        "rs.bike.dzeparac.model",
        "rs.bike.dzeparac.service"
})
@EnableJpaRepositories(basePackages = "rs.bike.dzeparac.repository")
@EntityScan(basePackages = "rs.bike.dzeparac.model")
public class AllowanceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllowanceAppApplication.class, args);
    }
}