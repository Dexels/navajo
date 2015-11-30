package com.dexels.navajo.scala
import com.dexels.navajo.document.types.Binary
import com.dexels.navajo.parser._
import com.dexels.navajo.document.types.Money

trait Base {
  self: ScalaScript =>

  def setupFunction(func: FunctionInterface): FunctionInterface = {

    func.reset
    func.setAccess(scriptAccess)

    func.setCurrentMessage(getCurrentOutMsg())
    func.setInMessage(scriptAccess.getInDoc())
    //	  func.setOutMessage(this.getAccess().getInDoc())
    func.setCurrentMessage(getCurrentOutMsg())
    return func
  }
}