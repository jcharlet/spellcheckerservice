package org.jct.spellchecker.wordsrepository.service.impl;

import org.jct.spellchecker.wordsrepository.exception.SpellCheckerExceptionStatus;
import org.jct.spellchecker.wordsrepository.exception.SpellCheckerInvalidParameterException;
import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.jct.spellchecker.wordsrepository.jpa.repository.LanguageRepository;
import org.jct.spellchecker.wordsrepository.jpa.repository.WordRepository;
import org.jct.spellchecker.wordsrepository.service.ISpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jct.spellchecker.wordsrepository.service.impl.ISpellCheckerService
	 * #check(org.jct.spellchecker.wordsrepository.jpa.entity.Language,
	 * java.lang.String)
	 */
	@Override
	public boolean check(String languageShortCode, String word)
			throws SpellCheckerInvalidParameterException {
		if (StringUtils.isEmpty(word)) {
			throw new SpellCheckerInvalidParameterException(
					SpellCheckerExceptionStatus.INVALID_WORD.toString());
		}
		if (StringUtils.isEmpty(languageShortCode)) {
			throw new SpellCheckerInvalidParameterException(
					SpellCheckerExceptionStatus.INVALID_LANGUAGE.toString());
		}

		Language language = languageRepository
				.findByShortCode(languageShortCode);
		if (language == null) {
			throw new SpellCheckerInvalidParameterException(
					SpellCheckerExceptionStatus.UNKNOWN_LANGUAGE.toString());
		}
		Word foundWord = wordRepository.findByLanguageAndName(language, word);
		if (foundWord == null) {
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
	public boolean addWord(String languageShortCode, String word)
			throws SpellCheckerInvalidParameterException {
		if (StringUtils.isEmpty(word)) {
			throw new SpellCheckerInvalidParameterException(
					SpellCheckerExceptionStatus.INVALID_WORD.toString());
		}
		if (StringUtils.isEmpty(languageShortCode)) {
			throw new SpellCheckerInvalidParameterException(
					SpellCheckerExceptionStatus.INVALID_LANGUAGE.toString());
		}

		Language language = languageRepository
				.findByShortCode(languageShortCode);
		if (language == null) {
			language = languageRepository.save(new Language(languageShortCode));
		} else {
			if (wordRepository.findByLanguageAndName(language, word) != null) {
				return false;
			}
		}
		wordRepository.save(new Word(word, language));
		return true;
	}

}
