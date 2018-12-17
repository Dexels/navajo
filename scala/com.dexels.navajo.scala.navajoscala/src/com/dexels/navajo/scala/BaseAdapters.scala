package com.dexels.navajo.scala

import com.dexels.navajo.scala.document.NavajoMessage
import com.dexels.navajo.script.api.Mappable
import com.dexels.navajo.expression.api.FunctionInterface

trait BaseAdapters {
  self: ScalaScript =>

  def setupMap[T <: Adapter](message: NavajoMessage, map: Adapter, a: T => Unit): T = {
    access.setCurrentOutMessage(message.parent)
    map.load(access)
    try {
      a(map.asInstanceOf[T]);
    } catch {
      case e: Throwable => {
          map.kill
          println("Exception in the map!")
          println ("" + e)
      }

    }
    map.store()
    return map.asInstanceOf[T]
  }

}
