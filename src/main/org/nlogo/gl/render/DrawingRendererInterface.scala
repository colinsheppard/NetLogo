// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.gl.render

import javax.media.opengl.GL

private trait DrawingRendererInterface {
  def renderDrawing(gl: GL): Unit
  def clear(): Unit
  def init(gl: GL): Unit
}
