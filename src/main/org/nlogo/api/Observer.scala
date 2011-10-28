// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.api

/**
 * Provides access to NetLogo observer
 */

trait Observer extends Agent {

  /** Returns the currently watched or followed agent (or nobody) */
  def targetAgent: Agent

  /** Returns the current perspective */
  def perspective: Perspective

  /** Returns the current distance behind the followed turtle the 3D view is displaying */
  def followDistance: Int

  def dist: Double
  def heading: Double
  def pitch: Double
  def roll: Double
  def oxcor: Double
  def oycor: Double
  def ozcor: Double
  def dx: Double
  def dy: Double
  def dz: Double
  def setPerspective(p: Perspective, a: Agent)

}
