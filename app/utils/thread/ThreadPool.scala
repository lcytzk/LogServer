package utils.thread

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}
import java.util.logging.Logger

class ThreadPool {

  private val logger: Logger = Logger.getLogger(classOf[ThreadPool].getName)

  private val defaultCorePoolSize: Int = 10

  private val defaultMaximumPoolSize: Int = 20

  private val aliveTime: Long = 60L

  private val timeUnit: TimeUnit = TimeUnit.SECONDS

  private val defaultQueueSize: Int = 100

  private val queue: BlockingQueue[Runnable] = new LinkedBlockingQueue[Runnable](defaultQueueSize)

  private val threadPoolExecutor: ThreadPoolExecutor = new
      ThreadPoolExecutor(defaultCorePoolSize, defaultMaximumPoolSize, aliveTime, timeUnit, queue)

  def submit(task: Runnable) {
    threadPoolExecutor.execute(task)
  }

  def shutdown() = {
    logger.info("Start shut down thread pool.")
    threadPoolExecutor.shutdown()
    try {
      while (!awaitTermination(5)) {
        logger.info("Not finished yet")
      }
      logger.info("Finished.")
    }
    catch {
      case e: InterruptedException => {
        logger.warning("Something wrong happend when shutdown thread pool. " + e)
      }
    }
  }

  @throws(classOf[InterruptedException])
  def awaitTermination(seconds: Int): Boolean = {
    threadPoolExecutor.awaitTermination(seconds, timeUnit)
  }

}
