package com.danielqueiroz.resource;

import java.util.Arrays;
import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.cogroo.analyzer.Analyzer;
import org.cogroo.analyzer.ComponentFactory;
import org.cogroo.text.Chunk;
import org.cogroo.text.Document;
import org.cogroo.text.Sentence;
import org.cogroo.text.SyntacticChunk;
import org.cogroo.text.Token;
import org.cogroo.text.impl.DocumentImpl;

@Path("/")
public class Hello {

	@GET
	public String getResult() {

		
	    ComponentFactory factory = ComponentFactory.create(new Locale("pt", "BR"));

	    Analyzer cogroo = factory.createPipe();

	    Document document = new DocumentImpl();
	    document.setText("texto de teste no cogroo");
	
	    cogroo.analyze(document);

		return print(document);
	}
	
	private String print(Document document) {
	    StringBuilder output = new StringBuilder();
	
	    // and now we navigate the document to print its data
	    for (Sentence sentence : document.getSentences()) {
	
	      // Print the sentence. You can also get the sentence span annotation.
	      output.append("Sentence: ").append(sentence.getText()).append("\n");
	
	      output.append("  Tokens: \n");
	
	      // for each token found...
	      for (Token token : sentence.getTokens()) {
	        String lexeme = token.getLexeme();
	        String lemmas = Arrays.toString(token.getLemmas());
	        String pos = token.getPOSTag();
	        String feat = token.getFeatures();
	
	        output.append(String.format("    %-10s %-12s %-6s %-10s\n", lexeme,
	            lemmas, pos, feat));
	      }
	
	      // we can also print the chunks, but printing it is not that easy!
	      output.append("  Chunks: ");
	      for (Chunk chunk : sentence.getChunks()) {
	        output.append("[").append(chunk.getTag()).append(": ");
	        for (Token innerToken : chunk.getTokens()) {
	          output.append(innerToken.getLexeme()).append(" ");
	        }
	        output.append("] ");
	      }
	      output.append("\n");
	
	      // we can also print the shallow parsing results!
	      output.append("  Shallow Structure: ");
	      for (SyntacticChunk structure : sentence.getSyntacticChunks()) {
	        output.append("[").append(structure.getTag()).append(": ");
	        for (Token innerToken : structure.getTokens()) {
	          output.append(innerToken.getLexeme()).append(" ");
	        }
	        output.append("] ");
	      }
	      output.append("\n");
	    }
	
	    return output.toString();
	  }
}
