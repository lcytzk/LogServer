package business.monitor

import com.google.protobuf.GeneratedMessage

trait Filter {
  def needFilter(message: GeneratedMessage): Boolean
}
