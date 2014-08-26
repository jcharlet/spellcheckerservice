package org.jct.spellchecker.wordsrepository.cli.domain;

import java.util.ArrayList;
import java.util.List;

//TODO JCT handle concurrency and unmutability<br/>
//Pay attention not to instantiate new objects when you return unmodifiable lists<br/>
// Collections.unmodifiableList(listOfWordsToProcess);<br/>
//FIXME JCT provide a map of words with current treatment status
public class SpellCheckerCliProcessData {
	private List<String> listOfWordsToProcess;
	private List<String> listOfDiscardedWords = new ArrayList<String>();
	private List<String> listOfAddedWords = new ArrayList<String>();
	private Integer wordIndex = 0;

	public SpellCheckerCliProcessData(List<String> listOfWordsToProcess) {
		super();
		this.listOfWordsToProcess = listOfWordsToProcess;
	}

	public List<String> getListOfWordsToProcess() {
		return this.listOfWordsToProcess;
	}


	public List<String> getListOfDiscardedWords() {
		return this.listOfDiscardedWords;
	}

	public List<String> getListOfAddedWords() {
		return this.listOfAddedWords;
	}



	public Integer getNumberOfElements() {
		return listOfWordsToProcess.size();
	}

	public void nextWord() {
			wordIndex++;
	}

	public String getCurrentWord() {
		return listOfWordsToProcess.get(wordIndex);
	}

	public void addCurrentWordToRepository() {
		listOfAddedWords.add(listOfWordsToProcess.get(wordIndex));
	}

	public boolean hasNextWord() {
		if (wordIndex == null) {
			return this.listOfWordsToProcess.size() > 0;
		}
		return this.wordIndex < this.listOfWordsToProcess.size() - 1;
	}

	public void discardCurrentWord() {
		listOfDiscardedWords.add(listOfWordsToProcess.get(wordIndex));
	}

}
