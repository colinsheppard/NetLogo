// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.gui

import org.nlogo.api.Syntax
import org.nlogo.nvm.{ Command, Context }

class _deletelogfiles extends Command {
  override def syntax =
    Syntax.commandSyntax("O---", true)
  override def perform(context: Context) {
    workspace.deleteLogFiles()
    context.ip = next
  }
}
