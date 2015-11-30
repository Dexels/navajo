package com.dexels.navajo.scala.document.streaming

import com.dexels.navajo.scala.document.NavajoMessage
import akka.actor.ActorRef

sealed trait Event

case class SubmitMessage(message: NavajoMessage) extends Event

case class GetMessage(messageName: String, f: NavajoMessage => NavajoMessage = (msg)=>msg ) extends Event

case class GetAllMessages() extends Event

case class Stop() extends Event
