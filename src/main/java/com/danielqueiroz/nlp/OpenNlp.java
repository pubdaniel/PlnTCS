package com.danielqueiroz.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.cogroo.text.Document;
import org.cogroo.text.impl.DocumentImpl;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import opennlp.tools.util.featuregen.TokenPatternFeatureGenerator;

public class OpenNlp {

	private static final String RESOURCE_BASE_PATH = "src/main/resources/";
	private static LanguageDetectorModel languageModel;
	private static SentenceModel sentenceModel;
	private static TokenNameFinderModel nameFinderModel;
	private static TokenNameFinderModel organizationFinderModel;
	private static TokenNameFinderModel locationFinderModel;
	private static TokenNameFinderModel dateFinderModel;
	private static TokenNameFinderModel moneyFinderModel;
	private static TokenNameFinderModel percentageFinderModel;
	private static TokenNameFinderModel timeFinderModel;

	private static TokenizerModel tokenModel;

	public OpenNlp() throws IOException {
		languageModel = new LanguageDetectorModel(
				new File(RESOURCE_BASE_PATH + "models/langdetect-183.bin").toURI().toURL());

		sentenceModel = new SentenceModel(new File(RESOURCE_BASE_PATH + "models\\pt-sent.bin"));
		nameFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\en-ner-person.bin"));
		locationFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\en-ner-location.bin"));
		organizationFinderModel = new TokenNameFinderModel(
				new File(RESOURCE_BASE_PATH + "models\\en-ner-organization.bin"));
		dateFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\en-ner-date.bin"));
		moneyFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\en-ner-money.bin"));
		percentageFinderModel = new TokenNameFinderModel(
				new File(RESOURCE_BASE_PATH + "models\\en-ner-percentage.bin"));
		timeFinderModel = new TokenNameFinderModel(new File(RESOURCE_BASE_PATH + "models\\en-ner-time.bin"));
		tokenModel = new TokenizerModel(new File(RESOURCE_BASE_PATH + "models\\cogroo\\pt-tok.model"));

	}

	public Language detectLanguage(String text) {
		LanguageDetector detector = new LanguageDetectorME(languageModel);
		return detector.predictLanguage(text);
	}

	public Language[] detectLanguages(String text) {
		LanguageDetector detector = new LanguageDetectorME(languageModel);
		return detector.predictLanguages(text);
	}

	public List<String> extractPhrases(String text) {
		SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
		String sentences[] = sentenceDetector.sentDetect(text);
		return Arrays.asList(sentences);
	}

	private String[] getTokens(String text) {
		Tokenizer tokenizer = new TokenizerME(tokenModel);
		return tokenizer.tokenize(text);
	}

	public void findNamedEntity(TokenNameFinderModel model, String[] texts) {
		NameFinderME nameFinder = new NameFinderME(model);
		Span nameSpans[] = nameFinder.find(texts);

		for (Span span : nameSpans) {
			System.out.println(" - Position - " + span.toString() + "    LocationName - " + texts[span.getStart()]);
		}
	}

	public void nameFinder(String text) {
		try {
			Cogroo cogroo = new Cogroo(text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		NameFinderME nameFinder = new NameFinderME(nameFinderModel);
		String[] tokens = getTokens(text);
		Span nameSpans[] = nameFinder.find(tokens);

		for (Span span : nameSpans) {
			System.out.println("Position - " + span.toString() + "    LocationName - " + tokens[span.getStart()]);
		}

	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		String text = "Hastings said at the New York Times DealBook Conference in New York City. “You’ll hear some subscriber numbers but you can just bundle things so that’s not going to be that relevant. So the real measurement will be time how do consumers vote with their evenings? What mix of all the services do they end up watching?";
		OpenNlp nlp = new OpenNlp();
		nlp.detectLanguage("teste de lingauem");
		String[] tokens = nlp.getTokens(text);
		
		
		TokenNameFinderModel[] models = {dateFinderModel, nameFinderModel, locationFinderModel, moneyFinderModel, organizationFinderModel, percentageFinderModel, timeFinderModel}; 
		
		Arrays.asList(models).forEach(model -> {
			nlp.findNamedEntity(model, tokens);
			System.out.println(".");
		});

		System.out.println("terminou");

	}

}
