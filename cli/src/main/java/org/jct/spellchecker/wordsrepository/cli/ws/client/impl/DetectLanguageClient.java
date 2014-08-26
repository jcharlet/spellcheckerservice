package org.jct.spellchecker.wordsrepository.cli.ws.client.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.ws.client.IDetectLanguageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.errors.APIError;

@Service
@Scope("singleton")
public class DetectLanguageClient implements IDetectLanguageClient {


	protected final Logger LOG = Logger.getLogger(getClass().getName());

	@Value("${detectlanguage.ismocked}")
	private Boolean isMocked;

	@Value("${detectlanguage.mockresponse}")
	private String mockLanguage;
	
	@Value("${detectlanguage.apiKey}")
	private String apiKey;

	@PostConstruct
	public void initApi() {
		DetectLanguage.apiKey = apiKey;
	}

	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.ws.client.impl.IDetectLanguageClient#detectLanguage(java.lang.String)
	 */
	@Override
	public String detectLanguage(String text) {
		if (isMocked) {
			return mockLanguage;
		}
		String language;
		try {
			language = DetectLanguage.simpleDetect(text);
		} catch (APIError e) {
			LOG.log(Level.SEVERE, "DETECT_LANGUAGE_ERROR: code returned="
					+ e.code);
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.DETECT_LANGUAGE_ERROR, e);
		}
		return language.toUpperCase();
	}

}
