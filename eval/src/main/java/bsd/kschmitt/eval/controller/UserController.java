package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Users;
import bsd.kschmitt.eval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Userss")
public class UserController {

    @Autowired
    private UserRepository UserRepository;

//    @Autowired
//    private UsersService UsersService; // If you need additional business logic

    @PostMapping
    public ResponseEntity<Users> createUsers(@RequestBody Users Users) {
        // Check if email already exists
        if (bsd.kschmitt.eval.repository.UserRepository.findByEmail(Users.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // HTTP 409
        }
        Users savedUsers = UserRepository.save(Users);
        System.out.println(savedUsers);
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Users> getAllUserss() {
        return UserRepository.findAll();
    }

    // Get a specific Users by ID
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable Integer id) {
        Optional<Users> Users = UserRepository.findById(id);
        return Users.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Users
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable Integer id, @RequestBody Users updatedUsers) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedUsers.setId(id);
        Users savedUsers = UserRepository.save(updatedUsers);
        return ResponseEntity.ok(savedUsers);
    }

    // Delete a Users
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Integer id) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
