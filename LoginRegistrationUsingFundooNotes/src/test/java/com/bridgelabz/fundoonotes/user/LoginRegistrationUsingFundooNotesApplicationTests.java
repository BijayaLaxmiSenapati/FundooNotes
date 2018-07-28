package com.bridgelabz.fundoonotes.user;

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
public class LoginRegistrationUsingFundooNotesApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/*************************************
	 * login
	 *********************************************/
	@Test
	public void loginTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"bijaya.8434@gmail.com\", \"password\" : \"Mama1234?\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("successfully logged in with email:bijaya.8434@gmail.com"))
				.andExpect(jsonPath("$.status").value(1));

	}

	@Test
	public void loginTestWithNullValue() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"bijaya.8434@gmail.com\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("All fields should be filled"))
				.andExpect(jsonPath("$.status").value(-2));

	}

	@Test
	public void loginTestWithoutRegisteredEmail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"mama.8434@gmail.com\", \"password\" : \"Mama1234?\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists()).andExpect(jsonPath("$.message").value("Email id not present"))
				.andExpect(jsonPath("$.status").value(-2));

	}

	@Test
	public void loginTestWithIncorrectPassword() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"bijaya.8434@gmail.com\", \"password\" : \"Mmma1234?\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists()).andExpect(jsonPath("$.message").value("Wrong Password given"))
				.andExpect(jsonPath("$.status").value(-2));

	}

	@Test
	public void loginTestWithInvalidRegexOfEmail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"email\": \"mama.8434gmail.com\", \"password\" : \"Mama1234?\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Email Format not correct"))
				.andExpect(jsonPath("$.status").value(-2));

	}

	/**********************************************
	 * Register
	 ********************************************/
	@Test
	public void verifyRegistrationWithWrongName() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("successfully registered with email-id"))
				.andExpect(jsonPath("$.status").value(1));

	}

	@Test
	public void registrationTestWithWrongName() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"name\": \"Bi\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Name should have atleast 3 charecters"))
				.andExpect(jsonPath("$.status").value(-3));

	}

	@Test
	public void registrationTestWithNullField() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{\"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("All fields should be filled"))
				.andExpect(jsonPath("$.status").value(-3));

	}

	@Test
	public void registrationTestWithWrongContactNum() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"737714\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Contact number should have 10 digits"))
				.andExpect(jsonPath("$.status").value(-3));

	}

	@Test
	public void registrationTestWithUnequalPasswordField() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message")
				.value("Both 'password' and 'confirmPassword' field should have same value"))
				.andExpect(jsonPath("$.status").value(-3));

	}

	@Test
	public void registrationTestWithInvalidPassword() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6@gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama12\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Password should have atleast 8 charecters"))
				.andExpect(jsonPath("$.status").value(-3));

	}

	@Test
	public void registrationTestWithInvalidRegexOfEmail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"name\": \"Bijaya Laxmi Senapati\", \"email\" : \"simranbodra6gmail.com\", \"contactNumber\" : \"7377145005\", \"password\" : \"Mama1234?\", \"confirmPassword\" : \"Mama1234?\" }")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Email Format not correct"))
				.andExpect(jsonPath("$.status").value(-3));

	}
	/*************************************************forgotPassword*****************************************/
	@Test
	public void forgotPasswordTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/forgotpassword").contentType(MediaType.APPLICATION_JSON)
				.content("bijaya.8434@gmail.com")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("email sent for changing password"))
				.andExpect(jsonPath("$.status").value(1));
	}
	
	@Test
	public void resetPasswordTest() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/fundoo/resetPassword").contentType(MediaType.APPLICATION_JSON)
				.param("token",
				"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1YjUyYjgwNTQ5MzA0ZDM1Y2ZmZWYyOGYifQ.18aeyklbm4sIG_vFtkBRfQ7xJMaO8L5Jo5qtqhLAmqVdyE5UCMvc8dhOPJosBjSdYlmkUjgj8sEz11piCdVxlw")
				.content("{ \"newPassword\": \"Mama1234?\", \"confirmNewPassword\" : \"Mama1234?\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("password of your account has been successfully changed"))
				.andExpect(jsonPath("$.status").value(1));
	}

}
