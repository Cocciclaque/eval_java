package bsd.kschmitt.eval.repository;

import bsd.kschmitt.eval.model.Convention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConventionRepository extends JpaRepository<Convention, Integer> {
}
