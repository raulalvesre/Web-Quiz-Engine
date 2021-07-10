package engine;

import engine.infrastructure.repositories.QuizRepository;
import engine.infrastructure.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {QuizRepository.class, UserRepository.class})
public class WebQuizEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebQuizEngineApplication.class, args);
    }

}
