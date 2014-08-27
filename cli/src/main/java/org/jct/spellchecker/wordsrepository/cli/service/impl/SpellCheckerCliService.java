package org.jct.spellchecker.wordsrepository.cli.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.ISpellCheckerCliService;
import org.jct.spellchecker.wordsrepository.cli.ws.client.IDetectLanguageClient;
import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.springframework.beans.factory.annotation.Autowired;
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
	// private static final int NB_THREADS = 10;

	// protected final Logger LOG = Logger.getLogger(getClass().getName());

	@Autowired
	private ISpellCheckerClient spellCheckerClient;

	@Autowired
	private IDetectLanguageClient detectLanguageClient;

	// private ExecutorService executorService;
	//
	// @PostConstruct
	// private void initExecutorService() {
	// executorService = Executors.newFixedThreadPool(NB_THREADS);
	// }

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
		try {
			return new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.READING_ERROR, e);
		}
	}

	@Override
	public String detectLanguage(LinkedHashSet<String> listOfWords) {
		return detectLanguageClient.detectLanguage(listOfWords.toString());
	}

	@Override
	public LinkedHashSet<String> checkWords(LinkedHashSet<String> uniqueWords, String language) {
		LinkedHashSet<String> unknownWords = new LinkedHashSet<String>();
		for (String wordToCheck : uniqueWords) {
			if (!spellCheckerClient.check(language, wordToCheck)) {
				unknownWords.add(wordToCheck);
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
