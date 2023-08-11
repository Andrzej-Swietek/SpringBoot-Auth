package pl.swietek.springbootapi.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.models.Role;
import pl.swietek.springbootapi.requests.user.AddUserRoleRequest;
import pl.swietek.springbootapi.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/add").toUriString());
        return ResponseEntity
                .created(uri)
                .body(userService.saveUser(user));
    }

    @PostMapping("/role/assignRole")
    public ResponseEntity<?> assignRoleRole(@RequestBody AddUserRoleRequest payload) {
        userService.addRoleToUser(payload.getUsername(), payload.getRoleName());
        return ResponseEntity.ok().build();

    }
}
