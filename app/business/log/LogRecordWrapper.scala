package business.log

import java.io.{IOException, EOFException, ByteArrayInputStream, DataInputStream}

import business.dto.LogRecordProto.LogRecord
import org.slf4j.LoggerFactory

class LogRecordWrapper(bytes: Array[Byte]){

  private val logger = LoggerFactory.getLogger(LogRecordWrapper.getClass)

  private val data: Array[Byte] = bytes

  private val is: DataInputStream = wrap(data)

  @throws(classOf[IOException])
  def readALogRecord: Option[LogRecord] = {
    try {
      val length = is.readInt()
      val bs = new Array[Byte](length)
      is.read(bs)
      Some(LogRecord.parseFrom(bs))
    } catch {
      case e: EOFException => None
      case e: IOException =>
        logger.error("Error when read a logrecord", e)
        throw e
    }
  }

  def wrap(data: Array[Byte]): DataInputStream = {
    new DataInputStream(new ByteArrayInputStream(data))
  }
}

object LogRecordWrapper {
  def apply(bytes: Array[Byte]): LogRecordWrapper = {
    new LogRecordWrapper(bytes)
  }
}
