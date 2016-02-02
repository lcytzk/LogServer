package business.log

import business.dto.LogRecordProto.LogRecord
import business.monitor.impl.LogMonitor
import business.upload.LogUploadService
import utils.thread.UploadThreadPool

import scala.collection.mutable.ListBuffer

object LogService {

  def uploadLog(bytes: Array[Byte]) = {
    // Is here need thread pool? Need return fail if any error occurs?
    UploadThreadPool.submit(new Runnable {
      override def run() = {
        val wrapper = LogRecordWrapper(bytes)
        val records = ListBuffer[LogRecord]()
        var record = wrapper.readALogRecord
        while (record.nonEmpty) {
          if (!LogMonitor.filter(record)) {
            records.append(record.get)
          }
          record = wrapper.readALogRecord
        }
        LogUploadService.uploadLog(records.toList)
      }
    })
  }

}
