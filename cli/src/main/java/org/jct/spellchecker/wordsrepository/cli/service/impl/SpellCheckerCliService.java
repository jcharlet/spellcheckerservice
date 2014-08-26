package org.jct.spellchecker.wordsrepository.cli.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.ISpellCheckerCliService;
import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class SpellCheckerCliService implements ISpellCheckerCliService {
	// protected final Logger LOG = Logger.getLogger(getClass().getName());

	@Autowired
	ISpellCheckerClient spellCheckerClient;

	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.service.impl.ISpellCheckerCliService#checkFile(java.lang.String)
	 */
	@Override
	public List<String> checkFile(String fileName) {
		String text = readFile(fileName);
		return Arrays.asList(text.split("[\\n\\r\\s]+"));
	}

	// FIXME JCT might need to set the folder where to look
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

}
