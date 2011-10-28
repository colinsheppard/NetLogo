// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.prim;

import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.api.Syntax;

public final strictfp class _randomconst extends Reporter {
  public long n;

  @Override
  public Syntax syntax() {
    return Syntax.reporterSyntax(Syntax.NumberType());
  }

  @Override
  public String toString() {
    return super.toString() + ":" + n;
  }

  @Override
  public Object report(Context context) {
    return report_1(context);
  }

  public double report_1(Context context) {
    return context.job.random.nextLong(n);
  }
}
