
//Written by Jeremy Fristoe
//CEN-3024C-17056 Module 2 v3, 9/20/20
//This program references and processes specific areas of text on a website, outputting the frequency
//of occurrences for each word and sorted based on which words are most frequently used.

package m2v3;

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TextAnalyzer {

	public static void main(String[] args) throws IOException {
	
		Map<String, Word> countMap = new HashMap<String, Word>();

		// Connecting to the site and pulling the data
		System.out.println("Retrieving info from website and analyzing data. One moment, please...\n");
		
		Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").timeout(25000).get();

		// To get the list of all links from a website
		Elements paragraphs = doc.getElementsByTag("p");
		Elements heading1 = doc.getElementsByTag("h1");
		Elements heading3 = doc.getElementsByTag("h3");
		Elements heading4 = doc.getElementsByTag("h4");


		//Getting the actual text from the page, excluding the HTML
		String text = doc.body().text();

		System.out.println("#       Words\n-------------------");

		//Create BufferedReader so the words can be counted
		BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes())));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] words = line.split("[^a-zA-z]");
				for (String word : words) {
					if ("".equals(word)) {
						continue;
					}
					Word wordObj = countMap.get(word);
					if (wordObj == null) {
						wordObj = new Word();
						wordObj.word = word;
						wordObj.count = 0;
						countMap.put(word, wordObj);
					}
					wordObj.count++;
				}
		}

	    reader.close();
	
	    SortedSet<Word> sortedWords = new TreeSet<Word>(countMap.values());
	    int i = 0;
	    int maxWordsToDisplay = 20;
	
	    for (Word word : sortedWords) {
	        if (i >= maxWordsToDisplay) {
	             break;
	        }
	        else {
	            System.out.println(word.count + "\t" + word.word);
	            i++;
	        }
	
		}
	
	}

	public static class Word implements Comparable<Word> {
	     String word;
	     int count;

	     public int hashCode() { return word.hashCode(); }

	     public boolean equals(Object obj) { return word.equals(((Word)obj).word); }

	     public int compareTo(Word b) { return b.count - count; }
	}
}