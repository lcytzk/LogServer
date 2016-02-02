package business.monitor.impl

import java.util.concurrent.atomic.AtomicInteger

import business.dto.LogRecordProto.LogRecord

object LogMonitor {

  var size = new AtomicInteger(0)

  def filter(record: Option[LogRecord]): Boolean = {
    if (record.nonEmpty) {
      val content = record.get
      monitor(content)
      LogFilterManager.filter(content)
    } else {
      false
    }
  }

  def monitor(logRecord: LogRecord) = {
//    how to evaluate size of a log
    size.addAndGet(logRecord.getSerializedSize)
  }
}
