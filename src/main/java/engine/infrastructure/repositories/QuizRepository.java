package engine.infrastructure.repositories;

import engine.domain.entities.quiz.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {}
