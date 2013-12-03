package de.webertise.simpleprp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.InvalidPathException;

import de.webertise.simpleprp.controller.UserController;
import de.webertise.simpleprp.service.TestDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:servlet-context-test.xml")
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webAppCtx;

    @Autowired
    private TestDataService tds;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webAppCtx).build();
        // List<String> result = tds.generate(10);
    }

    @Test
    public void thatCreatesUser() throws Exception {

        // define mvc call
        ResultActions ra = this.mockMvc.perform(post("/users")
                .content("{\"login\":\"bernfried11\",\"plainPassword\":\"password\",\"firstName\":\"Bernfried11\",\"lastName\":\"Howe\",\"email\":\"bernfried.howe11@me.com\"}")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        // define result action
        ra.andDo(print());
        ra.andExpect(status().isCreated());
        ra.andExpect(content().contentType("application/json"));

        // check result json string
        ResultMatcher rm = null;
        try {
            rm = jsonPath("$.firstName").value("Bernfried11");
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }

        ra.andExpect(rm);
    }

    @Test
    public void thatGetsExistingUserById() throws Exception {

        ResultActions ra = this.mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON));
        ra.andDo(print());
        ra.andExpect(status().isOk());
        ra.andExpect(content().contentType("application/json"));

        // check result json string
        ResultMatcher rm = null;
        try {
            rm = jsonPath("$.firstName").value("Hans0");
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }

        ra.andExpect(rm);
    }

    @Test
    public void thatGetsNotExistingUserById() throws Exception {

        ResultActions ra = this.mockMvc.perform(get("/users/22").accept(MediaType.APPLICATION_JSON));
        ra.andDo(print());
        ra.andExpect(status().isBadRequest());

    }

    @Test
    public void thatGetsAllUsers() throws Exception {

        ResultActions ra = this.mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON));
        ra.andDo(print());
        ra.andExpect(status().isOk());
        ra.andExpect(content().contentType("application/json"));

        // check result json string
        ResultMatcher rm = null;
        try {
            rm = jsonPath("$.[0].firstName").value("Hans0");
        } catch (InvalidPathException e) {
            e.printStackTrace();
        }

        ra.andExpect(rm);
    }

    @Test
    public void thatDeleteExistingUserById() throws Exception {

        ResultActions ra = this.mockMvc.perform(delete("/users/{id}", "1").accept(MediaType.APPLICATION_JSON));
        ra.andDo(print());
        ra.andExpect(status().isOk());

    }

    @Test
    public void thatDeleteNotExistingUserById() throws Exception {

        ResultActions ra = this.mockMvc.perform(delete("/users/{id}", "1").accept(MediaType.APPLICATION_JSON));
        ra.andDo(print());
        ra.andExpect(status().isBadRequest());

    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public UserController userController() {
            return new UserController();
        }

    }

}
