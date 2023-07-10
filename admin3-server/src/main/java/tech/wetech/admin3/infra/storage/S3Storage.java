package tech.wetech.admin3.infra.storage;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import tech.wetech.admin3.sys.model.StorageConfig;

import java.io.InputStream;

/**
 * @author cjbi
 */
public class S3Storage implements Storage {

  private final StorageConfig config;

  public S3Storage(StorageConfig config) {
    this.config = config;
  }


  @Override
  public String getId() {
    return config.getStorageId();
  }

  @Override
  public void store(InputStream inputStream, long contentLength, String contentType, String filename) {
    AmazonS3 s3client = getS3Client();
    String bucketName = config.getBucketName();
    if (!s3client.doesBucketExistV2(bucketName)) {
      s3client.createBucket(bucketName);
    }
    putObject(bucketName, filename, inputStream, contentLength, contentType);
  }

  /**
   * 上传文件
   *
   * @param bucketName  bucket名称
   * @param objectName  文件名称
   * @param stream      文件流
   * @param size        大小
   * @param contextType 类型
   * @see <a href= "http://docs.aws.amazon.com/goto/WebAPI/s3-2006-03-01/PutObject">AWS
   * API Documentation</a>
   */
  public PutObjectResult putObject(String bucketName, String objectName, InputStream stream, long size,
                                   String contextType) {
    AmazonS3 amazonS3 = getS3Client();
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(size);
    objectMetadata.setContentType(contextType);
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, stream, objectMetadata);
    // Setting the read limit value to one byte greater than the size of stream will
    // reliably avoid a ResetException
    putObjectRequest.getRequestClientOptions().setReadLimit(Long.valueOf(size).intValue() + 1);
    return amazonS3.putObject(putObjectRequest);

  }

  private AmazonS3 getS3Client() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
    AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
      config.getEndpoint(), null);
    return AmazonS3Client.builder()
      .withEndpointConfiguration(endpointConfiguration)
      .withClientConfiguration(clientConfiguration)
      .withCredentials(awsCredentialsProvider)
      .disableChunkedEncoding()
      .withPathStyleAccessEnabled(false)
      .build();
  }

  @Override
  public Resource loadAsResource(String filename) {
    String bucketName = config.getBucketName();
    AmazonS3 amazonS3 = getS3Client();
    S3Object s3Object = amazonS3.getObject(bucketName, filename);
    InputStream is = s3Object.getObjectContent();
    return new InputStreamResource(is);
  }

  @Override
  public void delete(String filename) {
    String bucketName = config.getBucketName();
    AmazonS3 amazonS3 = getS3Client();
    amazonS3.deleteObject(bucketName, filename);
  }

  @Override
  public String getUrl(String filename) {
    if (config.getAddress() != null) {
      return config.getAddress() + filename;
    }
    String bucketName = config.getBucketName();
    return getS3Client().getUrl(bucketName, filename).toString();
  }
}
