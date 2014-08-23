package org.jct.spellchecker.wordsrepository.service;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.jct.spellchecker.wordsrepository.Application;
import org.jct.spellchecker.wordsrepository.exception.SpellCheckerExceptionStatus;
import org.jct.spellchecker.wordsrepository.exception.SpellCheckerInvalidParameterException;
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
	private static final String KNOWN_LANGUAGE = "EN_test";
	private static final String UNKNOWN_LANGUAGE = "FR";
	private static final String UNKNOWN_WORD = "X";
	private static Language enLanguage = new Language(KNOWN_LANGUAGE);

	@Autowired
	@InjectMocks
	SpellCheckerService spellCheckerService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	// Must return SpellCheckerInvalidParameterException with reason
	// INVALID_LANGUAGE
	@Test
	public void testCheckWithUnknownLanguage() {
		SpellCheckerInvalidParameterException exception = null;
		try {
			LanguageRepository languageRepositoryMock = Mockito
					.mock(LanguageRepository.class);
			Mockito.when(
					languageRepositoryMock.findByShortCode(UNKNOWN_LANGUAGE))
					.thenReturn(null);
			spellCheckerService.setLanguageRepository(languageRepositoryMock);

			spellCheckerService.check(UNKNOWN_LANGUAGE, KNOWN_WORD);

		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}

		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.UNKNOWN_LANGUAGE.toString(),
				exception.getMessage());
	}

	@Test
	public void testCheckWithNullLanguage() {
		SpellCheckerInvalidParameterException exception = null;
		try {
			LanguageRepository languageRepositoryMock = Mockito
					.mock(LanguageRepository.class);
			Mockito.when(languageRepositoryMock.findByShortCode(null))
					.thenReturn(null);
			spellCheckerService.setLanguageRepository(languageRepositoryMock);

			spellCheckerService.check(null, KNOWN_WORD);

		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}

		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.INVALID_LANGUAGE.toString(),
				exception.getMessage());
	}

	@Test
	public void testCheckWithNullWord() {
		SpellCheckerInvalidParameterException exception = null;
		try {
			spellCheckerService.check(KNOWN_LANGUAGE, null);

		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.INVALID_WORD.toString(),
				exception.getMessage());
	}

	@Test
	public void testCheckWithKnownWord()
			throws SpellCheckerInvalidParameterException {
		Word expectedWord = new Word(KNOWN_WORD, enLanguage);

		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(enLanguage);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock
						.findByLanguageAndName(enLanguage, KNOWN_WORD))
				.thenReturn(expectedWord);
		spellCheckerService.setWordRepository(wordRepositoryMock);

		Boolean wasFound = spellCheckerService
				.check(KNOWN_LANGUAGE, KNOWN_WORD);

		Assert.assertNotNull(wasFound);
		Assert.assertEquals(true, wasFound);
	}

	@Test
	public void testCheckWithUnknownWord()
			throws SpellCheckerInvalidParameterException {

		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(enLanguage);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock.findByLanguageAndName(enLanguage,
						UNKNOWN_WORD)).thenReturn(null);
		spellCheckerService.setWordRepository(wordRepositoryMock);

		Boolean wasFound = spellCheckerService.check(KNOWN_LANGUAGE,
				UNKNOWN_WORD);

		Assert.assertNotNull(wasFound);
		Assert.assertEquals(false, wasFound);
	}

	@Test
	public void testAddWordWithNewLanguage() {
		Language newLanguage = new Language(UNKNOWN_LANGUAGE);
		Word word = new Word(KNOWN_WORD, newLanguage);

		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(UNKNOWN_LANGUAGE))
				.thenReturn(null);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock.findByLanguageAndName(newLanguage,
						KNOWN_WORD)).thenReturn(word);
		spellCheckerService.setWordRepository(wordRepositoryMock);

		Boolean isAdded = spellCheckerService.addWord(UNKNOWN_LANGUAGE,
				UNKNOWN_WORD);

		assertThat(isAdded).isNotNull();
		assertThat(isAdded).isEqualTo(true);

		verify(languageRepositoryMock, times(1)).save(
				Mockito.any(Language.class));
		verify(wordRepositoryMock, times(1)).save(Mockito.any(Word.class));
	}

	@Test
	public void testAddWordWithNullLanguage() {
		SpellCheckerInvalidParameterException exception = null;
		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(null)).thenReturn(
				null);

		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		try {
			spellCheckerService.addWord(null, UNKNOWN_WORD);
		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.INVALID_LANGUAGE.toString(),
				exception.getMessage());
	}

	@Test
	public void testAddWordWithEmptyLanguage() {
		SpellCheckerInvalidParameterException exception = null;
		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(null)).thenReturn(
				null);

		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		try {
			spellCheckerService.addWord("", UNKNOWN_WORD);
		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.INVALID_LANGUAGE.toString(),
				exception.getMessage());
	}

	@Test
	public void testAddWordWithNullName() {
		SpellCheckerInvalidParameterException exception = null;
		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(enLanguage);

		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		try {
			spellCheckerService.addWord(KNOWN_LANGUAGE, null);
		} catch (SpellCheckerInvalidParameterException e) {
			exception = e;
		}
		Assert.assertNotNull(exception);
		Assert.assertEquals(
				SpellCheckerExceptionStatus.INVALID_WORD.toString(),
				exception.getMessage());
	}

	@Test
	public void testAddWordKnown() throws SpellCheckerInvalidParameterException {
		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(enLanguage);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock
						.findByLanguageAndName(enLanguage, KNOWN_WORD))
				.thenReturn(new Word(KNOWN_WORD, enLanguage));
		spellCheckerService.setWordRepository(wordRepositoryMock);

		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		Boolean isAdded = spellCheckerService.addWord(KNOWN_LANGUAGE,
				KNOWN_WORD);
		assertThat(isAdded).isEqualTo(false);
	}

	@Test
	public void testAddWordUnknown()
			throws SpellCheckerInvalidParameterException {
		LanguageRepository languageRepositoryMock = Mockito
				.mock(LanguageRepository.class);
		Mockito.when(languageRepositoryMock.findByShortCode(KNOWN_LANGUAGE))
				.thenReturn(enLanguage);
		spellCheckerService.setLanguageRepository(languageRepositoryMock);

		WordRepository wordRepositoryMock = Mockito.mock(WordRepository.class);
		Mockito.when(
				wordRepositoryMock.findByLanguageAndName(enLanguage,
						UNKNOWN_WORD)).thenReturn(null);
		spellCheckerService.setWordRepository(wordRepositoryMock);


		Boolean isAdded = spellCheckerService.addWord(KNOWN_LANGUAGE,
				UNKNOWN_WORD);

		assertThat(isAdded).isEqualTo(true);
	}

}
