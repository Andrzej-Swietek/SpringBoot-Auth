package pl.swietek.springbootapi.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.models.Role;
import pl.swietek.springbootapi.requests.user.AddUserRoleRequest;
import pl.swietek.springbootapi.responses.auth.ApiBasicResponse;
import pl.swietek.springbootapi.services.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/user")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping(path="all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @GetMapping(path="{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity
                    .notFound().build();
        }
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        return ResponseEntity
                .ok()
                .body(userService.saveUser(user));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);

        if (existingUser != null) {
            modelMapper.map(updatedUser,existingUser);
            User savedUser = userService.saveUser(existingUser);

            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity
                    .ok()
                    .body(new ApiBasicResponse(true, "User Deleted"));
        } else {
            return ResponseEntity
                    .ok()
                    .body(new ApiBasicResponse(false, "Unable to Delete User"));
        }
    }

//    @PostMapping("/role/assignRole")
//    public ResponseEntity<?> assignRoleRole(@RequestBody AddUserRoleRequest payload) {
//        userService.addRoleToUser(payload.getUsername(), payload.getRoleName());
//        return ResponseEntity.ok().build();
//    }
}
