package business.monitor.impl

import business.dto.LogRecordProto.LogRecord
import business.monitor.Filter

import scala.collection.mutable.ListBuffer

object LogFilterManager {

  var filters = ListBuffer[Filter]()

  def filter(record: LogRecord): Boolean = {
    true
  }

  def getFilters: ListBuffer[Filter] = {
    filters
  }

  def addFilter(filter: Filter) = {
    filters.append(filter)
  }
}
