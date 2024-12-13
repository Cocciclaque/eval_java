package bsd.kschmitt.eval.controller;

import bsd.kschmitt.eval.model.Convention;
import bsd.kschmitt.eval.model.Salarie;
import bsd.kschmitt.eval.repository.ConventionRepository;
import bsd.kschmitt.eval.repository.SalarieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalarieController {

    @Autowired
    private SalarieRepository salarieRepository;

    @Autowired
    private ConventionRepository conventionRepository;

    @PostMapping
    public ResponseEntity<Salarie> addSalarie(@RequestBody Salarie salarie) {
        Convention convention = conventionRepository.findById(salarie.getConvention().getId()).orElse(null);
        if (convention == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (convention.getSalaries().size() >= convention.getSalarieMaximum()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Salarie savedSalarie = salarieRepository.save(salarie);
        return new ResponseEntity<>(savedSalarie, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Salarie> getAllSalaries() {
        return salarieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salarie> getSalarieById(@PathVariable Integer id) {
        return salarieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

