package com.dexels.navajo.scala.document

import com.dexels.replication.api.ReplicationMessage
import com.dexels.replication.impl.ReplicationMessageImpl
import com.dexels.replication.factory.ReplicationFactory
import java.util.Collections


class ScalaMessage(val parent: ReplicationMessage) {

  def this() {
    this(ReplicationFactory.fromMap(null, Collections.emptyMap(), Collections.emptyMap()))
  }
 
  def put(key : String, value: Any, `type`:String = "") : ScalaMessage = {
    new ScalaMessage(parent.`with`(key, value, `type`))
  }
  
  def message(key: String, msg: ScalaMessage ) : ScalaMessage = {
    new ScalaMessage(parent.withSubMessage(key, msg.parent))
  }
}