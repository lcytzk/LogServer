package business.upload

import java.io.ByteArrayInputStream

import business.dto.LogRecordProto.LogRecord
import business.upload.cache.LogCacheManager
import business.upload.config.{UploadTestConfig, UploadProdConfig, UploadConfig}
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.{Regions, Region}
import com.amazonaws.services.s3.model.{PutObjectResult, ObjectMetadata}
import com.amazonaws.services.s3.AmazonS3Client
import com.google.protobuf.GeneratedMessage
import org.slf4j.LoggerFactory
import play.api.Play
import utils.proto.ProtoHelper

// TODO Change aws config got upload @Liang Chenye
object UploadService {

  private val logger = LoggerFactory.getLogger(UploadService.getClass)

  private var uploadConfig: UploadConfig = UploadProdConfig

  private var credentials: BasicAWSCredentials = null

  private var s3Client: AmazonS3Client = null

  {
    if (Play.current.configuration.getString("env").get == "Test") {
      uploadConfig = UploadTestConfig
    }
    credentials = new BasicAWSCredentials(uploadConfig.AWS_ACCESS_ID, uploadConfig.AWS_SECRET_KEY)
    s3Client = new AmazonS3Client(credentials)
    // TODO Change region @Liang Chenye
    val usWest2: Region = Region.getRegion(Regions.US_WEST_2)
    s3Client.setRegion(usWest2)
  }

  def uploadLog(records: List[LogRecord]): PutObjectResult = {
    uploadToAWS(UploadHelper.generateLogS3Key(records.head.getLogType), records)
  }

  def uploadToAWS(key: String, records: List[GeneratedMessage]): PutObjectResult = {
    uploadToAWS(key, ProtoHelper.toByteArray(records))
  }

  def uploadToAWS(key: String, bytes: Array[Byte]): PutObjectResult = {
    val om = new ObjectMetadata
    om.setContentLength(bytes.length)
    s3Client.putObject(uploadConfig.AWS_BUCKET, key, new ByteArrayInputStream(bytes), om)
  }

  def close() = {
    s3Client.shutdown()
  }

}
