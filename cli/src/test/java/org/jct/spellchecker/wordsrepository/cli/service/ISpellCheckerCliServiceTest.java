package org.jct.spellchecker.wordsrepository.cli.service;

import static org.fest.assertions.api.Assertions.assertThat;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.Bootstrap;

public class ISpellCheckerCliServiceTest {

	private ISpellCheckerCliService spellCheckerCliService;

	@Before
	public void initTest() {
		Bootstrap bootstrap = new Bootstrap();
		spellCheckerCliService = bootstrap.getApplicationContext().getBean(
				ISpellCheckerCliService.class);
	}

	@Test
	public void testCheckUnknownFile() {
		SpellCheckerCliException exception = null;
		try {
			spellCheckerCliService.readFile("X");
		} catch (SpellCheckerCliException e) {
			exception = e;
		}
		assertThat(exception).isNotNull();
		assertThat(exception.getExceptionStatus()).isEqualTo(
				SpellCheckerCliExceptionStatus.READING_ERROR);
	}

	@Test
	public void testCheckKnownFile() {
		String text = spellCheckerCliService.readFile("test.txt");
		assertThat(text).isNotNull();
		assertThat(text).isEqualTo("this is a test file");
	}

}
