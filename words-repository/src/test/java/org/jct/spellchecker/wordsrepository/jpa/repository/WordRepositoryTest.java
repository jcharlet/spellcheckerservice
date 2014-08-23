package org.jct.spellchecker.wordsrepository.jpa.repository;


import org.jct.spellchecker.wordsrepository.AbstractSpellCheckerDatabaseTest;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.junit.Assert;
import org.junit.Test;

public class WordRepositoryTest extends AbstractSpellCheckerDatabaseTest {

	@Test
	public void testFindByLanguageAndNameWithNullName() {
		Word word = wordRepository.findByLanguageAndName(enLanguage, null);
		Assert.assertNull(word);
	}

	@Test
	public void testFindByLanguageAndNameWithNullShortCode() {
		Word word = wordRepository.findByLanguageAndName(null,
				testWord.getName());
		Assert.assertNull(word);
	}

	@Test
	public void testFindByLanguageAndNameWithKnownName() {
		Word word = wordRepository.findByLanguageAndName(enLanguage,
				testWord.getName());
		Assert.assertNotNull(word);
		Assert.assertEquals(testWord.getName(), word.getName());
	}

	@Test
	public void testFindByLanguageAndNameWithUnknownName() {
		String name = "X";
		Word word = wordRepository.findByLanguageAndName(enLanguage, name);
		Assert.assertNull(word);
	}


}
