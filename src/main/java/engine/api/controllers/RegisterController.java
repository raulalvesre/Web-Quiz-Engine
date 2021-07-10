package engine.api.controllers;

import engine.api.advices.RegisterControllerAdvice;
import engine.application.dto.UserDTO;
import engine.application.exceptions.EmailAlreadyRegisteredException;
import engine.application.interfaces.RegisterServiceInterface;
import engine.application.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    RegisterServiceInterface registerService;

    @PostMapping(value = "/api/register", consumes = "application/json")
    public void createUser(@Valid @RequestBody UserDTO newUser) throws EmailAlreadyRegisteredException {
        this.registerService.createUser(newUser);
    }

}
