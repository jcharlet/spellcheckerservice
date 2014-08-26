package org.jct.spellchecker.wordsrepository.cli.ws.client;

public interface ISpellCheckerClient {

	public abstract Boolean check(String language, String word);

	public abstract Boolean add(String language, String word);

}