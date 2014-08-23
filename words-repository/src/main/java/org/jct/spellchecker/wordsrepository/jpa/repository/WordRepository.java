package org.jct.spellchecker.wordsrepository.jpa.repository;

import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.jct.spellchecker.wordsrepository.jpa.entity.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Long> {

	Word findByLanguageAndName(Language enLanguage, String name);
}