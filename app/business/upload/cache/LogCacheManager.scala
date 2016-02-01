package business.upload.cache

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

import business.upload.UploadHelper

object LogCacheManager {

  val maps = new ConcurrentHashMap[Int, ConcurrentLinkedQueue[String]]()

  def addARow(logType: Int, value: String) = {
    val queue = getCacheQueue(logType)
    queue.add(value)
//    if (condition) {
//      uploadToAWS()
//    }
  }

  def getCacheQueue(logType: Int): ConcurrentLinkedQueue[String] = {
    if (maps.contains(logType)) {
      maps.get(logType)
    } else {
      maps.put(logType, new ConcurrentLinkedQueue[String]())
    }
  }

  def uploadToAWS(logType: Int) = {
    UploadHelper.generateLogS3Key(logType)
  }

}
