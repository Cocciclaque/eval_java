package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.User;
import bsd.kschmitt.eval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository UserRepository;

//    @Autowired
//    private UserService UserService; // If you need additional business logic

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User User) {
        // Check if email already exists
        if (UserRepository.findByEmail(User.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // HTTP 409
        }
        User savedUser = UserRepository.save(User);
        System.out.println(savedUser);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return UserRepository.findAll();
    }

    // Get a specific User by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> User = UserRepository.findById(id);
        return User.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedUser.setId(id);
        User savedUser = UserRepository.save(updatedUser);
        return ResponseEntity.ok(savedUser);
    }

    // Delete a User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
