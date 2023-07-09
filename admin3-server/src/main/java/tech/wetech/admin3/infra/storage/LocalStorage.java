package tech.wetech.admin3.infra.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.sys.model.StorageConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
public class LocalStorage implements Storage {

  private final StorageConfig config;

  private final Path rootLocation;

  public LocalStorage(StorageConfig config) {
    this.config = config;

    this.rootLocation = Paths.get(config.getStoragePath());
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @Override
  public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {
    try {
      Files.copy(inputStream, rootLocation.resolve(keyName), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + keyName, e);
    }
  }

  @Override
  public Stream<Path> getAll() {
    try {
      return Files.walk(rootLocation, 1)
        .filter(path -> !path.equals(rootLocation))
        .map(path -> rootLocation.relativize(path));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read stored files", e);
    }
  }

  private Path get(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public File getFile(String filename) {
    Path path = get(filename);
    File file = path.toFile();
    if (!file.exists()) {
      throw new BusinessException(CommonResultStatus.RECORD_NOT_EXIST, "文件" + filename + "不存在");
    }
    return path.toFile();
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = get(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        return null;
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
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
    return config.getAddress() + keyName;
  }
}
