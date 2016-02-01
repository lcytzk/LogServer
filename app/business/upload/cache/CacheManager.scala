package business.upload.cache

import java.util.concurrent.{ConcurrentHashMap, ConcurrentLinkedQueue}

object CacheManager {

  val maps = new ConcurrentHashMap[String, ConcurrentLinkedQueue[String]]()

  def addARow(name: String, value: String) = {
    val queue = getCacheQueue(name)
    queue.add(value)
//    if (condition) {
//      uploadToAWS()
//    }
  }

  def getCacheQueue(name: String): ConcurrentLinkedQueue[String] = {
    if (maps.contains(name)) {
      maps.get(name)
    } else {
      maps.put(name, new ConcurrentLinkedQueue[String]())
    }
  }

}
