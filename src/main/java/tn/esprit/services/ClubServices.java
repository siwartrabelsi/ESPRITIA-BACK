package tn.esprit.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Club;
import tn.esprit.entities.Evenement;
import tn.esprit.entities.User;
import tn.esprit.repositories.ClubRepository;
import tn.esprit.repositories.EvenementRepository;
import tn.esprit.repositories.UserRepository;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubServices implements IClubServices {

    @Autowired
    private ClubRepository clubRepository;


    @Override
    public List<Club> findAll() {
        return clubRepository.findAll();
    }

    @Override
    public Optional<Club> findById(Long id) {
        return clubRepository.findById(id);
    }


    @Override
    public Club save(Club club) {
        return clubRepository.save(club);
    }

    @Override
    public void deleteById(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    public Club update(Long id, Club club) {
        if (clubRepository.existsById(id)) {
            club.setId(id);
            return clubRepository.save(club);
        }
        return null;
    }

    @Override
    public List<Club> findByNom(String nom) {
        return clubRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public Optional<Club> likeClub(Long id) {
        Optional<Club> clubOpt = clubRepository.findById(id);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            club.setNbLikes(club.getNbLikes() + 1);
            club.setPointsFidelite(club.getPointsFidelite() + 1);
            clubRepository.save(club);
        }
        return clubOpt;
    }

    @Override
    public Optional<Club> dislikeClub(Long id) {
        Optional<Club> clubOpt = clubRepository.findById(id);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            club.setNbDislikes(club.getNbDislikes() + 1);
            club.setPointsFidelite(club.getPointsFidelite() - 1);
            clubRepository.save(club);
        }
        return clubOpt;
    }

    @Override
    public byte[] generateQRCode(Long clubId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            String qrText = club.getNom() + " - " + club.getObjectif() + " - " + club.getDate();

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix;
            try {
                Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
                hintMap.put(EncodeHintType.MARGIN, 1); // Set margin
                bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200, hintMap);
            } catch (WriterException e) {
                e.printStackTrace();
                return null;
            }

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            try {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return pngOutputStream.toByteArray();
        }
        return null;
    }

    @Override
    public byte[] generatePDF(Long clubId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                PdfWriter.getInstance(document, out);
                document.open();
                document.add(new Paragraph("Club Details"));
                document.add(new Paragraph("Name: " + club.getNom()));
                document.add(new Paragraph("Objectif: " + club.getObjectif()));
                document.add(new Paragraph("Date: " + club.getDate()));
                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return out.toByteArray();
        }
        return null;
    }

    @Override
    public long getTotalClubCount() {
        return clubRepository.count();
    }

    @Override
    public long getTotalLikesCount() {
        return clubRepository.sumLikes();
    }

    @Override
    public long getTotalDislikesCount() {
        return clubRepository.sumDislikes();
    }

    @Override
    public int getPointsFidelite(Long clubId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        return clubOpt.map(Club::getPointsFidelite).orElse(0);
    }

}
