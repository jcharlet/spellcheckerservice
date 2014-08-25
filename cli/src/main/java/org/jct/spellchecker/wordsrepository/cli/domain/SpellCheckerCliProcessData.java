package org.jct.spellchecker.wordsrepository.cli.domain;

import java.util.ArrayList;
import java.util.List;

//TODO JCT handle concurrency and unmutability<br/>
//Pay attention not to instantiate new objects when you return unmodifiable lists<br/>
// Collections.unmodifiableList(listOfWordsToProcess);<br/>
public class SpellCheckerCliProcessData {
	private List<String> listOfWordsToProcess;
	private List<String> listOfDiscardedWords = new ArrayList<String>();
	private List<String> listOfAddedWords = new ArrayList<String>();

	public SpellCheckerCliProcessData(List<String> listOfWordsToProcess) {
		super();
		this.listOfWordsToProcess = listOfWordsToProcess;
	}

	public List<String> getListOfWordsToProcess() {
		return this.listOfWordsToProcess;
	}

	public String getWordFromIndex(Integer wordIndex) {
		return this.listOfWordsToProcess.get(wordIndex);
	}

	public List<String> getListOfDiscardedWords() {
		return this.listOfDiscardedWords;
	}

	public List<String> getListOfAddedWords() {
		return this.listOfAddedWords;
	}

	public synchronized void discardWord(String word) {
		listOfDiscardedWords.add(word);
	}

	public synchronized void addWordToRepository(String word) {
		listOfAddedWords.add(word);
	}

}
