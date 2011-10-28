// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.threed

import org.nlogo.agent.{ Agent3D, ArrayAgentSet, Turtle }
import org.nlogo.api.{ AgentException, Syntax }
import org.nlogo.nvm.{ Context, Reporter }

class _breedat(breedName: String) extends Reporter {
  def this() = this(null)
  override def syntax =
    Syntax.reporterSyntax(Array(Syntax.NumberType,
                                Syntax.NumberType,
                                Syntax.NumberType),
                          Syntax.TurtlesetType, "-TP-")
  override def toString =
    super.toString + ":" + breedName
  override def report(context: Context): ArrayAgentSet = {
    val dx = argEvalDoubleValue(context, 0)
    val dy = argEvalDoubleValue(context, 1)
    val dz = argEvalDoubleValue(context, 2)
    val patch =
      try context.agent.asInstanceOf[Agent3D].getPatchAtOffsets(dx, dy, dz)
      catch {
        case e: AgentException =>
          return new ArrayAgentSet(classOf[Turtle], 0, false, world)
      }
    if (patch == null)
      new ArrayAgentSet(classOf[Turtle], 0, false, world)
    else {
      val agentset = new ArrayAgentSet(
        classOf[Turtle], patch.turtleCount, false, world)
      val breed = world.getBreed(breedName)
      val it = patch.turtlesHere.iterator
      while(it.hasNext) {
        val turtle = it.next()
        if (turtle != null && (turtle.getBreed eq breed))
          agentset.add(turtle)
      }
      agentset
    }
  }
}
