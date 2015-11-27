package com.dexels.navajo.function.pdf.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait PdfFunctionsComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def MergePDF(arg0: com.dexels.navajo.document.types.Binary, arg1: com.dexels.navajo.document.types.Binary): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.pdf.functions.MergePDF)
    function.insertOperand(arg0)
    function.insertOperand(arg1)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
  def SecurePDF(arg0: com.dexels.navajo.document.types.Binary): com.dexels.navajo.document.types.Binary = {
    val function = this.setupFunction(new com.dexels.navajo.pdf.functions.SecurePDF)
    function.insertOperand(arg0)
    function.evaluate().asInstanceOf[com.dexels.navajo.document.types.Binary]
  }
}