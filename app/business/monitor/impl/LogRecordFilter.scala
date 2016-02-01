package business.monitor.impl

import business.monitor.Filter
import com.google.protobuf.GeneratedMessage

class LogRecordFilter extends Filter {

  override def needFilter(message: GeneratedMessage): Boolean = {
    true
  }

}
