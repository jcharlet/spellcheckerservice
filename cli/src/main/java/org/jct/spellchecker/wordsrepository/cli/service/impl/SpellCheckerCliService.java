package org.jct.spellchecker.wordsrepository.cli.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.jct.spellchecker.wordsrepository.cli.domain.SpellCheckerStatus;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.ISpellCheckerCliService;
import org.jct.spellchecker.wordsrepository.cli.ws.client.IDetectLanguageClient;
import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * See https://github.com/detectlanguage/detectlanguage-java
 * 
 */
@Service
@Scope("singleton")
public class SpellCheckerCliService implements ISpellCheckerCliService {
	private static final int NB_THREADS = 10;

	// protected final Logger LOG = Logger.getLogger(getClass().getName());

	@Autowired
	private ISpellCheckerClient spellCheckerClient;

	@Autowired
	private IDetectLanguageClient detectLanguageClient;

	private ExecutorService executorService;

	@PostConstruct
	private void initExecutorService() {
		executorService = Executors.newFixedThreadPool(NB_THREADS);
	}

	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.service.impl.ISpellCheckerCliService#checkFile(java.lang.String)
	 */
	@Override
	public LinkedHashSet<String> parseFile(String fileName) {
		String text = readFile(fileName);
		String[] array = text.split("[\\n\\r\\s]+");
		LinkedHashSet<String> listOfUniqueAndSortedWords = new LinkedHashSet<String>();
		for (String word : array) {
			listOfUniqueAndSortedWords.add(word);
		}
		return listOfUniqueAndSortedWords;
	}

	public Map<String, SpellCheckerStatus> checkFileWithMap(String fileName) {
		String text = readFile(fileName);
		List<String> listOfWords = Arrays.asList(text.split("[\\n\\r\\s]+"));
		LinkedHashSet<String> listOfUniqueWords = new LinkedHashSet<String>(
				listOfWords);
		for (String word : listOfUniqueWords) {

		}
		return null;
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
	public LinkedHashSet<String> checkWords(LinkedHashSet<String> uniqueWords) {
		for (String wordToCheck : uniqueWords) {

		}
		return null;
	}

}
