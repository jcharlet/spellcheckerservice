package org.jct.spellchecker.wordsrepository.cli.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
public class SpellCheckerCommands implements CommandMarker {

	private static final String DISCARD_COMMAND = "discard";

	private static final String ADD_COMMAND = "add";

	private static final String CHECK_COMMAND = "check";

	protected final Logger LOG = Logger.getLogger(getClass().getName());

	private boolean isAddOrDiscardCommandAvailable = false;

	private List<String> listOfWordsToProcess;
	private List<String> listOfDiscardedWords;
	private List<String> listOfAddedWords;

	private Integer wordIndex = null;

	@CliAvailabilityIndicator({ CHECK_COMMAND })
	public boolean isCheckCommandAvailable() {
		return !isAddOrDiscardCommandAvailable;
	}

	@CliCommand(value = CHECK_COMMAND, help = "add the word requested to the common repository")
	public String doCheckCommand(
			@CliOption(key = { "fileName" }, mandatory = true, help = "the name of the file to check") final String fileName) {
		check(fileName);
		StringBuilder buf = new StringBuilder();
		buf.append(getNumberOfWordsString());
		buf.append(getAddWordQuestionString());
		return buf.toString();
	}

	private String getNumberOfWordsString() {
		StringBuilder buf = new StringBuilder();
		return buf.append("There are ").append(listOfWordsToProcess.size())
				.append(" words to process.").append(OsUtils.LINE_SEPARATOR)
				.append(OsUtils.LINE_SEPARATOR)
				.toString();
	}

	private String getAddWordQuestionString() {
		if (wordIndex == null) {
			wordIndex = 0;
		} else {
			wordIndex++;
		}
		StringBuilder buf = new StringBuilder();
		buf.append("Do you want to add ")
				.append(listOfWordsToProcess.get(wordIndex))
				.append(" to the common repository?")
				.append(OsUtils.LINE_SEPARATOR);
		return buf.toString();
	}

	private void check(String fileName) {
		LOG.log(Level.INFO, "file processed: " + fileName);
		wordIndex = null;
		this.isAddOrDiscardCommandAvailable = true;
		this.listOfWordsToProcess = Arrays.asList("this", "is", "a", "test");
		this.listOfDiscardedWords = new ArrayList<String>();
		this.listOfAddedWords = new ArrayList<String>();
	}

	@CliAvailabilityIndicator({ ADD_COMMAND })
	public boolean isAddCommandAvailable() {
		return isAddOrDiscardCommandAvailable;
	}

	@CliCommand(value = ADD_COMMAND, help = "add the word requested to the common repository")
	public String doAddCommand() {
		StringBuilder buf = new StringBuilder();

		buf.append(add());
		if (wordIndex < listOfWordsToProcess.size() - 1) {
			buf.append(getAddWordQuestionString());
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private Object add() {
		listOfAddedWords.add(listOfWordsToProcess.get(wordIndex));
		StringBuilder buf = new StringBuilder();
		buf.append(listOfWordsToProcess.get(wordIndex) + " added")
				.append(OsUtils.LINE_SEPARATOR).append(OsUtils.LINE_SEPARATOR);
		return buf.toString();
	}

	@CliAvailabilityIndicator({ DISCARD_COMMAND })
	public boolean isDiscardCommandAvailable() {
		return isAddOrDiscardCommandAvailable;
	}
	
	@CliCommand(value = DISCARD_COMMAND, help = "discard the word requested and move to the next")
	public String doDiscardCommand() {
		StringBuilder buf = new StringBuilder();
		discard();
		buf.append(listOfWordsToProcess.get(wordIndex) + " discarded")
				.append(OsUtils.LINE_SEPARATOR).append(OsUtils.LINE_SEPARATOR);
		if (wordIndex < listOfWordsToProcess.size() - 1) {
			buf.append(getAddWordQuestionString());
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private void discard() {
		listOfDiscardedWords.add(listOfWordsToProcess.get(wordIndex));

	}

	private Object completeCheckTreatment() {
		this.isAddOrDiscardCommandAvailable = false;
		this.listOfWordsToProcess = null;
		this.listOfDiscardedWords = null;
		this.listOfWordsToProcess = null;

		StringBuilder buf = new StringBuilder();
		buf.append("Treatment Completed").append(OsUtils.LINE_SEPARATOR);
		buf.append("Added words: ");
		for (String word : listOfAddedWords) {
			buf.append(word).append(" ");
		}
		buf.append(OsUtils.LINE_SEPARATOR);
		buf.append("Discarded words: ");
		for (String word : listOfDiscardedWords) {
			buf.append(word).append(" ");
		}
		return buf.toString();
	}

}
