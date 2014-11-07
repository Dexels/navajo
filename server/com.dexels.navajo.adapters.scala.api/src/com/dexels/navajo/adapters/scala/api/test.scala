package com.dexels.navajo.adapters.scala.api

import com.dexels.navajo.scala.ScalaCompiledScript


class ScalaQueryClub extends ScalaCompiledScript with AdaptersComponent  {

   override def run() {
        System.err.println("Access: " + runtime.parent.getInstance())
        val clubData = output.addMessage("ClubData");
   }
}