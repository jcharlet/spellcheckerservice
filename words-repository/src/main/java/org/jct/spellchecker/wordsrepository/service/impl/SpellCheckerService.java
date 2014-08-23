package org.jct.spellchecker.wordsrepository.service.impl;

import org.jct.spellchecker.wordsrepository.exception.ExceptionStatus;
import org.jct.spellchecker.wordsrepository.exception.SpellCheckerException;
import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.jct.spellchecker.wordsrepository.jpa.repository.LanguageRepository;
import org.jct.spellchecker.wordsrepository.jpa.repository.WordRepository;
import org.jct.spellchecker.wordsrepository.service.ISpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpellCheckerService implements ISpellCheckerService {

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	private LanguageRepository languageRepository;

	public void setWordRepository(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	public void setLanguageRepository(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}


	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.service.impl.ISpellCheckerService#check(org.jct.spellchecker.wordsrepository.jpa.entity.Language, java.lang.String)
	 */
	@Override
	public boolean check(String shortCode, String name)
			throws SpellCheckerException {
		if (name == null) {
			throw new SpellCheckerException(ExceptionStatus.INVALID_WORD);
		}

		Language language = languageRepository.findByShortCode(shortCode);
		if (language == null) {
			throw new SpellCheckerException(ExceptionStatus.INVALID_LANGUAGE);
		}
		Word word = wordRepository.findByLanguageAndName(language, name);
		if (word == null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jct.spellchecker.wordsrepository.service.impl.ISpellCheckerService
	 * #addWord(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addWord(String shortCode, String name)
			throws SpellCheckerException {
		// TODO Auto-generated method stub
		return false;
	}

}
