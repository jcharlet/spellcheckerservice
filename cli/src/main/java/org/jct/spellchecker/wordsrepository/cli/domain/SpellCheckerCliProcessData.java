package org.jct.spellchecker.wordsrepository.cli.domain;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

//TODO JCT handle concurrency and unmutability<br/>
//Pay attention not to instantiate new objects when you return unmodifiable lists<br/>
// Collections.unmodifiableList(listOfWordsToProcess);<br/>
//FIXME JCT provide a map of words with current treatment status instead of 3 different lists
/**
 * Object that handles the data of a file processing. <br/>
 * contains the list of words to process, list of added or discarded words<br/>
 * provides helper tools like "get current word", "has next word", etc
 * 
 */
public class SpellCheckerCliProcessData {
	private Set<String> listOfWordsToProcess;
	private Set<String> listOfDiscardedWords = new LinkedHashSet<String>();
	private Set<String> listOfAddedWords = new LinkedHashSet<String>();
	private Iterator<String> wordIterator;
	private String word;
	private String language;

	public SpellCheckerCliProcessData(Set<String> listOfWordsToProcess,
			String language) {
		super();
		this.listOfWordsToProcess = listOfWordsToProcess;
		this.language = language;

		wordIterator = listOfWordsToProcess.iterator();
		if (wordIterator.hasNext()) {
			word = wordIterator.next();
		}
	}

	public Set<String> getListOfWordsToProcess() {
		return this.listOfWordsToProcess;
	}

	public Set<String> getListOfDiscardedWords() {
		return this.listOfDiscardedWords;
	}

	public Set<String> getListOfAddedWords() {
		return this.listOfAddedWords;
	}

	public Integer getNumberOfElements() {
		return listOfWordsToProcess.size();
	}

	public void nextWord() {
		if (wordIterator.hasNext()) {
			word = wordIterator.next();
		}
	}

	public String getCurrentWord() {
		return word;
	}

	public void addCurrentWordToRepository() {
		listOfAddedWords.add(word);
	}

	public boolean hasNextWord() {
		return wordIterator.hasNext();
	}

	public void discardCurrentWord() {
		listOfDiscardedWords.add(word);
	}

	public String getLanguage() {
		return language;
	}

}
