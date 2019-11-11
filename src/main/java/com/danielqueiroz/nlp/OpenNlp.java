package com.danielqueiroz.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.cogroo.text.Document;
import org.cogroo.text.impl.DocumentImpl;

import com.danielqueiroz.model.Entity;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.TokenPatternFeatureGenerator;

public class OpenNlp {

	private String text;
	private static final String RESOURCE_BASE_PATH = "D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\";
	private static LanguageDetectorModel languageModel;
	private static SentenceModel sentenceModel;
	private static TokenNameFinderModel namedFinderModel;

	private static TokenizerModel tokenModel;

	public OpenNlp(String text) throws IOException {
		this.text = text;
		languageModel = new LanguageDetectorModel(new File(RESOURCE_BASE_PATH + "models\\langdetect-183.bin"));
		sentenceModel = new SentenceModel(new File(RESOURCE_BASE_PATH + "models\\pt-sent.bin"));
		tokenModel = new TokenizerModel(new File(RESOURCE_BASE_PATH + "models\\cogroo\\pt-tok.model"));
		namedFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\amazonia\\ner-amazonia-custom-model.bin"));

	}

	public Language detectLanguage() {
		LanguageDetector detector = new LanguageDetectorME(languageModel);
		return detector.predictLanguage(text);
	}

	public Language[] detectLanguages() {
		LanguageDetector detector = new LanguageDetectorME(languageModel);
		return detector.predictLanguages(text);
	}

	public List<String> extractPhrases() {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
		String sentences[] = sentenceDetector.sentDetect(text);
		return Arrays.asList(sentences);
	}

	private String[] getTokens(String text) {
		Tokenizer tokenizer = new TokenizerME(tokenModel);
		return tokenizer.tokenize(text);
	}

	public List<Entity> findNamedEntity() {

		TokenNameFinder nameFinder = new NameFinderME(namedFinderModel);
		TokenizerModel tokenizerModel;
		List<Entity> entitys = new ArrayList<>();

		try {
			tokenizerModel = new TokenizerModel(new File("D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\models\\cogroo\\pt-tok.model"));
			Tokenizer tokenizer = new TokenizerME(tokenizerModel);
			
	        String[] testSentence =tokenizer.tokenize(text);
	 
	        System.out.println("Encontrando tipos no texto:");
	        Span[] names = nameFinder.find(testSentence);
	        
	        for(Span name:names){
	        	Entity entity = new Entity(name);
	        	entitys.add(entity);
	        	String description ="";
	            for(int i=name.getStart();i<name.getEnd();i++){
	            	description+=testSentence[i]+" ";
	            }
	            entity.setDescription(description);
	            
	            System.out.println(description + " | TYPE" + name.getType()+ "\t\t PROB: "+name.getProb() * 100);
	        }
	        
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entitys;
		
	}

	public void nameFinder(String text) {
		try {
			Cogroo cogroo = new Cogroo(text);
		} catch (IOException e) {
			e.printStackTrace();
		}

		NameFinderME nameFinder = new NameFinderME(namedFinderModel);
		String[] tokens = getTokens(text);
		Span nameSpans[] = nameFinder.find(tokens);

		for (Span span : nameSpans) {
			System.out.println("Position - " + span.toString() + "    LocationName - " + tokens[span.getStart()]);
		}

	}

	

	public void getEntitys() {
		
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		

	}
}
