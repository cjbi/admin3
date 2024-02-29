package tech.wetech.admin3.infra.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import tech.wetech.admin3.sys.model.StorageConfig;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author cjbi
 */
public class LocalStorage implements Storage {

  private final StorageConfig config;

  private final Path rootLocation;

  public LocalStorage(StorageConfig config) {
    this.config = config;

    this.rootLocation = Paths.get(config.getStoragePathWithEnv());
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public String getId() {
    return config.getStorageId();
  }

  @Override
  public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {
    try {
      Files.copy(inputStream, rootLocation.resolve(keyName), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + keyName, e);
    }
  }

  private Path get(String filename) {
    return rootLocation.resolve(filename);
  }

  public InputStream getFileContent(String filename) {
    try {
      Path path = get(filename);
      File file = path.toFile();
      if (!file.exists()) {
        return null;
      }
      return new FileInputStream(path.toFile());
    } catch (FileNotFoundException e) {
      return null;
    }
  }

  @Override
  public void delete(String filename) {
    Path file = get(filename);
    try {
      Files.delete(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getUrl(String keyName) {
    return config.getAddressWithEnv() + keyName;
  }
}
