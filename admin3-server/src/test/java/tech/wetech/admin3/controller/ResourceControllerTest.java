package tech.wetech.admin3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.wetech.admin3.AbstractIntegrationTest;

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
public class ResourceControllerTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Test
  void testFindMenus() throws Exception {
    mvc.perform(get("/resources/menu")
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
  }

  @Test
  void testFindResourceTree() throws Exception {
    mvc.perform(get("/resources/tree")
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
  }

  @Test
  void testCreateResourceWhenTypeIsButton() throws Exception {
    mvc.perform(post("/resources")
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
            "name": "测试按钮资源",
            "type": "BUTTON",
            "permission": "test_case_node:create",
            "parentId": 1
          }
          """))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("测试按钮资源")));
  }

  @Test
  void testUpdateResource() throws Exception {
    mvc.perform(put("/resources/{resourceId}", 1001)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
           {
            "name": "测试按钮资源更新",
            "type": "BUTTON",
            "permission": "test_case_node2:update",
            "parentId": 1
          }
          """))
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("测试按钮资源更新")));
  }

  @Test
  void testDeleteResource() throws Exception {
    mvc.perform(delete("/resources/{resourceId}", 1002)
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isNoContent());
  }
}
