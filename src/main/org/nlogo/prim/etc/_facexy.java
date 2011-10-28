// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim.etc;

import org.nlogo.agent.Turtle;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Command;
import org.nlogo.nvm.Context;
import org.nlogo.api.Syntax;

public final strictfp class _facexy
    extends Command {
  @Override
  public Syntax syntax() {
    return Syntax.commandSyntax
        (new int[]{Syntax.NumberType(), Syntax.NumberType()},
            "OT--", true);
  }

  @Override
  public void perform(final Context context)
      throws LogoException {
    if (context.agent instanceof org.nlogo.agent.Turtle) {
      Turtle turtle = (Turtle) context.agent;
      turtle.face(argEvalDoubleValue(context, 0),
          argEvalDoubleValue(context, 1),
          true);
    } else {
      world.observer().
          face(argEvalDoubleValue(context, 0),
              argEvalDoubleValue(context, 1));
    }
    context.ip = next;
  }
}
