// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.headless

class TestModelsErrorExample extends AbstractTestModels {
  testModel("error", Model("to-report zero report 0 end")) {
    testError(observer>>"print 1 / zero", "Division by zero.")
  }
}
