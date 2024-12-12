package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Entreprise;
import bsd.kschmitt.eval.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    // Create a new Entreprise
    @PostMapping
    public ResponseEntity<Entreprise> createEntreprise(@RequestBody Entreprise entreprise) {
        // Check if entreprise already exists
        if (entrepriseRepository.findByNom(entreprise.getNom()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);  // HTTP 409
        }
        Entreprise savedEntreprise = entrepriseRepository.save(entreprise);
        return new ResponseEntity<>(savedEntreprise, HttpStatus.CREATED);
    }

    // Get all Entreprises
    @GetMapping
    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    // Get a specific Entreprise by ID
    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Integer id) {
        Optional<Entreprise> entreprise = entrepriseRepository.findById(id);
        return entreprise.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing Entreprise
    @PutMapping("/{id}")
    public ResponseEntity<Entreprise> updateEntreprise(@PathVariable Integer id, @RequestBody Entreprise updatedEntreprise) {
        if (!entrepriseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedEntreprise.setId(id);
        Entreprise savedEntreprise = entrepriseRepository.save(updatedEntreprise);
        return ResponseEntity.ok(savedEntreprise);
    }

    // Delete an Entreprise
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntreprise(@PathVariable Integer id) {
        if (!entrepriseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entrepriseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

