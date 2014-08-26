package org.jct.spellchecker.wordsrepository.cli.ws.client.model;

//TODO JCT: duplicate of WS object. to provide in a separate api module jar
public class SpellCheckerResponse {
	private String word;
	private String action;
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
