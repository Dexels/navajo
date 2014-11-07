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
  def query: java.lang.String = {
    return instance.getQuery
  }
  def query(query: java.lang.String): JDBCQUERY = {
    instance.setQuery(query)
    return this
  }
  def update: java.lang.String = {
    return instance.getUpdate
  }
  def update(update: java.lang.String): JDBCQUERY = {
    instance.setUpdate(update)
    return this
  }
  def binaryQuery: com.dexels.navajo.document.types.Binary = {
    return instance.getBinaryQuery
  }
  def binaryQuery(binaryQuery: com.dexels.navajo.document.types.Binary): JDBCQUERY = {
    instance.setBinaryQuery(binaryQuery)
    return this
  }
  def binaryUpdate: com.dexels.navajo.document.types.Binary = {
    return instance.getBinaryUpdate
  }
  def binaryUpdate(binaryUpdate: com.dexels.navajo.document.types.Binary): JDBCQUERY = {
    instance.setBinaryUpdate(binaryUpdate)
    return this
  }
  def debug: java.lang.Boolean = {
    return instance.getDebug
  }
  def debug(debug: java.lang.Boolean): JDBCQUERY = {
    instance.setDebug(debug)
    return this
  }
  def transactionContext: java.lang.Integer = {
    return instance.getTransactionContext
  }
  def transactionContext(transactionContext: java.lang.Integer): JDBCQUERY = {
    instance.setTransactionContext(transactionContext)
    return this
  }
  def datasource: java.lang.String = {
    return instance.getDatasource
  }
  def datasource(datasource: java.lang.String): JDBCQUERY = {
    instance.setDatasource(datasource)
    return this
  }
  def username: java.lang.String = {
    return instance.getUsername
  }
  def username(username: java.lang.String): JDBCQUERY = {
    instance.setUsername(username)
    return this
  }
  def parameter: java.lang.Object = {
    return instance.getParameter
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

class HTTPURL(instance: com.dexels.navajo.adapter.resource.URLMap = new com.dexels.navajo.adapter.resource.URLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.adapter.resource.URLMap => Unit) {
    f(instance)
  }
  def url: java.lang.String = {
    return instance.getUrl
  }
  def url(url: java.lang.String): HTTPURL = {
    instance.setUrl(url)
    return this
  }
  def method: java.lang.String = {
    return instance.getMethod
  }
  def method(method: java.lang.String): HTTPURL = {
    instance.setMethod(method)
    return this
  }
  def textContent: java.lang.String = {
    return instance.getTextContent
  }
  def textContent(textContent: java.lang.String): HTTPURL = {
    instance.setTextContent(textContent)
    return this
  }
  def contentType: java.lang.String = {
    return instance.getContentType
  }
  def contentType(contentType: java.lang.String): HTTPURL = {
    instance.setContentType(contentType)
    return this
  }
  def content: com.dexels.navajo.document.types.Binary = {
    return instance.getContent
  }
  def content(content: com.dexels.navajo.document.types.Binary): HTTPURL = {
    instance.setContent(content)
    return this
  }
  def queuedsend: java.lang.Boolean = {
    return instance.getQueuedsend
  }
  def queuedsend(queuedsend: java.lang.Boolean): HTTPURL = {
    instance.setQueuedsend(queuedsend)
    return this
  }
  def doSend: Unit = {
    instance.setDoSend(true)
  }
}