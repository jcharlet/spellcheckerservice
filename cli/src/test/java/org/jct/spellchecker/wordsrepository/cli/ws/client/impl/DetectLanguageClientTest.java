package org.jct.spellchecker.wordsrepository.cli.ws.client.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import org.jct.spellchecker.wordsrepository.cli.ws.client.IDetectLanguageClient;
import org.junit.Before;
import org.springframework.shell.Bootstrap;

/**
 * Disabled not to contact distant api
 */
public class DetectLanguageClientTest {

	private IDetectLanguageClient detectLanguageClient;

	@Before
	public void initTest() throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		detectLanguageClient = bootstrap.getApplicationContext().getBean(
				IDetectLanguageClient.class);
	}

	// @Test
	public void testDetectLanguageEN() {
		String language = detectLanguageClient
				.detectLanguage("this is a test in english");
		assertThat(language).isNotNull();
		assertThat(language).isEqualTo("EN");
	}

	// @Test
	public void testDetectLanguageFR() {
		String language = detectLanguageClient
				.detectLanguage("c'est un test en français");
		assertThat(language).isNotNull();
		assertThat(language).isEqualTo("FR");
	}
}
