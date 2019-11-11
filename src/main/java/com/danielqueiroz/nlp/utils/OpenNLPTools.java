package com.danielqueiroz.nlp.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import opennlp.tools.namefind.BioCodec;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderFactory;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPTools {

	
	public static void main(String[] args) throws IOException {
//		OpenNLPTools.trainModelFromCorpus(
//				"D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\amazonia\\corpus.txt",
//				"D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\amazonia\\ner-amazonia-custom-model.bin");
		OpenNLPTools.testTrainNameFinder(
				"D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\models\\amazonia\\ner-amazonia-custom-model.bin",
				"Pedro foi para São Paulo com Ana com R$50 em 12 de maio");
		
		 
	}

	private static void trainModelFromCorpus(String pathIn, String pathOut) throws FileNotFoundException, IOException {
		InputStreamFactory in = null;
		try {
			in = new MarkableFileInputStreamFactory(new File(pathIn));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ObjectStream sampleStream = null;
		try {
			sampleStream = new NameSampleDataStream(
					new PlainTextByLineStream(in, StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		TrainingParameters params = new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 100);
		params.put(TrainingParameters.CUTOFF_PARAM, 1);
		
		TokenNameFinderModel nameFinderModel = null;
		try {
		    nameFinderModel = NameFinderME.train("pt", null, sampleStream,
		        params, TokenNameFinderFactory.create(null, null, Collections.emptyMap(), new BioCodec()));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		File output = new File(pathOut);
		FileOutputStream outputStream = new FileOutputStream(output);
		nameFinderModel.serialize(outputStream);
	}

	private static void testTrainNameFinder(String fileModel, String text) throws IOException {
		TokenNameFinderModel model = new TokenNameFinderModel(new File(fileModel));
		TokenNameFinder nameFinder = new NameFinderME(model);
		TokenizerModel tokenizerModel = new TokenizerModel(new File("D:\\Desenvimento\\git\\PlnTCS\\src\\main\\resources\\models\\cogroo\\pt-tok.model"));
		Tokenizer tokenizer = new TokenizerME(tokenizerModel);
		
		
	        String[] testSentence =tokenizer.tokenize(text);
	 
	        System.out.println("Encontrando tipos no texto:");
	        Span[] names = nameFinder.find(testSentence);
	        for(Span name:names){
	            String personName="";
	            for(int i=name.getStart();i<name.getEnd();i++){
	                personName+=testSentence[i]+" ";
	            }
	            System.out.println(name.getType()+" : "+personName+"\t [probability="+name.getProb()+"]");
	        }
	}
	

}













