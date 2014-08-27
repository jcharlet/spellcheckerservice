/*
 * Copyright 2011-2012 the original author or authors.
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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * @author Jarred Li
 * 
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyBannerProvider extends DefaultBannerProvider {

	public String getBanner() {
		StringBuffer buf = new StringBuffer();
		buf.append("======================================="
).append(
				OsUtils.LINE_SEPARATOR);
		buf.append("*                                     *"
).append(
				OsUtils.LINE_SEPARATOR);
		buf.append("*            Spell Checker            *"
).append(
				OsUtils.LINE_SEPARATOR);
		buf.append("*                                     *"
).append(
				OsUtils.LINE_SEPARATOR);
		buf.append("======================================="
).append(
				OsUtils.LINE_SEPARATOR);
		buf.append("Version:").append(this.getVersion());
		return buf.toString();
	}

	public String getVersion() {
		return "1.0";
	}

	public String getWelcomeMessage() {
		StringBuffer buf = new StringBuffer();
		buf.append(
				"To start with the spell checking, please provide the fileName of your file to parse")
				.append(OsUtils.LINE_SEPARATOR);
		buf.append(
				"with the following command: 'check --fileName myPath/to/myFile.txt'. The root path for the application is the folder from where you ran this application")
				.append(
OsUtils.LINE_SEPARATOR).append(OsUtils.LINE_SEPARATOR);
		buf.append(
				"you can quit the application at any time by typing 'exit'. type in 'help' to see other commands provided by Spring shell")
				.append(
				OsUtils.LINE_SEPARATOR);
		return buf.toString();
	}

	@Override
	public String getProviderName() {
		return "Hello World Banner";
	}
}