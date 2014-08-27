package org.jct.spellchecker.wordsrepository.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Word provided in a certain language
 * 
 */
@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "language_id")
	private Language language;

	public Word() {
	}

	public Word(String name, Language language) {
		super();
		this.name = name;
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public Language getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Word [id=").append(id).append(", name=").append(name)
				.append(", language=").append(language).append("]");
		return builder.toString();
	}


}
