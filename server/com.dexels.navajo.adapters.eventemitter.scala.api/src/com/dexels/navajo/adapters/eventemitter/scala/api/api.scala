package com.dexels.navajo.adapters.eventemitter.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait EventEmitterComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaCompiledScript => 
  def sendevent(message: NavajoMessage, f: SENDEVENT => Unit): Unit = {
    val instance = new SENDEVENT
    setupMap(message, instance, f)
  }
}

class SENDEVENT(instance: com.dexels.navajo.adapter.eventemitter.NavajoSendEventAdapter = new com.dexels.navajo.adapter.eventemitter.NavajoSendEventAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.eventemitter.NavajoSendEventAdapter => Unit) {
    f(instance)
  }
  def addProperty(name: java.lang.String, value: java.lang.Object): Unit = {
    instance.setPropertyName(name)
    instance.setValue(value)
  }
  def send(topic: java.lang.String): Unit = {
    instance.setSend(topic)
  }
}