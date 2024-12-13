package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Users;
import bsd.kschmitt.eval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.UserRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Users> createUsers(@RequestBody Users users) {
        if (UserRepository.findByEmail(users.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Users savedUsers = UserRepository.save(users);
        System.out.println(savedUsers);
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return UserRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable Integer id) {
        Optional<Users> Users = UserRepository.findById(id);
        return Users.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUsers(@PathVariable Integer id, @RequestBody Users updatedUsers) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedUsers.setId(id);
        Users savedUsers = UserRepository.save(updatedUsers);
        return ResponseEntity.ok(savedUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsers(@PathVariable Integer id) {
        if (!UserRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
