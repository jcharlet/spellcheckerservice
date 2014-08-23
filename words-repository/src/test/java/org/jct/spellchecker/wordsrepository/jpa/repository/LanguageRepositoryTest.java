package org.jct.spellchecker.wordsrepository.jpa.repository;

import org.jct.spellchecker.wordsrepository.AbstractSpellCheckerDatabaseTest;
import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.junit.Assert;
import org.junit.Test;

public class LanguageRepositoryTest extends AbstractSpellCheckerDatabaseTest {

	@Test
	public void testGetLanguageByShortCodeWithNullShortCode() {
		Language language = languageRepository.findByShortCode(null);
		Assert.assertNull(language);
	}

	@Test
	public void testGetLanguageByShortCodeWithKnownShortCode() {
		Language language = languageRepository.findByShortCode("EN");
		Assert.assertNotNull(language);
		Assert.assertEquals("EN", language.getShortCode());
	}

	@Test
	public void testGetLanguageByShortCodeWithUnknownShortCode() {
		Language language = languageRepository.findByShortCode("X");
		Assert.assertNull(language);
	}

}
