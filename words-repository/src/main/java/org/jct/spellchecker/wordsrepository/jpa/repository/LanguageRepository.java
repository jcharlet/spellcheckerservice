package org.jct.spellchecker.wordsrepository.jpa.repository;

import org.jct.spellchecker.wordsrepository.jpa.entity.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Long> {

	Language findByShortCode(String shortCode);
}