package engine.application.security.userDetails;

import engine.domain.entities.User;
import engine.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = users.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND!"));

        return user.map(MyUserDetails::new).get();
    }

}
