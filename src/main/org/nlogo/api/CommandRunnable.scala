// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.api

trait CommandRunnable {
  @throws(classOf[LogoException])
  def run()
}
