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
	
	@Test
	public void createNote() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/notes/createNote")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{ \"title\": \"Bijaya Laxmi Senapati's BIRTHDAY\", \"description\" : \"Her Birthday is on 24th january\"}")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.title").exists())
				.andExpect(jsonPath("$.description").exists())
				.andExpect(jsonPath("$.createdAt").exists())
				.andExpect(jsonPath("$.isTrashed").exists())
				
				.andExpect(jsonPath("$.message").value("successfully logged in with email:bijaya.8434@gmail.com"))
				.andExpect(jsonPath("$.status").value(1));
				
	}

}
