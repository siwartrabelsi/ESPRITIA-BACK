package tn.esprit.services;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {
    private static Properties prop;
    private static String propertiesName = "tokenize, ssplit, parse, sentiment";
    public static StanfordCoreNLP stanfordCoreNLP;
    private Pipeline(){}
    static {
        prop = new Properties();
        prop.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getPipeline(){
        if(stanfordCoreNLP == null){
            stanfordCoreNLP = new StanfordCoreNLP(prop);
        }
        return stanfordCoreNLP;
    }
}
