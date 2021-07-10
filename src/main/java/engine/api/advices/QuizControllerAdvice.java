package engine.api.advices;

import engine.api.controllers.QuizController;
import engine.application.dto.MessageDTO;
import engine.application.exceptions.QuizNotFoundException;
import engine.application.exceptions.UserDoesNotOwnQuizException;
import engine.application.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(assignableTypes = {QuizController.class})
public class QuizControllerAdvice {

    @ExceptionHandler(QuizNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDTO handleQuizNotFoundException(Exception e) {
        return new MessageDTO("Quiz not found");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageDTO handleUserNotFoundException(Exception e) {
        return new MessageDTO("User not found");
    }

    @ExceptionHandler(UserDoesNotOwnQuizException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MessageDTO handleUserDoesNotOwnQuizException(Exception e) {
        return new MessageDTO("You don't own this quiz");
    }

}
