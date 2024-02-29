package tech.wetech.admin3.controller;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tech.wetech.admin3.AbstractIntegrationTest;
import tech.wetech.admin3.common.JsonUtils;

import java.util.Map;

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
@Testcontainers
public class StorageControllerTest extends AbstractIntegrationTest {

  @Autowired
  private MockMvc mvc;

  @Container
  static MinIOContainer minIO = new MinIOContainer("minio/minio:RELEASE.2023-09-04T19-57-37Z");

  @Test
  void testFindConfigList() throws Exception {
    mvc.perform(get("/storage/configs")
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isOk())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
  }

  @Test
  @Order(0)
  void testUpload() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
      "files",
      "hello.txt",
      MediaType.TEXT_PLAIN_VALUE,
      "Hello, World!".getBytes()
    );
    mvc.perform(multipart("/storage/upload")
      .file(file)
      .header(TOKEN_HEADER_NAME, TOKEN)
    ).andExpect(status().isOk());
  }

  @Test
  void testUploadToMinIO() throws Exception {
    String json = mvc.perform(post("/storage/configs")
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content(String.format("""
           {
               "accessKey": "%s",
               "secretKey": "%s",
               "address": null,
               "bucketName": "test-bucket",
               "createTime": "",
               "createUser": "",
               "endpoint": "%s",
               "isDefault": false,
               "name": "MinIO",
               "storagePath": "files",
               "type": "OSS"
          }
          """, minIO.getUserName(), minIO.getPassword(), minIO.getS3URL())))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("MinIO")))
      .andReturn().getResponse().getContentAsString();
    String storageId = (String) JsonUtils.parseToMap(json).get("storageId");


    MockMultipartFile file = new MockMultipartFile(
      "files",
      "hello.txt",
      MediaType.TEXT_PLAIN_VALUE,
      "Hello, World!".getBytes()
    );
    String json2 = mvc.perform(multipart("/storage/upload")
      .file(file)
      .param("storageId", storageId)
      .header(TOKEN_HEADER_NAME, TOKEN)
    ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    Map<?, ?> fileMap = JsonUtils.parseToList(json2, Map.class).getFirst();
    String fileKey = (String) fileMap.get("key");
    mvc.perform(get("/storage/download/{key}", fileKey)
      .header(TOKEN_HEADER_NAME, TOKEN)
    ).andExpect(status().isOk());

//    String fileUrl = (String) fileMap.get("url");
//    HttpClient client = HttpClient.newHttpClient();
//    HttpRequest request = HttpRequest.newBuilder()
//      .uri(URI.create(fileUrl))
//      .GET()
//      .build();
//    HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.discarding());
//    Assertions.assertEquals(200, response.statusCode());
  }

  @Test
  void testCreateAndThenUpdateElseEndDelete() throws Exception {
    String json = mvc.perform(post("/storage/configs")
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
           {
               "accessKey": "111111111111",
               "address": "https://metacode-dev.wetech.tech",
               "bucketName": "metacode-dev",
               "createTime": "",
               "createUser": "",
               "endpoint": "oss-cn-shanghai.aliyuncs.com",
               "isDefault": false,
               "name": "阿里云",
               "secretKey": "2222222222",
               "storagePath": "files",
               "type": "OSS"
          }
          """))
      .andExpect(status().isCreated())
      .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("name", is("阿里云")))
      .andReturn().getResponse().getContentAsString();
    Number id = (Number) JsonUtils.parseToMap(json).get("id");

    mvc.perform(put("/storage/configs/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN)
        .content("""
          {
               "accessKey": "111111111111",
               "address": "https://metacode-dev.wetech.tech",
               "bucketName": "metacode-dev",
               "createTime": "",
               "createUser": "",
               "endpoint": "oss-cn-shanghai.aliyuncs.com",
               "isDefault": false,
               "name": "阿里云222",
               "secretKey": "2222222222",
               "storagePath": "files",
               "type": "OSS"
          }
          """))
      .andExpect(status().isOk())
      .andExpect(jsonPath("name", is("阿里云222")));

    mvc.perform(delete("/storage/configs/{id}", id)
        .header(TOKEN_HEADER_NAME, TOKEN)
      )
      .andExpect(status().isNoContent());
  }

  @Test
  void testMarkAsDefaultStorageConfig() throws Exception {
    mvc.perform(post("/storage/configs/{id}:markAsDefault", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .header(TOKEN_HEADER_NAME, TOKEN))
      .andExpect(status().isNoContent());
  }

}
