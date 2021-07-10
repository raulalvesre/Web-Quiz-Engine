package engine.application.interfaces;

import engine.application.dto.UserDTO;
import engine.application.exceptions.EmailAlreadyRegisteredException;

public interface RegisterServiceInterface {

    void createUser(UserDTO newUser) throws EmailAlreadyRegisteredException;

}
