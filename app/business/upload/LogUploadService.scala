package business.upload

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import business.aws.AWSClient
import business.dto.LogRecordProto.LogRecord
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

object LogUploadService {

  private val logger = LoggerFactory.getLogger(LogUploadService.getClass)

  private val maps = new ConcurrentHashMap[Int, ConcurrentLinkedQueue[String]]()

  private val CACHE_SIZE = 10

  def uploadLog(records: List[LogRecord]) = {
    val logType = records.head.getLogType
    val queue = getCacheQueue(logType)
    queue.synchronized {
      UploadHelper.convertLogRecordToString(records).foreach(value => queue.add(value))
      if (queue.size() > CACHE_SIZE) {
        uploadToAWS(UploadHelper.generateLogS3Key(logType), queue)
        queue.clear()
      }
    }
  }

  private def getCacheQueue(logType: Int): ConcurrentLinkedQueue[String] = {
    if (maps.contains(logType)) {
      maps.get(logType)
    } else {
      maps.put(logType, new ConcurrentLinkedQueue[String]())
    }
  }

  private def uploadToAWS(key: String, records: ConcurrentLinkedQueue[String]) = {
    AWSClient.uploadToAWS(key, StringUtils.join(records, "\n").getBytes())
  }

}
