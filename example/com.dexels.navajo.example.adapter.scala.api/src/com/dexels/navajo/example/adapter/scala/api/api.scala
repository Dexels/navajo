package com.dexels.navajo.example.adapter.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait ExampleAdaptersComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def monkey(message: NavajoMessage, f: MONKEY => Unit): Unit = {
    val instance = new MONKEY
    setupMap(message, instance, f)
  }
}

class MONKEY(instance: com.dexels.navajo.example.adapter.MonkeyAdapter = new com.dexels.navajo.example.adapter.MonkeyAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.example.adapter.MonkeyAdapter => Unit): Unit = {
    f(instance)
  }
}