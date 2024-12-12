package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Convention;
import bsd.kschmitt.eval.repository.ConventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conventions")
public class ConventionController {

    @Autowired
    private ConventionRepository conventionRepository;

    // Create a new Convention
    @PostMapping
    public ResponseEntity<Convention> createConvention(@RequestBody Convention convention) {
        Convention savedConvention = conventionRepository.save(convention);
        return new ResponseEntity<>(savedConvention, HttpStatus.CREATED);
    }

    // Get all Conventions
    @GetMapping
    public List<Convention> getAllConventions() {
        return conventionRepository.findAll();
    }

    // Get a specific Convention by ID
    @GetMapping("/{id}")
    public ResponseEntity<Convention> getConventionById(@PathVariable Integer id) {
        Optional<Convention> convention = conventionRepository.findById(id);
        return convention.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Convention
    @PutMapping("/{id}")
    public ResponseEntity<Convention> updateConvention(@PathVariable Integer id, @RequestBody Convention updatedConvention) {
        if (!conventionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedConvention.setId(id);
        Convention savedConvention = conventionRepository.save(updatedConvention);
        return ResponseEntity.ok(savedConvention);
    }

    // Delete a Convention
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConvention(@PathVariable Integer id) {
        if (!conventionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        conventionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

