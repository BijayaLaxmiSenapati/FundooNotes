package com.bridgelabz.fundoonotes.note;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.bridgelabz.fundoonotes.LoginRegistrationUsingFundooNotesApplication;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LoginRegistrationUsingFundooNotesApplication.class)
@SpringBootTest
public class NoteControllerTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/*****************************************************
	 * createNote
	 ************************************************/
	@Test
	public void createNoteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/notes/create-note").contentType(MediaType.APPLICATION_JSON)
				.header("token",
						"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content(
						"{ \"title\": \"Bijaya Laxmi Senapati's BIRTHDAY\", \"description\" : \"Her Birthday is on 24th january\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("Bijaya Laxmi Senapati's BIRTHDAY"))
				.andExpect(jsonPath("$.description").value("Her Birthday is on 24th january"))
				.andExpect(jsonPath("$.*", hasSize(10))).andDo(print());
	}

	@Test
	public void createNoteTestWithWrongReminder() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/notes/create-note").contentType(MediaType.APPLICATION_JSON)
				.header("token",
						"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content(
						"{ \"reminder\": \"2017-07-25T04:27:13.871Z\",\"title\": \"BHOOMI\", \"description\" : \"She is my girlfriend\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("reminder should not be earlier from now"))
				.andExpect(jsonPath("$.status").value(-60)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

	@Test
	public void createNoteTestWithEmptyTitleAndDescription() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/notes/create-note").contentType(MediaType.APPLICATION_JSON)
				.header("token",
						"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content("{ \"reminder\": \"2017-07-25T04:27:13.871Z\",\"color\": \"black\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").value("Both title and description fields should not be empty"))
				.andExpect(jsonPath("$.status").value(-20)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

	/*****************************************************
	 * updateNote
	 *****************************************************/
	@Test
	public void updateNoteTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/notes/update-note").contentType(MediaType.APPLICATION_JSON).header(
				"token",
				"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content(
						"{ \"id\": \"5b580a4149304d2c70c04540\",\"description\" : \"Her Birthday is on 24th january(updated)\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Note Successfully updated"))
				.andExpect(jsonPath("$.status").value(1)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

	@Test
	public void updateNoteTestWithoutNoteId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/notes/update-note").contentType(MediaType.APPLICATION_JSON).header(
				"token",
				"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content("{ \"description\" : \"Her Birthday is on 24th january(updated1)\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("For deletion of note \"id\" is needed"))
				.andExpect(jsonPath("$.status").value(-10)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

	/*********************************************
	 * getAllNotes
	 * 
	 * @throws Exception
	 *********************************************************/

	@Test
	public void getAllNotesTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/notes/get-all-notes").contentType(MediaType.APPLICATION_JSON)
				.header("token",
						"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

	/*****************************************************
	 * trashNote
	 **************************************************************/
	public void trashNoteTestWithoutNoteId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/notes/trash-note/{id}", "5b580a4149304d2c70c04540")
				.contentType(MediaType.APPLICATION_JSON)
				.header("token",
						"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").value("Note added to trash"))
				.andExpect(jsonPath("$.status").value(1)).andExpect(jsonPath("$.*", hasSize(2))).andDo(print());
	}

}
