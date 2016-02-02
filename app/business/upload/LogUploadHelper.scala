package business.upload

import java.text.SimpleDateFormat
import java.util.Date

import business.dto.LogRecordProto.{LogRecord, LogType}

object LogUploadHelper {

  val dateFormat = new SimpleDateFormat("yyyyMMdd")
  val timeFormat = new SimpleDateFormat("HHmmss")

  private val BUCKET = Map[Int, String](
    LogType.EXCEPTION_LOG_VALUE -> "test-exception"
  )

  private val FILENAME_MAP = Map[Int, String](
    LogType.EXCEPTION_LOG_VALUE -> "test-exception"
  )

  def generateLogS3Key(logType: Int): String = {
    val date = new Date()
    s"${BUCKET(logType)}/${getDateStr(date)}/${getTime(date)}"
  }

  def getDateStr(date: Date): String = {
    dateFormat.format(date)
  }

  def getTime(date: Date): String = {
    timeFormat.format(date)
  }

  def convertLogRecordToString(logRecord: LogRecord): String = {
    logRecord.getSerial
  }

  def convertLogRecordToString(logRecords: List[LogRecord]): List[String] = {
    logRecords.map { record =>
      convertLogRecordToString(record)
    }
  }
}
