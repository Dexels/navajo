package com.dexels.navajo.scala

import com.dexels.navajo.script.api.Access
import com.dexels.navajo.script.api.Mappable

abstract class Adapter(val instance : Mappable) {

  def load(access: Access): Unit = {
      instance.load(access)
  }

  def store(): Unit = {
      instance.store()
  }

  def kill(): Unit = {
      instance.kill()
  }

}