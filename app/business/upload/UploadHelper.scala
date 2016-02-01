package business.upload

import business.dto.LogRecordProto.LogType

object UploadHelper {

  private val KEY_PREFIX = Map[Int, String](
    LogType.EXCEPTION_LOG_VALUE -> "test-exception/"
  )

  private val FILENAME_MAP = Map[Int, String](
    LogType.EXCEPTION_LOG_VALUE -> "test-exception/"
  )

  def generateLogS3Key(logType: Int): String = {
    KEY_PREFIX(logType) + "date" + "filename"
  }

}
