// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim

import org.nlogo.agent.AgentSet
import org.nlogo.api.Syntax
import org.nlogo.nvm.{ Context, Reporter }

class _links extends Reporter {
  override def syntax =
    Syntax.reporterSyntax(Syntax.LinksetType)
  override def report(context: Context) =
    report_1(context)
  def report_1(context: Context): AgentSet =
    world.links()
}
