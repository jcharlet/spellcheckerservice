package org.jct.spellchecker.wordsrepository.cli.ws.client.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//TODO JCT: duplicate of WS object. to provide in a separate api module jar
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SpellCheckerResponse {
	@XmlElement
	private String word;
	@XmlElement
	private String action;
	@XmlElement
	private Boolean value;

	public SpellCheckerResponse() {
		super();
	}

	public SpellCheckerResponse(String word, String action, Boolean value) {
		super();
		this.word = word;
		this.action = action;
		this.value = value;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpellCheckerResponse [word=").append(word)
				.append(", action=").append(action).append(", value=")
				.append(value).append("]");
		return builder.toString();
	}

}
