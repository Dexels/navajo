package com.dexels.navajo.kml.scala.api

import com.dexels.navajo.scala.document._

import com.dexels.navajo.scala._

trait KmlComponent extends com.dexels.navajo.scala.BaseAdapters with com.dexels.navajo.scala.Base { self: com.dexels.navajo.scala.ScalaCompiledScript => 
  def kml(message: NavajoMessage, f: KML => Unit): Unit = {
    val instance = new KML
    setupMap(message, instance, f)
  }
}

class KML(instance: com.dexels.navajo.geo.KMLMap = new com.dexels.navajo.geo.KMLMap) extends Adapter(instance) {
  def instance(f: com.dexels.navajo.geo.KMLMap => Unit) {
    f(instance)
  }
}