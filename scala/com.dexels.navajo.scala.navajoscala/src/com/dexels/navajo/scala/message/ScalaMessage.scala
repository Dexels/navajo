package com.dexels.navajo.scala.document

import com.dexels.replication.api.ReplicationMessage
import com.dexels.replication.factory.ReplicationFactory
import java.util.Collections
import com.dexels.immutable.api.ImmutableMessage
import com.dexels.immutable.factory.ImmutableFactory


class ScalaMessage(val parent: ImmutableMessage) {

  def this() {
    this(ImmutableFactory.empty());
  }
  
  /* Getters */
  def get(key : String) : ScalaMessage = {
    val optionalMsg = parent.subMessage(key)
    optionalMsg.get match {
      case msg : ImmutableMessage => new ScalaMessage(optionalMsg.get)
      case _ => null
    }
  }
  def getString(key : String) : String = {
    parent.columnValue(key).asInstanceOf[String]
  }
  def getInt(key : String) : Int = {
    parent.columnValue(key).asInstanceOf[Int]
  }
  def getBoolean(key : String) : Boolean = {
    parent.columnValue(key).asInstanceOf[Boolean]
  }
  
 /* Putters */
  def put(key : String, value: Any, `type`:String = "") : ScalaMessage = {
    new ScalaMessage(parent.`with`(key, value, `type`))
  }
  
  def message(key: String, msg: ScalaMessage ) : ScalaMessage = {
    new ScalaMessage(parent.withSubMessage(key, msg.parent))
  }
}