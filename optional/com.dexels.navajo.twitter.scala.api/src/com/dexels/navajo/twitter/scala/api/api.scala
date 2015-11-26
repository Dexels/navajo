package com.dexels.navajo.twitter.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait TwitterComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaScript => 
  def twitter(message: NavajoMessage, f: TWITTER => Unit): Unit = {
    val instance = new TWITTER
    setupMap(message, instance, f)
  }
}

class TWITTER(instance: com.dexels.twitter.TwitterAdapter = new com.dexels.twitter.TwitterAdapter) extends Adapter(instance) {
  def instance(f: com.dexels.twitter.TwitterAdapter => Unit) = {
    f(instance)
  }
  def login(): Unit = {
    ()
  }
  def tweet(text: java.lang.String): Unit = {
    instance.setStatus(text)
  }
}