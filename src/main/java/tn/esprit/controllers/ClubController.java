package tn.esprit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.entities.Club;
import tn.esprit.services.ClubServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
@CrossOrigin (origins= "http ://Localhost :4200")

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubServices clubService;

    @GetMapping
    public List<Club> getAllClubs() {
        return clubService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClubById(@PathVariable Long id) {
        Optional<Club> club = clubService.findById(id);
        return club.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Club createClub(@RequestBody Club club) {
        return clubService.save(club);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Club> updateClub(@PathVariable Long id, @RequestBody Club club) {
        Club updatedClub = clubService.update(id, club);
        if (updatedClub != null) {
            return ResponseEntity.ok(updatedClub);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        if (clubService.findById(id).isPresent()) {
            clubService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Club> searchClubs(@RequestParam String nom) {
        return clubService.findByNom(nom);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Club> likeClub(@PathVariable Long id) {
        Optional<Club> clubOpt = clubService.likeClub(id);
        return clubOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/dislike")
    public ResponseEntity<Club> dislikeClub(@PathVariable Long id) {
        Optional<Club> clubOpt = clubService.dislikeClub(id);
        return clubOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long id) {
        byte[] qrCode = clubService.generateQRCode(id);
        if (qrCode != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(qrCode.length);
            return ResponseEntity.ok().headers(headers).body(qrCode);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long id) {
        byte[] pdf = clubService.generatePDF(id);
        if (pdf != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentLength(pdf.length);
            return ResponseEntity.ok().headers(headers).body(pdf);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/stats/total-clubs")
    public long getTotalClubCount() {
        return clubService.getTotalClubCount();
    }

    @GetMapping("/stats/total-likes")
    public long getTotalLikesCount() {
        return clubService.getTotalLikesCount();
    }

    @GetMapping("/stats/total-dislikes")
    public long getTotalDislikesCount() {
        return clubService.getTotalDislikesCount();
    }
}
