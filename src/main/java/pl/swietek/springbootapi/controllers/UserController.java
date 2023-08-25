package pl.swietek.springbootapi.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.swietek.springbootapi.models.User;
import pl.swietek.springbootapi.responses.common.ApiBasicResponse;
import pl.swietek.springbootapi.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/user")
//@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping(path="all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity
                .ok()
                .body(userService.getUsers());
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping(path="{id}")
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
    public ResponseEntity<User> addUsers(@RequestBody User user) {
        return ResponseEntity
                .ok()
                .body(userService.saveUser(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            User savedUser = userService.updateUser(existingUser);

            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
//    @PreAuthorize("hasAuthority('admin:delete')")
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
