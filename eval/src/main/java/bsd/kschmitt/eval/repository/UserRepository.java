package bsd.kschmitt.eval.repository;

import bsd.kschmitt.eval.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    static Users findByEmail(String email) {
        return null;
    }

}
