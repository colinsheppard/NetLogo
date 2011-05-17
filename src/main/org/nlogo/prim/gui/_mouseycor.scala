package org.nlogo.prim.gui

import org.nlogo.api.LogoException
import org.nlogo.nvm.{ Context, Reporter, Syntax }
import org.nlogo.window.GUIWorkspace

class _mouseycor extends Reporter {
  override def syntax =
    Syntax.reporterSyntax(Syntax.TYPE_NUMBER)
  override def report(context: Context): java.lang.Double =
    workspace match {
      case gw: GUIWorkspace =>
        gw.mouseYCor()
      case _ =>
        0
    }
}