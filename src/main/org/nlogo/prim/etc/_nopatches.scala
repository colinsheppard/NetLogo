// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.etc

import org.nlogo.api.Syntax
import org.nlogo.nvm.{ Context, Reporter }

class _nopatches extends Reporter {
  override def syntax =
    Syntax.reporterSyntax(Syntax.PatchsetType)
  override def report(context: Context) =
    world.noPatches
}
