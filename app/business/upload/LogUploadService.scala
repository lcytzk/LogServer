package business.upload

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import business.aws.AWSClient
import business.dto.LogRecordProto.LogRecord
import com.amazonaws.services.s3.model.PutObjectResult
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

object LogUploadService {

  private val logger = LoggerFactory.getLogger(LogUploadService.getClass)

  val maps = new ConcurrentHashMap[Int, ConcurrentLinkedQueue[String]]()

  def uploadLog(records: List[LogRecord]) = {
    val logType = records.head.getLogType
    val queue = getCacheQueue(logType)
    UploadHelper.convertLogRecordToString(records).foreach(value => queue.add(value))
//    if (condition) {
//      uploadToAWS(UploadHelper.generateLogS3Key(logType))
//    }
  }

  private def getCacheQueue(logType: Int): ConcurrentLinkedQueue[String] = {
    if (maps.contains(logType)) {
      maps.get(logType)
    } else {
      maps.put(logType, new ConcurrentLinkedQueue[String]())
    }
  }

  private def uploadToAWS(key: String, records: List[String]): PutObjectResult = {
    AWSClient.uploadToAWS(key, StringUtils.join(records, "\n").getBytes())
  }

}
