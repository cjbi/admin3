package tech.wetech.admin3.infra.storage;


import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
public interface Storage {
  /**
   * 存储一个文件对象
   *
   * @param inputStream   文件输入流
   * @param contentLength 文件长度
   * @param contentType   文件类型
   * @param keyName       文件名
   */
  void store(InputStream inputStream, long contentLength, String contentType, String keyName);

  Stream<Path> getAll();

  File getFile(String filename);

  Resource loadAsResource(String filename);

  void delete(String filename);

  String getUrl(String filename);
}
