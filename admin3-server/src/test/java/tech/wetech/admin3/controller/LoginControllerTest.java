package tech.wetech.admin3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.wetech.admin3.AbstractIntegrationTest;
import tech.wetech.admin3.common.JsonUtils;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.wetech.admin3.Constants.TOKEN;
import static tech.wetech.admin3.Constants.TOKEN_HEADER_NAME;

/**
 * @author cjbi
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void testLoginWhenSuccessThenStatus200() throws Exception {
    mvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
             {
               "username": "admin",
               "password": "123456"
              }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("username", is("admin")));
  }

  @Test
  void testLoginWhenPasswordErrorThenStatus401() throws Exception {
    mvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
             {
               "username": "admin",
               "password": "admin"
              }
          """))
      .andExpect(status().is4xxClientError())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("code", is(1004)));
  }

  @Test
  void testLogout() throws Exception {
    String json = mvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
             {
               "username": "guest",
               "password": "guest"
              }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andReturn().getResponse().getContentAsString();
    mvc.perform(post("/logout").header(TOKEN_HEADER_NAME, JsonUtils.parseToMap(json).get("token"))).andExpect(status().isOk());
  }

  @Test
  void testUserInfo() throws Exception {
    mvc.perform(get("/userinfo")
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("username", is("admin")));
  }

}
