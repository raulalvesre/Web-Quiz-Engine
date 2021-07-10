package engine.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import engine.domain.entities.quiz.QuizCompletion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface QuizCompletionRepository extends PagingAndSortingRepository<QuizCompletion, Long> {

    @Query(value = "SELECT * FROM quiz_completions WHERE who_completed = :userId",
            countQuery = "SELECT count(*) FROM quiz_completions WHERE who_completed = :userId",
            nativeQuery = true)
    Page<QuizCompletion> findAllUserCompletions(@Param("userId") long userId, Pageable pageable);

    @Query(value = "SELECT * FROM quiz_completions WHERE who_completed = :userId AND completed_quiz = :quizId",
            nativeQuery = true)
    List<QuizCompletion> findUserCompletion(@Param("userId") long userId, @Param("quizId") long quizId);

}