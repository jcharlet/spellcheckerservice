package org.jct.spellchecker.wordsrepository.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Language identified by its shortCode ("en" for English)
 * 
 */
@Entity
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String shortCode;

	protected Language() {
	}

	public Language(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getShortCode() {
		return shortCode;
	}

	@Override
	public String toString() {
		return String.format("Language[id=%d, shortCode='%s']", id, shortCode);
	}

}
