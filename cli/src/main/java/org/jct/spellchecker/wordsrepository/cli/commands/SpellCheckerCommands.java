package org.jct.spellchecker.wordsrepository.cli.commands;

import java.util.Arrays;

import org.jct.spellchecker.wordsrepository.cli.domain.SpellCheckerCliProcessData;
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

	// protected final Logger LOG = Logger.getLogger(getClass().getName());

	private boolean isAddOrDiscardCommandAvailable = false;

	private SpellCheckerCliProcessData data;

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
		return buf.append("There are ")
				.append(this.data.getListOfWordsToProcess().size())
				.append(" words to process.").append(OsUtils.LINE_SEPARATOR)
				.append(OsUtils.LINE_SEPARATOR).toString();
	}

	private String getAddWordQuestionString() {
		if (wordIndex == null) {
			wordIndex = 0;
		} else {
			wordIndex++;
		}
		StringBuilder buf = new StringBuilder();
		buf.append("Do you want to add ")
				.append(this.data.getWordFromIndex(wordIndex))
				.append(" to the common repository?")
				.append(OsUtils.LINE_SEPARATOR);
		return buf.toString();
	}

	private void check(String fileName) {
		// LOG.log(Level.INFO, "file processed: ", fileName);
		data = new SpellCheckerCliProcessData(Arrays.asList("this", "is", "a",
				"test"));
		wordIndex = null;
		this.isAddOrDiscardCommandAvailable = true;
	}

	@CliAvailabilityIndicator({ ADD_COMMAND })
	public boolean isAddCommandAvailable() {
		return isAddOrDiscardCommandAvailable;
	}

	@CliCommand(value = ADD_COMMAND, help = "add the word requested to the common repository")
	public String doAddCommand() {
		StringBuilder buf = new StringBuilder();

		buf.append(add());
		if (wordIndex < this.data.getListOfWordsToProcess().size() - 1) {
			buf.append(getAddWordQuestionString());
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private Object add() {
		this.data.addWordToRepository(this.data.getWordFromIndex(wordIndex));
		StringBuilder buf = new StringBuilder();
		buf.append(this.data.getWordFromIndex(wordIndex)).append(" added")
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
		buf.append(this.data.getWordFromIndex(wordIndex)).append(" discarded")
				.append(OsUtils.LINE_SEPARATOR).append(OsUtils.LINE_SEPARATOR);
		if (wordIndex < this.data.getListOfWordsToProcess().size() - 1) {
			buf.append(getAddWordQuestionString());
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private void discard() {
		this.data.discardWord(this.data.getWordFromIndex(wordIndex));

	}

	private Object completeCheckTreatment() {
		this.isAddOrDiscardCommandAvailable = false;

		StringBuilder buf = new StringBuilder();
		buf.append("Treatment Completed").append(OsUtils.LINE_SEPARATOR);
		buf.append("Added words: ");
		for (String word : this.data.getListOfAddedWords()) {
			buf.append(word).append(" ");
		}
		buf.append(OsUtils.LINE_SEPARATOR);
		buf.append("Discarded words: ");
		for (String word : this.data.getListOfDiscardedWords()) {
			buf.append(word).append(" ");
		}
		this.data = null;
		return buf.toString();
	}

}
