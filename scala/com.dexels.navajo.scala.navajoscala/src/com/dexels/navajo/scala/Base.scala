package com.dexels.navajo.scala
import com.dexels.navajo.document.types.Binary
import com.dexels.navajo.expression.api._
import com.dexels.navajo.document.types.Money

trait Base {
  self: ScalaScript =>

  def setupFunction(func: FunctionInterface): FunctionInterface = {

    func.reset
    func.setAccess(access)

    func.setCurrentMessage(getCurrentOutMsg())
    func.setInMessage(access.getInDoc())
    //	  func.setOutMessage(this.getAccess().getInDoc())
    func.setCurrentMessage(getCurrentOutMsg())
    return func
  }
}
