package bsd.kschmitt.eval.repository;

import bsd.kschmitt.eval.model.Salarie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalarieRepository extends JpaRepository<Salarie, Integer> {
}
