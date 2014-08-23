package org.jct.spellchecker.wordsrepository.service;

import org.jct.spellchecker.wordsrepository.Application;
import org.jct.spellchecker.wordsrepository.exception.ExceptionStatus;
import org.jct.spellchecker.wordsrepository.exception.SpellCheckerException;
import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.jct.spellchecker.wordsrepository.jpa.repository.LanguageRepository;
import org.jct.spellchecker.wordsrepository.jpa.repository.WordRepository;
import org.jct.spellchecker.wordsrepository.service.impl.SpellCheckerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SpellCheckerServiceTest {

	private static final String KNOWN_WORD = "test";
	private static final String KNOWN_LANGUAGE = "EN";
	private static final String UNKNOWN_LANGUAGE = "X";
	private static final String UNKNOWN_WORD = "X";
	@Autowired
	@InjectMocks
	SpellCheckerService spellCheckerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// Must return SpellCheckerException with reason INVALID_LANGUAGE
	@Test
	public void testCheckWithUnknownLanguage() {
		SpellCheckerException exception = null;
		try {
			LanguageRepository languageRepositoryMock = Mockito
					.mock(LanguageRepository.class);
			Mockito.when(
					languageRepositoryMock.findByShortCode(UNKNOWN_LANGUAGE))
					.thenReturn(null);
			spellCheckerService.setLanguageRepository(languageRepositoryMock);

			spellCheckerService.check(UNKNOWN_LANGUAGE, KNOWN_WORD);

		} catch (SpellCheckerException e) {
			exception = e;
		}

		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionStatus.INVALID_LANGUAGE,
				exception.getStatus());
	}

	@Test
	public void testCheckWithNullLanguage() {
		SpellCheckerException exception = null;
		try {
			LanguageRepository languageRepositoryMock = Mockito
					.mock(LanguageRepository.class);
			Mockito.when(languageRepositoryMock.findByShortCode(null))
					.thenReturn(null);
			spellCheckerService.setLanguageRepository(languageRepositoryMock);

			spellCheckerService.check(null, KNOWN_WORD);

		} catch (SpellCheckerException e) {
			exception = e;
		}

		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionStatus.INVALID_LANGUAGE,
				exception.getStatus());
	}

	@Test
	public void testCheckWithNullWord() {
		SpellCheckerException exception = null;
		try {
			spellCheckerService.check(KNOWN_LANGUAGE, null);

		} catch (SpellCheckerException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals(ExceptionStatus.INVALID_WORD, exception.getStatus());
	}

	@Test
	public void testCheckWithKnownWord() throws SpellCheckerException {
		Language language = new Language(KNOWN_LANGUAGE);
		Word expectedWord = new Word(KNOWN_WORD, language);

		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(language);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock.findByLanguageAndName(language, KNOWN_WORD))
				.thenReturn(expectedWord);
		spellCheckerService.setWordRepository(wordRepositoryMock);

		Boolean wasFound = spellCheckerService
				.check(KNOWN_LANGUAGE, KNOWN_WORD);

		Assert.assertNotNull(wasFound);
		Assert.assertEquals(true, wasFound);
	}

	@Test
	public void testCheckWithUnknownWord() throws SpellCheckerException {
		Language language = new Language(KNOWN_LANGUAGE);

		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(language);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock
						.findByLanguageAndName(language, UNKNOWN_WORD))
				.thenReturn(null);
		spellCheckerService.setWordRepository(wordRepositoryMock);

		Boolean wasFound = spellCheckerService.check(KNOWN_LANGUAGE,
				UNKNOWN_WORD);

		Assert.assertNotNull(wasFound);
		Assert.assertEquals(false, wasFound);
	}

}
