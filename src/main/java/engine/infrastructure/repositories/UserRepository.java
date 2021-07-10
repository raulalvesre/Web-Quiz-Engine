package engine.infrastructure.repositories;

import engine.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from users where email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

}
