package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Navajo
import com.dexels.navajo.document.Message

class NavajoDocument(val wrapped: Navajo = null) {

  def message(name: String): NavajoMessage = {
    if (wrapped.getMessage(name) != null) {
       return new NavajoMessage(wrapped.getMessage(name));
    }
    null
  }

  
  def rpcUser: String = {
    wrapped.getHeader().getRPCUser()
  }

  def rpcPassword: String = {
    wrapped.getHeader().getRPCPassword()
  }

  def addMessage(name: String): NavajoMessage = {
    val message = NavajoFactory.createMessage(this, name)
    new NavajoMessage(wrapped.addMessage(NavajoFactory.createMessage(this, name).parent))
  }
  

  def addMessage(message: NavajoMessage): NavajoMessage = {
    new NavajoMessage(wrapped.addMessage(message.parent))
  }

  def addArrayMessage(name: String): NavajoMessage = {
    new NavajoMessage(wrapped.addMessage(NavajoFactory.createArrayMessage(this, name).parent))
  }
  
  def forEachMessage(name: String, f: (NavajoMessage) => Unit) {
    if (wrapped.getMessage(name) == null) return
    wrapped.getMessage(name).getElements.forEach(element => f(new NavajoMessage(element)))
  }
  

  def withMessage(messageName: String, f: (NavajoMessage) => Unit) = {
    val msg = message(messageName)
    if (msg != null) {
      f(msg)
    }
  }

 

}