package engine.application.interfaces;

import engine.application.dto.*;
import engine.application.exceptions.QuizNotFoundException;
import engine.application.exceptions.UserDoesNotOwnQuizException;
import engine.application.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface QuizServiceInterface {

    Page<QuizRespDTO> getQuizPage(Integer pageNumber);

    QuizRespDTO getQuiz(long id) throws QuizNotFoundException;

    QuizRespDTO createQuiz(QuizReqDTO quizReq, Principal principal) throws UserNotFoundException;
    QuizRespDTO updateQuiz(long quizId, QuizReqDTO quizReq, Principal principal) throws UserDoesNotOwnQuizException, QuizNotFoundException, UserNotFoundException;

    FeedbackDTO solveQuiz(Long id, UserQuizAnswerReqDTO answer, Principal principal) throws QuizNotFoundException, UserNotFoundException;

    Page<QuizCompletionDTO> getCompletedQuizzes(Integer pageNumber, Principal principal) throws UserNotFoundException;

    MessageDTO deleteQuiz(long id, Principal principal) throws UserDoesNotOwnQuizException, QuizNotFoundException;

}
