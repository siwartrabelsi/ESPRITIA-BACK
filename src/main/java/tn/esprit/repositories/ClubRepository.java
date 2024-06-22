package tn.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.entities.Club;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {


    List<Club> findByNomContainingIgnoreCase(String nom);


    @Query("SELECT COUNT(c) FROM Club c")
    long countClubs();

    @Query("SELECT SUM(c.nbLikes) FROM Club c")
    long sumLikes();

    @Query("SELECT SUM(c.nbDislikes) FROM Club c")
    long sumDislikes();
}
