package org.nlogo.webstart.logging

import java.io.Writer
import java.net.URL
import message.DirectorMessage.{ToDirectorWrite, ToDirectorAbandon, ToDirectorFinalize}

class LogProxyWriter(mode: LogSendingMode, destinations: URL*) extends Writer {
  private val director = new LogDirector(mode, destinations: _*).start()
  override def write(cbuf: Array[Char], off: Int, len: Int) { director ! ToDirectorWrite(cbuf.subSequence(off, off + len).toString) }
  override def flush()  { /* While the Director manages its own flushing, making this throw an exception breaks everything; ignore operation */ }
  /*none*/ def delete() { director ! ToDirectorAbandon }
  override def close()  { director ! ToDirectorFinalize }
}