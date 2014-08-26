package org.jct.spellchecker.wordsrepository.cli.ws.client.impl;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliException;
import org.jct.spellchecker.wordsrepository.cli.exception.SpellCheckerCliExceptionStatus;
import org.jct.spellchecker.wordsrepository.cli.ws.client.ISpellCheckerClient;
import org.jct.spellchecker.wordsrepository.cli.ws.client.model.SpellCheckerResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
// FIXME JCT code duplication
public class SpellCheckerClient implements ISpellCheckerClient {

	private static final String CHECK_PATH = "check";
	private static final String SPELL_CHECKER_SERVER_URL = "http://localhost:8080";
	private static final String ADD_PATH = "add";
	private Client client;

	@PostConstruct
	private void initWSClient() {
		client = ClientBuilder.newClient().register(MoxyJsonFeature.class);
	}

	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.ws.client.impl.ISpellCheckerClient#check(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean check(String language, String word) {
		WebTarget target = client.target(SPELL_CHECKER_SERVER_URL).path(
				CHECK_PATH);

		Response genericResponse = target.queryParam("word", word)
				.queryParam("language", language)
				.request(MediaType.APPLICATION_JSON_TYPE).get();
		try {
			SpellCheckerResponse addResponse = genericResponse
					.readEntity(SpellCheckerResponse.class);
			return addResponse.getValue();
		} catch (Exception e) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.WS_ERROR, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.jct.spellchecker.wordsrepository.cli.ws.client.impl.ISpellCheckerClient#add(java.lang.String, java.lang.String)
	 */
	@Override
	public Boolean add(String language, String word) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(SPELL_CHECKER_SERVER_URL).path(
				ADD_PATH);

		Response genericResponse = target.queryParam("word", word)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("language", language).get();
		try {
			SpellCheckerResponse addResponse = genericResponse
					.readEntity(SpellCheckerResponse.class);
			return addResponse.getValue();
		} catch (Exception e) {
			throw new SpellCheckerCliException(
					SpellCheckerCliExceptionStatus.WS_ERROR, e);
		}
	}

}
