package org.jct.spellchecker.wordsrepository;

import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.jct.spellchecker.wordsrepository.jpa.repository.LanguageRepository;
import org.jct.spellchecker.wordsrepository.jpa.repository.WordRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public abstract class AbstractSpellCheckerDatabaseTest {

	@Autowired
	protected WordRepository wordRepository;

	@Autowired
	protected LanguageRepository languageRepository;

	protected static Language enLanguage;

	protected static Word testWord;

	@Before
	public void initData() {
		enLanguage = new Language("EN");
		languageRepository.save(enLanguage);

		testWord = new Word("test", enLanguage);
		wordRepository.save(testWord);
	}

	@After
	public void removeDate() {
		wordRepository.delete(testWord);
		languageRepository.delete(enLanguage);
	}

}
