package tech.wetech.admin3.infra.storage;


import java.io.InputStream;

/**
 * @author cjbi
 */
public interface Storage {

  String getId();

  /**
   * 存储一个文件对象
   *
   * @param inputStream   文件输入流
   * @param contentLength 文件长度
   * @param contentType   文件类型
   * @param filename      文件名
   */
  void store(InputStream inputStream, long contentLength, String contentType, String filename);

  InputStream getFileContent(String filename);

  void delete(String filename);

  String getUrl(String filename);
}
