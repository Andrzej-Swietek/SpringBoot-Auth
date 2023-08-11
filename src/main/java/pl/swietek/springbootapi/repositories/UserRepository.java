package pl.swietek.springbootapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.swietek.springbootapi.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
