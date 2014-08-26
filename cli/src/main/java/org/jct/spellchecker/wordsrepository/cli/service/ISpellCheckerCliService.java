package org.jct.spellchecker.wordsrepository.cli.service;

import java.util.List;

public interface ISpellCheckerCliService {

	List<String> checkFile(String fileName);

	String readFile(String fileName);

}