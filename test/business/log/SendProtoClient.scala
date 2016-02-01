package business.log

import business.dto.LogRecordProto.{ExceptionLog, Log, LogRecord}
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.HttpClients
import utils.proto.ProtoHelper

import scala.collection.mutable.ListBuffer

object SendProtoClient {

  def main (args: Array[String]) {
    val httpclient = HttpClients.createDefault()
    val post = new HttpPost("http://localhost:9000/postUpload")
    val record = LogRecord.newBuilder().setDevice("testDevice")
      .setLog(Log.newBuilder.setExceptionLog(
        ExceptionLog.newBuilder.setException("test exception").setDetail("test detail").build
      )).build
    val l = ListBuffer[LogRecord]()
    l.append(record)
    l.append(record)
    post.setEntity(new ByteArrayEntity(ProtoHelper.toByteArray(l.toList)))
    val response = httpclient.execute(post)
    val entity = response.getEntity
    println(entity.toString)
  }

}
