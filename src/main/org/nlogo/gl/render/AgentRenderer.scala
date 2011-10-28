// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.gl.render

import javax.media.opengl.GL
import com.sun.opengl.util.GLUT
import org.nlogo.api.World

abstract private class AgentRenderer(val world: World, val shapeRenderer: ShapeRenderer)

private object AgentRenderer {

  val glut = new com.sun.opengl.util.GLUT

  // This number is chosen to keep labels in 2D and 3D view at similar sizes.
  private val FontScale = 6500f

  def renderString(gl: GL, world: World, str: String, color: AnyRef, fontSize: Int, patchSize: Double) = {
    val rgb: Array[Float] =
      org.nlogo.api.Color.getColor(color).getRGBColorComponents(null)
    val scale: Float = (fontSize * 12 / patchSize.toFloat) / FontScale
    gl.glColor3fv(java.nio.FloatBuffer.wrap(rgb))          
    gl.glDisable(GL.GL_LIGHTING)
    gl.glScalef(scale, scale, 3.0f)
    val strwidth: Float = glut.glutStrokeLength(GLUT.STROKE_ROMAN, str)
    gl.glTranslatef(-strwidth / -2.0f, 0.0f, 0.0f)
    glut.glutStrokeString(GLUT.STROKE_ROMAN, str)
    gl.glEnable(GL.GL_LIGHTING)
  }

}
