package tech.wetech.admin3.infra.storage;

import org.springframework.core.io.Resource;
import tech.wetech.admin3.sys.model.storage.StorageConfig;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
public class S3Storage implements Storage {

  private final StorageConfig config;

  public S3Storage(StorageConfig config) {
    this.config = config;
  }


  @Override
  public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {

  }

  @Override
  public Stream<Path> getAll() {
    return null;
  }

  @Override
  public File getFile(String filename) {
    return null;
  }

  @Override
  public Resource loadAsResource(String filename) {
    return null;
  }

  @Override
  public void delete(String filename) {

  }

  @Override
  public String getUrl(String filename) {
    return null;
  }
}
