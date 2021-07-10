package engine.api.advices;

import engine.api.controllers.RegisterController;
import engine.application.dto.MessageDTO;
import engine.application.exceptions.EmailAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice(assignableTypes = {RegisterController.class})
public class RegisterControllerAdvice {

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDTO handleEmailAlreadyRegisteredException(Exception e) {
        return new MessageDTO("Email already registered");
    }

}
