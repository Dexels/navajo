package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Navajo
import com.dexels.navajo.document.Message

object NavajoFactory {
  def create(): NavajoDocument = {
    new NavajoDocument(com.dexels.navajo.document.NavajoFactory.getInstance().createNavajo())
  }

  def create(parent: Navajo): NavajoDocument = {
    new NavajoDocument(parent)
  }

  def createMessage(doc: NavajoDocument, name: String): NavajoMessage = {
    new NavajoMessage(com.dexels.navajo.document.NavajoFactory.getInstance().createMessage(doc.wrapped, name))
  }

  def createArrayMessage(doc: NavajoDocument, name: String): NavajoMessage = {
    new NavajoMessage(com.dexels.navajo.document.NavajoFactory.getInstance().createMessage(doc.wrapped, name, Message.MSG_TYPE_ARRAY))
  }

}