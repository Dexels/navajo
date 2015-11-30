package com.dexels.navajo.scala.document

import com.dexels.navajo.script.api.Access

class NavajoRuntime(val parent: Access) {
  def input: NavajoDocument = {
    new NavajoDocument(parent.getInDoc())
  }

  def output: NavajoDocument = {
    new NavajoDocument(parent.getOutputDoc())
  }

  def name: String = {
    parent.getRpcName()
  }

  def username: String = {
    parent.getRpcUser()
  }

}