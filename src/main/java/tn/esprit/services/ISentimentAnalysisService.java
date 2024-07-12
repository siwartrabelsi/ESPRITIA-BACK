package tn.esprit.services;

import tn.esprit.entities.Reclamation;

import java.util.List;
import java.util.Map;

public interface ISentimentAnalysisService {
    public String analyzeSentiment(String text);
    public Map<String, List<String>> analyzeSentimentsByUser(List<Reclamation> reclamations);
}
