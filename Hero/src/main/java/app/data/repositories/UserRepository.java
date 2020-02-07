package app.data.repositories;

import app.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUserByUsername(String username);
    User findUserByUsername(String username);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
