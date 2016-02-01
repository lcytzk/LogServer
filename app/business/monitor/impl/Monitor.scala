package business.monitor.impl

object Monitor {

  def filter(): Boolean = {
    monitor()
    process()
    FilterManager.filter(null)
  }

  def monitor() = {}

  def process() = {}
}
