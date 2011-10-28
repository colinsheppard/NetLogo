// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.agent

import java.io.{ IOException, PrintWriter }
import org.nlogo.util.Exceptions.ignoring
import org.nlogo.api.{ FileMode, Version }
import org.nlogo.api.Dump.csv

object AbstractExporter {

  def exportHeader(writer: PrintWriter, tyype: String, modelFileName: String, extraHeader: String) {
    import writer.println
    println(csv.header("export-" + tyype + " data (" + Version.version + ")"))
    println(csv.header(modelFileName))
    if(extraHeader.nonEmpty)
      println(csv.header(extraHeader))
    // date & time
    val currentDate = new java.util.Date // Use Date to avoid month bug in GregorianCalendar
    val dateFormatter = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS Z")
    println(csv.header(dateFormatter.format(currentDate)))
    println()
  }

}

abstract class AbstractExporter(filename: String) {

  @throws(classOf[IOException])
  def export(writer: java.io.PrintWriter) // abstract

  @throws(classOf[IOException])
  def export(tyype: String, modelFileName: String, extraHeader: String) {
    val file = new org.nlogo.api.LocalFile(filename)
    try {
      file.open(FileMode.Write)
      val writer = file.getPrintWriter
      AbstractExporter.exportHeader(writer, tyype, modelFileName, extraHeader)
      export(writer)
    }
    finally ignoring(classOf[IOException]) {
      file.close(false)
    }
  }

}
