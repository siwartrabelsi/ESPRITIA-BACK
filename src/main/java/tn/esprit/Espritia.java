package tn.esprit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling	
@SpringBootApplication
public class Espritia {
	public static void main(String[] args) {
	/*	StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
		String text = "go to hell";
		CoreDocument coreDocument = new CoreDocument(text);
		stanfordCoreNLP.annotate(coreDocument);
		List<CoreSentence> sentences = coreDocument.sentences();
		for (CoreSentence coreLabel: sentences){
			String sentiment = coreLabel.sentiment();
			System.out.println(sentiment + "\t" + coreLabel);
		}*/
		SpringApplication.run(Espritia.class, args);

	}
}
