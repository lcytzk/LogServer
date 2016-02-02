package business.aws

import java.io.ByteArrayInputStream

import business.aws.config.AWSConfig
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectResult}

object AWSClient {

  private val awsConfig = AWSConfig.getProperConfig

  private var credentials: BasicAWSCredentials = null

  private var s3Client: AmazonS3Client = null

  {
    credentials = new BasicAWSCredentials(awsConfig.AWS_ACCESS_ID, awsConfig.AWS_SECRET_KEY)
    s3Client = new AmazonS3Client(credentials)
    s3Client.setRegion(awsConfig.REGION)
  }
  
  def uploadToAWS(key: String, bytes: Array[Byte]): PutObjectResult = {
    val om = new ObjectMetadata
    om.setContentLength(bytes.length)
    s3Client.putObject(awsConfig.AWS_BUCKET, key, new ByteArrayInputStream(bytes), om)
  }

  override def finalize() = {
    s3Client.shutdown()
    super.finalize()
  }
}
