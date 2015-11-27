package com.dexels.navajo.entity.scala.api

import com.dexels.navajo.adapters.scala.api._

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait EntityComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def exampletransactionaladapter(message: NavajoMessage, f: EXAMPLETRANSACTIONALADAPTER => Unit): Unit = {
    val instance = new EXAMPLETRANSACTIONALADAPTER
    setupMap(message, instance, f)
  }
  def transaction(message: NavajoMessage, f: TRANSACTION => Unit): Unit = {
    val instance = new TRANSACTION
    setupMap(message, instance, f)
  }
  def entity(message: NavajoMessage, f: ENTITY => Unit): Unit = {
    val instance = new ENTITY
    setupMap(message, instance, f)
  }
}

class EXAMPLETRANSACTIONALADAPTER(instance: com.dexels.navajo.entity.adapters.ExampleTransactionalAdapter = new com.dexels.navajo.entity.adapters.ExampleTransactionalAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.entity.adapters.ExampleTransactionalAdapter => Unit): Unit = {
    f(instance)
  }
}

class TRANSACTION(instance: com.dexels.navajo.entity.adapters.TransactionMap = new com.dexels.navajo.entity.adapters.TransactionMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.entity.adapters.TransactionMap => Unit): Unit = {
    f(instance)
  }
}

class ENTITY(instance: com.dexels.navajo.entity.adapters.EntityMap = new com.dexels.navajo.entity.adapters.EntityMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.entity.adapters.EntityMap => Unit): Unit = {
    f(instance)
  }
  def entity(entity: java.lang.String): ENTITY = {
    instance.setEntity(entity)
    return this
  }
  def sendThrough(sendThrough: java.lang.Boolean): ENTITY = {
    instance.setSendThrough(sendThrough)
    return this
  }
  def server(server: java.lang.String): ENTITY = {
    instance.setServer(server)
    return this
  }
  def id(id: java.lang.String): ENTITY = {
    instance.setId(id)
    return this
  }
  def block(block: java.lang.Boolean): ENTITY = {
    instance.setBlock(block)
    return this
  }
  def username(username: java.lang.String): ENTITY = {
    instance.setUsername(username)
    return this
  }
  def password(password: java.lang.String): ENTITY = {
    instance.setPassword(password)
    return this
  }
  def resource(resource: java.lang.String): ENTITY = {
    instance.setResource(resource)
    return this
  }
  def useCurrentOutDoc(useCurrentOutDoc: java.lang.Boolean): ENTITY = {
    instance.setUseCurrentOutDoc(useCurrentOutDoc)
    return this
  }
  def messagePointer(messagePointer: java.lang.String): ENTITY = {
    instance.setMessagePointer(messagePointer)
    return this
  }
  def selectionPointer(selectionPointer: java.lang.String): ENTITY = {
    instance.setSelectionPointer(selectionPointer)
    return this
  }
  def withMessage(f: MESSAGEMAP => Unit): ENTITY = {
    f(new MESSAGEMAP(instance.getMessage))
    return this
  }
  def withEachMessages(f: MESSAGEMAP => Unit): ENTITY = {
    for (i <- instance.getMessages)
      f(new MESSAGEMAP(i))
    return this
  }
  def createproperty(name: java.lang.String, `type`: java.lang.String, value: java.lang.Object): Unit = {
    instance.setPropertyName(name)
    instance.setPropertyType(`type`)
    instance.setProperty(value)
  }
  def deleteproperty(name: java.lang.String): Unit = {
    instance.setDeleteProperty(name)
  }
  def deletemessage(name: java.lang.String): Unit = {
    instance.setDeleteMessage(name)
  }
  def suppressproperty(name: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective("monkey")
  }
  def showproperty(name: java.lang.String, direction: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective(direction)
    instance.setPropertyDirective("monkey")
  }
  def setdirection(name: java.lang.String, direction: java.lang.String): Unit = {
    instance.setPropertyId(name)
    instance.setPropertyDirective(direction)
  }
  def head(breakOnException: java.lang.Boolean, server: java.lang.String, username: java.lang.String, password: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setMethod("monkey")
    instance.setCall(true)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
  def get(breakOnException: java.lang.Boolean, server: java.lang.String, username: java.lang.String, password: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setMethod("monkey")
    instance.setCall(true)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
  def put(breakOnException: java.lang.Boolean, server: java.lang.String, username: java.lang.String, password: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setMethod("monkey")
    instance.setCall(true)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
  def post(breakOnException: java.lang.Boolean, server: java.lang.String, username: java.lang.String, password: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setMethod("monkey")
    instance.setCall(true)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
  def delete(breakOnException: java.lang.Boolean, server: java.lang.String, username: java.lang.String, password: java.lang.String, showProperties: java.lang.String, suppressProperties: java.lang.String, inputProperties: java.lang.String, outputProperties: java.lang.String, block: java.lang.Boolean, appendTo: java.lang.String, append: java.lang.String): Unit = {
    instance.setMethod("monkey")
    instance.setCall(true)
    instance.setBreakOnException(breakOnException)
    instance.setServer(server)
    instance.setUsername(username)
    instance.setPassword(password)
    instance.setSuppressProperties(showProperties)
    instance.setSuppressProperties(suppressProperties)
    instance.setInputProperties(inputProperties)
    instance.setOutputProperties(outputProperties)
    instance.setBlock(block)
    instance.setAppendTo(appendTo)
    instance.setAppend(append)
  }
}