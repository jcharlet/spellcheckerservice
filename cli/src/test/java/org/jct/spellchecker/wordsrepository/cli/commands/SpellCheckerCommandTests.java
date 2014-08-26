/*
 * Copyright 2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jct.spellchecker.wordsrepository.cli.commands;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.service.impl.SpellCheckerCliService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;
import org.springframework.shell.support.util.OsUtils;

public class SpellCheckerCommandTests {

	private static final String TEST_FILE = "testFile";

	private Bootstrap bootstrap;

	private SpellCheckerCliService spellCheckerCliService;

	@Before
	public void mockServiceLayer() {
		MockitoAnnotations.initMocks(this);
		bootstrap = new Bootstrap();

		spellCheckerCliService = Mockito.mock(SpellCheckerCliService.class);

		bootstrap.getApplicationContext().getBean(SpellCheckerCommands.class)
				.setService(spellCheckerCliService);
	}

	@Test
	public void testSimpleTreatment() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell
				.executeCommand("check --fileName " + TEST_FILE);
		assertEquals(true, cr.isSuccess());

		assertEquals(
				new StringBuilder("There are 4 words to process.")
						.append(OsUtils.LINE_SEPARATOR)
						.append(OsUtils.LINE_SEPARATOR)
						.append("Do you want to add this to the common repository?")
						.append(OsUtils.LINE_SEPARATOR).toString(),
				cr.getResult());
	}

	@Test
	public void testAddWhenNotAuthorized() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("add");

		assertEquals(false, cr.isSuccess());
	}

	@Test
	public void testDiscardWhenNotAuthorized() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("discard");

		assertEquals(false, cr.isSuccess());
	}

	@Test
	public void testCheckWhenNotAuthorized() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("check --fileName testFile");

		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("check --fileName testFile");

		assertEquals(false, cr.isSuccess());
	}

	@Test
	public void testAddTreatment() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("check --fileName testFile");

		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("add");

		assertEquals(true, cr.isSuccess());
		assertEquals(
				new StringBuilder("this added")
						.append(OsUtils.LINE_SEPARATOR)
						.append(OsUtils.LINE_SEPARATOR)
						.append("Do you want to add is to the common repository?")
						.append(OsUtils.LINE_SEPARATOR).toString(),
				cr.getResult());
	}

	@Test
	public void testDiscardTreatment() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("check --fileName testFile");

		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("discard");

		assertEquals(true, cr.isSuccess());
		assertEquals(
				new StringBuilder("this discarded")
						.append(OsUtils.LINE_SEPARATOR)
						.append(OsUtils.LINE_SEPARATOR)
						.append("Do you want to add is to the common repository?")
						.append(OsUtils.LINE_SEPARATOR).toString(),
				cr.getResult());
	}

	@Test
	public void testCompleteTreatment() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				Arrays.asList("this", "is", "a", "test"));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell.executeCommand("check --fileName testFile");

		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("add");
		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("discard");
		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("add");
		assertEquals(true, cr.isSuccess());

		cr = shell.executeCommand("add");
		assertEquals(true, cr.isSuccess());

		assertThat(cr.getResult()).isEqualTo(
				new StringBuilder("test added").append(OsUtils.LINE_SEPARATOR)
						.append(OsUtils.LINE_SEPARATOR)
						.append("Treatment Completed")
						.append(OsUtils.LINE_SEPARATOR)
						.append("Added words: this a test ")
						.append(OsUtils.LINE_SEPARATOR)
						.append("Discarded words: is ").toString());
	}

	@Test
	public void testEmptyFile() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenThrow(
				new SpellCheckerCliException(
						SpellCheckerCliExceptionStatus.FILE_NOT_FOUND));

		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell
				.executeCommand("check --fileName fileNotExisting");

		assertEquals(true, cr.isSuccess());
		assertThat(cr.getResult()).isEqualTo(
				"An error occured during the treatment: "
						+ SpellCheckerCliExceptionStatus.FILE_NOT_FOUND
								.toString());
	}

	@Test
	public void testFileWithAllWordsChecked() {
		when(spellCheckerCliService.checkFile(Mockito.anyString())).thenReturn(
				new ArrayList<String>());
		JLineShellComponent shell = bootstrap.getJLineShellComponent();

		CommandResult cr = shell
				.executeCommand("check --fileName fileNotExisting");

		assertEquals(true, cr.isSuccess());
		assertThat(cr.getResult()).isEqualTo(
				new StringBuilder("There are 0 words to process.")
						.append(OsUtils.LINE_SEPARATOR)
						.append(OsUtils.LINE_SEPARATOR)
						.append("All words checked").toString());
	}
}
