package business.monitor.impl

import business.dto.LogRecordProto.LogRecord

class LogRecordFilter {

  def filter(message: LogRecord): Boolean = {
    true
  }

}
