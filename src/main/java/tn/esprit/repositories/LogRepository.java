package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    // You can add custom query methods here if needed
}