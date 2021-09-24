package com.decathlon.alert.mechanism.system;

import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AlertManagementControllerTest {

	private static final String validInput = "{\n"
			+ "    \"teamName\": \"volleyBall\",\n"
			+ "    \"developers\": [\n"
			+ "        {\n"
			+ "            \"name\": \"Joe\",\n"
			+ "            \"phoneNumber\": \"90878268232\"\n"
			+ "        },\n"
			+ "        {\n"
			+ "            \"name\": \"Krish\",\n"
			+ "            \"phoneNumber\": \"12386322838\"\n"
			+ "        },\n"
			+ "        {\n"
			+ "            \"name\": \"Jack\",\n"
			+ "            \"phoneNumber\": \"4412312321344\"\n"
			+ "        }\n"
			+ "    ]\n"
			+ "}";

	private static final String inValidInput1 = "{\n"
			+ "    \"teamName\": \"volleyBall\",\n"
			+ "    \"developers\": null\n"
			+ "}";

	private static final String invalidInput2 = "{\n"
			+ "    \"teamName\": \"volleyBall\",\n"
			+ "    \"developers\": [\n"
			+ "        {\n"
			+ "            \"name\": null,\n"
			+ "            \"phoneNumber\": null\n"
			+ "        }\n"
			+ "    ]\n"
			+ "}";

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	void givenValidInput_whenCreateTeam_thenSuccess200() throws Exception {
		mockMvc.perform(
			post("/team")
			.content(validInput)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.teamId").isNotEmpty())
			.andExpect(jsonPath("$.resultInfo.id").value("00000"))
			.andExpect(jsonPath("$.resultInfo.code").value("SUCCESS"))
			.andExpect(jsonPath("$.resultInfo.message").value("Successful Request"));
	}

	@ParameterizedTest
	@ValueSource(strings = {inValidInput1, invalidInput2})
	void givenInValidInput_whenCreateTeam_thenReturn400() throws Exception {
		mockMvc.perform(
			post("/team")
			.content(inValidInput1)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.resultInfo.id").value("00001"))
			.andExpect(jsonPath("$.resultInfo.code").value("PARAM_ILLEGAL"))
			.andExpect(jsonPath("$.resultInfo.message").value("Illegal parameters. For example, non-numeric input, invalid date"));
	}

	@Test
	void givenValidInput_whenSendAlert_thenSuccess200() throws Exception {
		String response = mockMvc.perform(
				post("/team")
				.content(validInput)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.teamId").isNotEmpty())
				.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
		JSONObject jsonResponse = new JSONObject(response);
		Long teamId = jsonResponse.getJSONObject("data").getLong("teamId");

		mockMvc.perform(
			post("/{teamId}/alert", teamId)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
	}

	@Test
	void givenTeamId_whenDeveloperNotExist_thenReturn400() throws Exception {
		mockMvc.perform(
			post("/{teamId}/alert", 1111111)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.resultInfo.id").value("00006"))
			.andExpect(jsonPath("$.resultInfo.code").value("DEV_NOT_FOUND"))
			.andExpect(jsonPath("$.resultInfo.message").value("Developer details are empty for a requested team"));
	}

	@Test
	void givenInvalidTeamId_whenSendAlert_thenReturn400() throws Exception {
		mockMvc.perform(
		post("/{teamId}/alert", "232shs")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.resultInfo.id").value("00001"))
		.andExpect(jsonPath("$.resultInfo.code").value("PARAM_ILLEGAL"))
		.andExpect(jsonPath("$.resultInfo.message").value("Illegal parameters. For example, non-numeric input, invalid date"));
	}

}
