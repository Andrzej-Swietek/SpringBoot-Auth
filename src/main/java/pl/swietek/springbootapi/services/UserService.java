package pl.swietek.springbootapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.swietek.springbootapi.models.Role;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void addRoleToUser(String username, String roleName) {
        // Implement your logic for adding a role to a user
        // This could involve fetching the user by username from the repository,
        // fetching the role by roleName, and associating them
        // with the user's roles collection
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
