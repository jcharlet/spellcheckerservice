package org.jct.spellchecker.wordsrepository.cli.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.ISpellCheckerCliService;
import org.jct.spellchecker.wordsrepository.cli.ws.client.IDetectLanguageClient;
import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * See https://github.com/detectlanguage/detectlanguage-java
 * 
 */
@Service
@Scope("singleton")
public class SpellCheckerCliService implements ISpellCheckerCliService {

	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private ISpellCheckerClient spellCheckerClient;

	@Autowired
	private IDetectLanguageClient detectLanguageClient;


	@Value("${detectlanguage.mockresponse}")
	private String defaultLanguage;


	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.service.impl.ISpellCheckerCliService#checkFile(java.lang.String)
	 */
	@Override
	public LinkedHashSet<String> parseFile(String fileName) {
		String text = readFile(fileName);
		String[] array = removeUnwantedCharsFromText(text);
		LinkedHashSet<String> listOfUniqueAndSortedWords = new LinkedHashSet<String>();
		for (String word : array) {
			listOfUniqueAndSortedWords.add(word.toLowerCase());
		}
		return listOfUniqueAndSortedWords;
	}


	/**
	 * remove unwanted chars from a text (punctuation, whitespaces, break lines,
	 * and others): keeps only alphabet and accents
	 * 
	 * @param text
	 * @return
	 */
	public String[] removeUnwantedCharsFromText(String text) {
		String[] array = text.replaceAll("[^\\p{L} ]", " ")
				.split("[\\n\\r\\s]+");
		return array;
	}


	// FIXME JCT might need to set the folder where to look
	// FIXME JCT treatment can be optimized with use of Scanner
	public String readFile(String fileName) {
		// LOG.info("Current directory is "
		// + new File(".").getAbsolutePath());
		if (!Files.exists(Paths.get(fileName), LinkOption.NOFOLLOW_LINKS)) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.FILE_NOT_FOUND);
		}
		try {
			return new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.READING_ERROR, e);
		}
	}

	@Override
	public String detectLanguage(Set<String> listOfWords) {
		try {
			return detectLanguageClient.detectLanguage(listOfWords.toString());
		} catch (SpellCheckerCliException e) {
			logger.log(Level.WARNING,
					"an error occured while detecting the language. Default language returned"
							+ e.getExceptionStatus().toString());
			return defaultLanguage;
		}
	}

	@Override
	public LinkedHashSet<String> checkWords(Set<String> uniqueWords,
			String language) {
		LinkedHashSet<String> unknownWords = new LinkedHashSet<String>();
		try {
		for (String wordToCheck : uniqueWords) {
			if (!spellCheckerClient.check(language, wordToCheck)) {
				unknownWords.add(wordToCheck);
			}
		}
		} catch (SpellCheckerCliException e) {
			if (SpellCheckerCliExceptionStatus.SPELL_CHECKER_LANGUAGE_NOT_FOUND
					.equals(e.getExceptionStatus())) {
				unknownWords.addAll(uniqueWords);
			} else {
				throw e;
			}
		}
		return unknownWords;
	}

	@Override
	public Boolean addWord(String language, String word) {
		if (StringUtils.isEmpty(language) || StringUtils.isEmpty(word)) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.INVALID_PARAM_TO_ADD_REQUEST);
		}
		return spellCheckerClient.add(language, word);
	}

}
