package controllers.log

import java.io.FileInputStream

import business.dto.LogRecordProto
import business.log.{LogRecordWrapper, LogService}
import play.api.mvc._

class Log extends Controller {

  def upload: Action[AnyContent] = Action { request =>
    val bs = new Array[Byte](100)
    new FileInputStream("/home/liangchenye/tmp/hive/u.t").read(bs)
    val wrapper = LogRecordWrapper(bs)
    Ok(wrapper.readALogRecord.get.toString)
  }

  def postUpload: Action[RawBuffer] = Action(BodyParsers.parse.raw) { request =>
    LogService.uploadLog(request.body.asBytes().get)
    Ok("postupload")
  }

  def postUpload2: Action[RawBuffer] = Action(BodyParsers.parse.raw) { request =>
    println(request)
//    println(request.body)
    println(request.headers)
//    println(request.body.asBytes().get.length)
    val k = request.body.asBytes().get
//    println(k.length)
    println(LogRecordProto.LogRecord.parseFrom(k))
//    request.body.asBytes().get.foreach { b =>
//      println(b)
//    }
//    println(new String(
//        request.body.asBytes().get.drop(7)))

    Ok("postupload")
  }


}
