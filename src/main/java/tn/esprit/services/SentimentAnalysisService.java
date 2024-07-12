package tn.esprit.services;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;
import tn.esprit.entities.Reclamation;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class SentimentAnalysisService implements ISentimentAnalysisService {

    @Override
    public String analyzeSentiment(String text) {
        StanfordCoreNLP pipeline;
        try {
            Properties props = new Properties();
            props.setProperty("annotators", "sentiment");
            pipeline = new StanfordCoreNLP(props);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing StanfordCoreNLP pipeline", e);
        }
        Annotation annotation = pipeline.process(text);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentimentScore = RNNCoreAnnotations.getPredictedClass(tree);
            String sentiment;

            switch (sentimentScore) {
                case 0: sentiment = "Very Negative"; break;
                case 1: sentiment = "Negative"; break;
                case 2: sentiment = "Neutral"; break;
                case 3: sentiment = "Positive"; break;
                case 4: sentiment = "Very Positive"; break;
                default: sentiment = "Unknown"; break; // Handle unknown cases if any
            }

            return sentiment;
        }

        return "Unknown";
    }

    @Override
    public Map<String, List<String>> analyzeSentimentsByUser(List<Reclamation> reclamations) {
        return reclamations.stream()
                .collect(Collectors.groupingBy(reclamation -> reclamation.getUser().getNom(),
                        Collectors.mapping(reclamation -> analyzeSentiment(reclamation.getDescription()), Collectors.toList())));
    }
}
