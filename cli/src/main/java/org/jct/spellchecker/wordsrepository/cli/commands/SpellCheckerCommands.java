package org.jct.spellchecker.wordsrepository.cli.commands;

import java.util.LinkedHashSet;

import org.jct.spellchecker.wordsrepository.cli.domain.SpellCheckerCliProcessData;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.service.ISpellCheckerCliService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ISpellCheckerCliService service;

	public void setService(ISpellCheckerCliService service) {
		this.service = service;
	}

	@CliAvailabilityIndicator({ CHECK_COMMAND })
	public boolean isCheckCommandAvailable() {
		return !isAddOrDiscardCommandAvailable;
	}

	@CliCommand(value = CHECK_COMMAND, help = "add the word requested to the common repository")
	public String doCheckCommand(
			@CliOption(key = { "fileName" }, mandatory = true, help = "the name of the file to check") final String fileName) {

		StringBuilder buf = new StringBuilder();
		try {
			check(fileName);
		} catch (SpellCheckerCliException e) {
			buf.append("An error occured during the treatment: "
					+ e.getExceptionStatus());
			isAddOrDiscardCommandAvailable = false;
			return buf.toString();
		}
		buf.append(getNumberOfWordsMessage(this.data.getNumberOfElements()));
		if (this.data.hasNextWord()) {
			buf.append(getAddWordQuestionMessage(this.data.getCurrentWord()));
		} else {
			buf.append("All words checked");
		}
		return buf.toString();
	}

	private String getNumberOfWordsMessage(Integer numberOfElements) {
		StringBuilder buf = new StringBuilder();
		return buf.append("There are ").append(numberOfElements)
				.append(" words to process.").append(OsUtils.LINE_SEPARATOR)
				.append(OsUtils.LINE_SEPARATOR).toString();
	}

	private String getAddWordQuestionMessage(String word) {

		StringBuilder buf = new StringBuilder();
		buf.append("Do you want to add ").append(word)
				.append(" to the common repository?")
				.append(OsUtils.LINE_SEPARATOR);
		return buf.toString();
	}

	private void check(String fileName) {
		// LOG.log(Level.INFO, "file processed: ", fileName);
		LinkedHashSet<String> uniqueWords = null;
		uniqueWords = service.parseFile(fileName);
		String language = service.detectLanguage(uniqueWords);
		LinkedHashSet<String> unknownWords = service.checkWords(uniqueWords,
				language);
		data = new SpellCheckerCliProcessData(unknownWords, language);
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
		if (this.data.hasNextWord()) {
			data.nextWord();
			buf.append(getAddWordQuestionMessage(this.data.getCurrentWord()));
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private Object add() {
		this.service.addWord(this.data.getLanguage(),
				this.data.getCurrentWord());
		this.data.addCurrentWordToRepository();
		StringBuilder buf = new StringBuilder();
		buf.append(this.data.getCurrentWord()).append(" added")
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
		buf.append(this.data.getCurrentWord()).append(" discarded")
				.append(OsUtils.LINE_SEPARATOR).append(OsUtils.LINE_SEPARATOR);
		if (data.hasNextWord()) {
			data.nextWord();
			buf.append(getAddWordQuestionMessage(this.data.getCurrentWord()));
		} else {
			buf.append(completeCheckTreatment());
		}
		return buf.toString();
	}

	private void discard() {
		this.data.discardCurrentWord();

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
