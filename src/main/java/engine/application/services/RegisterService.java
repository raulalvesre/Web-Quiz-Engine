package engine.application.services;

import engine.application.dto.UserDTO;
import engine.application.interfaces.RegisterServiceInterface;
import engine.domain.entities.User;
import engine.application.exceptions.EmailAlreadyRegisteredException;
import engine.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RegisterService implements RegisterServiceInterface {

    @Autowired
    UserRepository users;

    public void createUser(UserDTO newUser) throws EmailAlreadyRegisteredException {
        if (isEmailAlreadyRegistered(newUser.getEmail())) {
            throw new EmailAlreadyRegisteredException();
        }

        var userDTO = mapUserDTOToEntity(newUser);
        users.save(userDTO);
    }

    private User mapUserDTOToEntity(UserDTO userDto) {
        return new User(userDto.getEmail(), encryptPassword(userDto.getPassword()));
    }

    private boolean isEmailAlreadyRegistered(String email) {
        return users.findByEmail(email).isPresent();
    }

    private String encryptPassword(String plainPassword) {
        var encoder = new BCryptPasswordEncoder(10, new SecureRandom());
        return encoder.encode(plainPassword);
    }

}
