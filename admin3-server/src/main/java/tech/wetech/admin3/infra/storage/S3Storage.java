package tech.wetech.admin3.infra.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import tech.wetech.admin3.sys.model.StorageConfig;

import java.io.InputStream;
import java.net.URI;

/**
 * @author cjbi
 */
public class S3Storage implements Storage {

  private final Logger log = LoggerFactory.getLogger(S3Storage.class);

  private final StorageConfig config;

  public S3Storage(StorageConfig config) {
    this.config = config;
  }

  private S3Client getS3Client() {
    return S3Client.builder()
      .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(config.getAccessKey(), config.getSecretKey())))
      .region(Region.AWS_GLOBAL)
      .endpointOverride(URI.create(config.getEndpoint()))
      .serviceConfiguration(config -> config
        .pathStyleAccessEnabled(true)
        .chunkedEncodingEnabled(false)
      )
      .build();
  }

  /**
   * 上传文件
   *
   * @param bucketName  bucket名称
   * @param objectName  文件名称
   * @param is      文件流
   * @param contentType 类型
   * @param contentLength 大小
   * @see <a href= "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/PutObject">AWS
   * API Documentation</a>
   */
  public PutObjectResponse putObject(String bucketName, String objectName, InputStream is,
                                     String contentType, long contentLength) {
    try (S3Client s3Client = getS3Client()) {
      return s3Client.putObject(
        req -> req.bucket(bucketName).key(objectName).contentType(contentType),
        RequestBody.fromInputStream(is, contentLength)
      );
    }
  }

  @Override
  public String getId() {
    return config.getStorageId();
  }

  @Override
  public void store(InputStream inputStream, long contentLength, String contentType, String keyName) {
    String bucketName = config.getBucketName();

    if (!doesBucketExist(bucketName)) {
      createBucket(bucketName);
    }
    putObject(bucketName, keyName, inputStream, contentType, contentLength);
  }

  private void createBucket(String bucketName) {
    try (S3Client s3Client = getS3Client()) {
      s3Client.createBucket(req -> req.bucket(bucketName));
    }
  }

  private boolean doesBucketExist(String bucketName) {
    try (S3Client s3Client = getS3Client()) {
      s3Client.headBucket(req -> req.bucket(bucketName));
      return true;
    } catch (AwsServiceException ex) {
      if (ex.statusCode() == 404 | ex.statusCode() == 403 || ex.statusCode() == 301) {
        return false;
      }
      throw ex;
    } catch (Exception ex) {
      log.error("Cannot check access", ex);
      throw ex;
    }
  }

  @Override
  public InputStream getFileContent(String filename) {
    String bucketName = config.getBucketName();
    try (S3Client s3Client = getS3Client()) {
      return s3Client.getObject(req -> req.bucket(bucketName).key(filename));
    }
  }

  @Override
  public void delete(String filename) {
    String bucketName = config.getBucketName();
    S3Client s3Client = getS3Client();
    s3Client.deleteObject(req -> req.bucket(bucketName).key(filename));
    s3Client.close();
  }

  @Override
  public String getUrl(String filename) {
    if (config.getAddress() != null) {
      return config.getAddress() + filename;
    }
    String bucketName = config.getBucketName();
    try (S3Client s3Client = getS3Client()) {
      return s3Client.utilities()
        .getUrl(req -> req.bucket(bucketName).key(filename)).toString();
    }
  }

}
