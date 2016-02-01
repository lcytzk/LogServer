package business.log

import business.dto.LogRecordProto.LogRecord
import business.monitor.impl.FilterManager
import business.upload.UploadService

import scala.collection.mutable.ListBuffer

object LogService {

  def uploadLog(bytes: Array[Byte]) = {
    val wrapper = LogRecordWrapper(bytes)
    val records = ListBuffer[LogRecord]()
    var record = wrapper.readALogRecord
    while (record.nonEmpty) {
      if (FilterManager.filter(record.get)) {
        records.append(record.get)
      }
      record = wrapper.readALogRecord
    }
    UploadService.uploadLog(records.toList)
  }

}
