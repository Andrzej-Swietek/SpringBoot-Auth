package pl.swietek.springbootapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.swietek.springbootapi.models.Role;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.repositories.UserRepository;

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
        return userRepository
                .findById(id)
                .orElse(null);
    }
    public User saveUser(User userRequest) {
        var user = User.builder()
                .firstname(userRequest.getFirstname())
                .lastname(userRequest.getLastname())
                .email(userRequest.getEmail())
                .password( passwordEncoder.encode(userRequest.getPassword()) )
                .role(userRequest.getRole())
                .build();
        return userRepository.save(user);
    }

//    public Optional<User> addRoleToUser(String email, UserRoleEnum roleName) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//        if (userOptional.isEmpty()) {
//            return Optional.empty(); // User not found
//        }
//
//        User user = userOptional.get();
//
//        // Check if the user already has the role
//        if (user.getRoles().contains(roleName)) {
//            return Optional.of(user); // Role is already associated with the user
//        }
//
//        user.getRoles().add(roleName);
//        userRepository.save(user);
//
//        return Optional.of(user);
//    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
