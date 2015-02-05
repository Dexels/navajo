package com.dexels.navajo.core.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait CoreComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaCompiledScript => 
  def service(message: NavajoMessage, f: SERVICE => Unit): Unit = {
    val instance = new SERVICE
    setupMap(message, instance, f)
  }
  def testbean(message: NavajoMessage, f: TESTBEAN => Unit): Unit = {
    val instance = new TESTBEAN
    setupMap(message, instance, f)
  }
  def propertydescription(message: NavajoMessage, f: PROPERTYDESCRIPTION => Unit): Unit = {
    val instance = new PROPERTYDESCRIPTION
    setupMap(message, instance, f)
  }
  def persistencemanager(message: NavajoMessage, f: PERSISTENCEMANAGER => Unit): Unit = {
    val instance = new PERSISTENCEMANAGER
    setupMap(message, instance, f)
  }
  def navajostatus(message: NavajoMessage, f: NAVAJOSTATUS => Unit): Unit = {
    val instance = new NAVAJOSTATUS
    setupMap(message, instance, f)
  }
}

class SERVICE(instance: com.dexels.navajo.mapping.bean.ServiceMapper = new com.dexels.navajo.mapping.bean.ServiceMapper) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.mapping.bean.ServiceMapper => Unit) {
    f(instance)
  }
  def serviceClass: java.lang.String = {
    return instance.getServiceClass
  }
  def serviceClass(serviceClass: java.lang.String): SERVICE = {
    instance.setServiceClass(serviceClass)
    return this
  }
  def serviceMethod: java.lang.String = {
    return instance.getServiceMethod
  }
  def serviceMethod(serviceMethod: java.lang.String): SERVICE = {
    instance.setServiceMethod(serviceMethod)
    return this
  }
  def invoke: Unit = {
    instance.setInvoke(true)
  }
}

class TESTBEAN(instance: com.dexels.navajo.mapping.bean.TestBean = new com.dexels.navajo.mapping.bean.TestBean) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.mapping.bean.TestBean => Unit) {
    f(instance)
  }
}

class PROPERTYDESCRIPTION(instance: com.dexels.navajo.server.descriptionprovider.PropertyDescription
		 = new com.dexels.navajo.server.descriptionprovider.PropertyDescription
		) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.server.descriptionprovider.PropertyDescription
		 => Unit) {
    f(instance)
  }
}

class PERSISTENCEMANAGER(instance: com.dexels.navajo.persistence.impl.PersistenceManagerImpl
		 = new com.dexels.navajo.persistence.impl.PersistenceManagerImpl
		) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.persistence.impl.PersistenceManagerImpl
		 => Unit) {
    f(instance)
  }
}

class NAVAJOSTATUS(instance: com.dexels.navajo.server.enterprise.statistics.MetricsManager
		 = new com.dexels.navajo.server.enterprise.statistics.MetricsManager
		) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.server.enterprise.statistics.MetricsManager
		 => Unit) {
    f(instance)
  }
}