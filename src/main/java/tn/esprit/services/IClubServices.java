package tn.esprit.services;

import tn.esprit.entities.Club;

import java.util.List;
import java.util.Optional;

public interface IClubServices {
    List<Club> findAll();

    Optional<Club> findById(Long id);

    Club save(Club club);

    void deleteById(Long id);

    Club update(Long id, Club club);
    List<Club> findByNom(String nom);
    Optional<Club> likeClub(Long id);
    Optional<Club> dislikeClub(Long id);
    byte[] generateQRCode(Long clubId);
    byte[] generatePDF(Long clubId);
    long getTotalClubCount();

    long getTotalLikesCount();

    long getTotalDislikesCount();
}

