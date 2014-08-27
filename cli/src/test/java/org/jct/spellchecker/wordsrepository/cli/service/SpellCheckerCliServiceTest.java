package org.jct.spellchecker.wordsrepository.cli.service;

import static org.fest.assertions.api.Assertions.assertThat;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.impl.SpellCheckerCliService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.Bootstrap;

public class SpellCheckerCliServiceTest {

	private SpellCheckerCliService spellCheckerCliService;

	@Before
	public void initTest() {
		Bootstrap bootstrap = new Bootstrap();
		spellCheckerCliService = (SpellCheckerCliService) bootstrap
				.getApplicationContext().getBean(ISpellCheckerCliService.class);
	}

	@Test
	public void testReadUnknownFile() {
		SpellCheckerCliException exception = null;
		try {
			spellCheckerCliService.readFile("X");
		} catch (SpellCheckerCliException e) {
			exception = e;
		}
		assertThat(exception).isNotNull();
		assertThat(exception.getExceptionStatus()).isEqualTo(
				SpellCheckerCliExceptionStatus.FILE_NOT_FOUND);
	}

	@Test
	public void testReadKnownFile() {
		String text = spellCheckerCliService.readFile("test.txt");
		assertThat(text).isNotNull();
		assertThat(text).isEqualTo("this is a test file");
	}

	@Test
	public void testParseFileWithTextWithSpecialChars() {
		String[] listOfWords = spellCheckerCliService
				.removeUnwantedCharsFromText("sacrée, français! 42; l'étan.");
		String[] expected = { "sacrée", "français", "l", "étan" };
		assertThat(listOfWords).isNotNull();
		assertThat(listOfWords).isEqualTo(expected);
	}

}
