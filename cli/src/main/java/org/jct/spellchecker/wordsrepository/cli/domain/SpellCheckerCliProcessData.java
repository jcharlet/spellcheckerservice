package org.jct.spellchecker.wordsrepository.cli.domain;

import java.util.Iterator;
import java.util.LinkedHashSet;

//TODO JCT handle concurrency and unmutability<br/>
//Pay attention not to instantiate new objects when you return unmodifiable lists<br/>
// Collections.unmodifiableList(listOfWordsToProcess);<br/>
//FIXME JCT provide a map of words with current treatment status
public class SpellCheckerCliProcessData {
	private LinkedHashSet<String> listOfWordsToProcess;
	private LinkedHashSet<String> listOfDiscardedWords = new LinkedHashSet<String>();
	private LinkedHashSet<String> listOfAddedWords = new LinkedHashSet<String>();
	private Iterator<String> wordIterator;
	private String word;
	private String language;

	public SpellCheckerCliProcessData(
			LinkedHashSet<String> listOfWordsToProcess, String language) {
		super();
		this.listOfWordsToProcess = listOfWordsToProcess;
		this.language = language;

		wordIterator = listOfWordsToProcess.iterator();
		if (wordIterator.hasNext()) {
			word = wordIterator.next();
		}
	}

	public LinkedHashSet<String> getListOfWordsToProcess() {
		return this.listOfWordsToProcess;
	}


	public LinkedHashSet<String> getListOfDiscardedWords() {
		return this.listOfDiscardedWords;
	}

	public LinkedHashSet<String> getListOfAddedWords() {
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
