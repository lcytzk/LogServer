package utils.proto

import java.io.{ByteArrayOutputStream, DataOutputStream}

import com.google.protobuf.GeneratedMessage

object ProtoHelper {

  def toByteArray(messages: List[GeneratedMessage]): Array[Byte] = {
    val bos = new ByteArrayOutputStream()
    val dos = new DataOutputStream(bos)
    messages.foreach { message =>
      dos.writeInt(message.getSerializedSize)
      dos.write(message.toByteArray)
    }
    dos.flush()
    val bs = bos.toByteArray
    bos.close()
    dos.close()
    bs
  }

}
