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

  def addRows(logType: Int, values: List[String]) = {
    val queue = getCacheQueue(logType)
    values.foreach( value => queue.add(value))
    //    if (condition) {
    //      uploadToAWS()
    //    }
  }



  def uploadToAWS(logType: Int) = {
    UploadHelper.generateLogS3Key(logType)
  }

}
