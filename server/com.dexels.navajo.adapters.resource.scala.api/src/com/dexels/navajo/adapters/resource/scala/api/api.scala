package com.dexels.navajo.adapters.resource.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait ResourceAdaptersComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaCompiledScript => 
  def jdbcquery(message: NavajoMessage, f: JDBCQUERY => Unit): Unit = {
    val instance = new JDBCQUERY
    setupMap(message, instance, f)
  }
  def httpurl(message: NavajoMessage, f: HTTPURL => Unit): Unit = {
    val instance = new HTTPURL
    setupMap(message, instance, f)
  }
}

class JDBCQUERY(instance: com.dexels.navajo.adapter.resource.JDBCMap = new com.dexels.navajo.adapter.resource.JDBCMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.resource.JDBCMap => Unit) {
    f(instance)
  }
  def query(query: java.lang.String): JDBCQUERY = {
    instance.setQuery(query)
    return this
  }
  def update(update: java.lang.String): JDBCQUERY = {
    instance.setUpdate(update)
    return this
  }
  def binaryQuery(binaryQuery: com.dexels.navajo.document.types.Binary): JDBCQUERY = {
    instance.setBinaryQuery(binaryQuery)
    return this
  }
  def debug(debug: java.lang.Boolean): JDBCQUERY = {
    instance.setDebug(debug)
    return this
  }
  def transactionContext(transactionContext: java.lang.Integer): JDBCQUERY = {
    instance.setTransactionContext(transactionContext)
    return this
  }
  def datasource(datasource: java.lang.String): JDBCQUERY = {
    instance.setDatasource(datasource)
    return this
  }
  def username(username: java.lang.String): JDBCQUERY = {
    instance.setUsername(username)
    return this
  }
  def parameter(parameter: java.lang.Object): JDBCQUERY = {
    instance.setParameter(parameter)
    return this
  }
  def withEachResultSet(f: RESULTROW => Unit): JDBCQUERY = {
    for (i <- instance.getResultSet)
      f(new RESULTROW(i))
    return this
  }
  def addParameter(value: java.lang.Object): Unit = {
    instance.setParameter(value)
  }
  def doUpdate: Unit = {
    instance.setDoUpdate(true)
  }
  def rollback: Unit = {
    instance.setKill(true)
  }
}

class RESULTROW(instance: com.dexels.navajo.adapter.sqlmap.ResultSetMap) extends Adapter(instance) {
  def columnName(columnName: java.lang.String): RESULTROW = {
    instance.setColumnName(columnName)
    return this
  }
  def columnValue: java.lang.Object = {
    return instance.getColumnValue
  }
  def value(name: java.lang.String): java.lang.Object = {
    instance.setColumnName(name)
    return instance.columnValue
  }
}

class HTTPURL(instance: com.dexels.navajo.adapter.resource.URLMap = new com.dexels.navajo.adapter.resource.URLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.resource.URLMap => Unit) {
    f(instance)
  }
  def url(url: java.lang.String): HTTPURL = {
    instance.setUrl(url)
    return this
  }
  def method(method: java.lang.String): HTTPURL = {
    instance.setMethod(method)
    return this
  }
  def textContent(textContent: java.lang.String): HTTPURL = {
    instance.setTextContent(textContent)
    return this
  }
  def contentType(contentType: java.lang.String): HTTPURL = {
    instance.setContentType(contentType)
    return this
  }
  def content(content: com.dexels.navajo.document.types.Binary): HTTPURL = {
    instance.setContent(content)
    return this
  }
  def queuedSend(queuedSend: java.lang.Boolean): HTTPURL = {
    instance.setQueuedSend(queuedSend)
    return this
  }
  def doSend: Unit = {
    instance.setDoSend(true)
  }
}