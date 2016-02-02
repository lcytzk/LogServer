package business.upload

import java.util
import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import business.aws.AWSClient
import business.dto.LogRecordProto.{LogType, LogRecord}
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory

object LogUploadService {

  private val logger = LoggerFactory.getLogger(LogUploadService.getClass)

  private val maps = new ConcurrentHashMap[Int, ConcurrentLinkedQueue[String]]()

  private val CACHE_SIZE = 10

  {
    init()
  }

  private def init() = {
    maps.put(LogType.EXCEPTION_LOG_VALUE, new ConcurrentLinkedQueue[String]())
//    add different log type queue into maps
  }

  def uploadLog(records: List[LogRecord]) = {
    val logType = records.head.getLogType
    val queue = maps.get(logType)
    queue.synchronized {
      records.foreach { record =>
        queue.add(LogUploadHelper.convertLogRecordToString(record))
      }
      if (queue.size() > CACHE_SIZE) {
        uploadToAWS(LogUploadHelper.generateLogS3Key(logType), queue)
        queue.clear()
      }
    }
  }

  private def uploadToAWS(key: String, records: util.Queue[String]) = {
    AWSClient.uploadToAWS(key, StringUtils.join(records, "\n").getBytes())
  }

}
