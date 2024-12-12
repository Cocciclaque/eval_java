package bsd.kschmitt.eval.repository;

import bsd.kschmitt.eval.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {
    Entreprise findByNom(String nom);
}
