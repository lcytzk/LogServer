package business.monitor

import com.google.protobuf.GeneratedMessage

trait Filter {
  def filter(message: GeneratedMessage): Boolean
}
