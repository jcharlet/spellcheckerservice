package org.jct.spellchecker.wordsrepository;

import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.repository.LanguageRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class LanguageRepositoryTest {

	@Autowired
	LanguageRepository repository;

	@Test
	public void testGetLanguageByShortCode(){
		Language enLanguage = repository.findByShortCode("EN");
		Assert.assertNotNull(enLanguage);
		Assert.assertEquals("EN", enLanguage.getShortCode());
	}

}
