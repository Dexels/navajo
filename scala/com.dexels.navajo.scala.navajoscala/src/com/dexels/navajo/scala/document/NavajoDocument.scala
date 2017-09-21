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

  def property(name: String): NavajoProperty = {
    val prop = wrapped.getProperty(name)
    if (prop == null) {
      //wrapped.write(System.err)
      //throw new NullPointerException("No property found: " + name);
      return null
    }
    return new NavajoProperty(prop)
  }

  def propertyValue(name: String): Any = {
    val p = property(name)
    if (p == null) {
      return null
    }
    return p.value
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
  

  def withMessage(m1: NavajoMessage)(f: (NavajoMessage) => Unit) = {

  }

  def withMessage(m1: NavajoMessage, m2: NavajoMessage)(f: (NavajoMessage, NavajoMessage) => Unit) = {

  }
  def withMessage(m1: NavajoMessage, m2: NavajoMessage, m3: NavajoMessage)(f: (NavajoMessage, NavajoMessage, NavajoMessage) => Unit) = {

  }

}