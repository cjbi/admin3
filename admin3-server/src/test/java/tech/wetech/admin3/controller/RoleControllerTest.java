package tech.wetech.admin3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.wetech.admin3.AbstractIntegrationTest;
import tech.wetech.admin3.common.JsonUtils;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.wetech.admin3.Constants.TOKEN;
import static tech.wetech.admin3.Constants.TOKEN_HEADER_NAME;

/**
 * @author cjbi
 */
@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void testFindRoles() throws Exception {
    mvc.perform(get("/roles")
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));

  }

  @Test
  void testFindRoleUsers() throws Exception {
    mvc.perform(get("/roles/{roleId}/users", 1)
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("total", greaterThanOrEqualTo(1)));
  }

  @Test
  void testCreateRole() throws Exception {
    mvc.perform(post("/roles")
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
            "name": "测试角色223",
            "description": "测试角色223描述"
          }
          """))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("测试角色223")));
  }

  @Test
  void testChangeResources() throws Exception {
    mvc.perform(put("/roles/{roleId}/resources", 4)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
            "resourceIds": [3,4,5,6,7]
          }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("id", is(4)));
  }

  @Test
  void testChangeUsers() throws Exception {
    mvc.perform(put("/roles/{roleId}/users", 4)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
            "userIds": [202,203,204]
          }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("id", is(4)));
  }

  @Test
  void testUpdateRole() throws Exception {
    mvc.perform(put("/roles/{roleId}", 4)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
           {
            "name": "测试角色110",
            "description": "测试角色110描述"
          }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("测试角色110")));
  }

  @Test
  void testDeleteRole() throws Exception {
    String json = mvc.perform(post("/roles")
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
            "name": "测试角色删除",
            "description": "测试角色删除描述"
          }
          """))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("测试角色删除")))
      .andReturn().getResponse().getContentAsString();

    mvc.perform(delete("/roles/{roleId}", JsonUtils.parseToMap(json).get("id"))
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isNoContent());
  }
}
