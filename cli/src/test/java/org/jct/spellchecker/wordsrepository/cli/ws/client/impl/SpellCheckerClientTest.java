package org.jct.spellchecker.wordsrepository.cli.ws.client.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.Bootstrap;

public class SpellCheckerClientTest {

	private ISpellCheckerClient spellCheckerClient;

	@Before
	public void initTest() throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		spellCheckerClient = bootstrap.getApplicationContext().getBean(
				ISpellCheckerClient.class);
	}

	@Test
	public void testCheckKnownWord() {
		Boolean isChecked = spellCheckerClient.check("EN", "test");
		assertThat(isChecked).isNotNull();
		assertThat(isChecked).isEqualTo(true);
	}

	@Test
	public void testCheckUnknownWord() {
		Boolean isChecked = spellCheckerClient.check("EN", "X");
		assertThat(isChecked).isNotNull();
		assertThat(isChecked).isEqualTo(false);
	}

	@Test
	public void testAddAlreadyKnownWord() {
		Boolean isChecked = spellCheckerClient.add("EN", "test");
		assertThat(isChecked).isNotNull();
		assertThat(isChecked).isEqualTo(false);
	}

}
