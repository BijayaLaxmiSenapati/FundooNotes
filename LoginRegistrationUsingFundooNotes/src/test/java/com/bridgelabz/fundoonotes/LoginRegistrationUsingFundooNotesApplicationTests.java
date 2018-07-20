package com.bridgelabz.fundoonotes;

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


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = LoginRegistrationUsingFundooNotesApplication.class)
@SpringBootTest
public class LoginRegistrationUsingFundooNotesApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/*@Test
	public void verifyRegistrationWithWrongName() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("successfully registered with email-id"))
				.andExpect(jsonPath("$.status").value(1));
				
	}*/
	
	@Test
	public void loginTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{ \"email\": \"bijaya.8434@gmail.com\", \"password\" : \"Mama1234?\"}")
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("successfully logged in with email:bijaya.8434@gmail.com"))
				.andExpect(jsonPath("$.status").value(1));
				
	}
	

}
