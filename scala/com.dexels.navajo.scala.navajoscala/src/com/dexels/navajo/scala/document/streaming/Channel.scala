package com.dexels.navajo.scala.document.streaming

import akka.actor.Actor
import com.dexels.navajo.scala.document.NavajoMessage
import scala.concurrent.Future
import scala.collection.mutable.MutableList
import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer

/**
 * @author chris
 */
class Channel extends Actor {
    val childChannels: ListBuffer[ActorRef] = ListBuffer()
    val myMessages: ListBuffer[NavajoMessage] = ListBuffer()
    val getAllMessagesRequests: ListBuffer[ActorRef] = ListBuffer()
    var messageRequests: Map[ActorRef, GetMessage] = Map()

    var state = "started"

    def receive: Actor.Receive = {
        case addmsg @ SubmitMessage(msg) =>
            myMessages += msg
            println(s"Adding  message ${msg.name} to ${self.toString()}")
            for ((actorref, reqmsg) <- messageRequests) {
                if (reqmsg.messageName == msg.name) {
                    println(s"Delevering message ${msg.name} to ${actorref.toString()}")
                    actorref ! addmsg

                    // We delivered our message, so remove the actorref from the list
                    messageRequests -= actorref
                }
            }

        case GetMessage(messagename, f) =>
            val msgOption = myMessages.find { msg => msg.name == messagename }
            msgOption match {
                case Some(msg) => sender ! SubmitMessage(msg)
                case None =>  
                    println(s"Didn't find message ${messagename} yet, going to wait for it")
                    messageRequests += (sender() -> GetMessage(messagename, f))
            }

        case GetAllMessages() =>
            state match {
                case "stopped" => {
                        println("replying directly")
                        sender() ! myMessages}
                
                case "started" => 
                    println(s"${self.toString()} Adding sender ${sender()} to list of allmessages")
                    getAllMessagesRequests += sender()
            }

        case Stop() =>
            // Let any outstanding requests to us know that we are stopping
            println(s" ${self.toString()} Going to close my channel!")
//            for ((actorref, reqmsg) <- messageRequests) {
//                actorref ! Stop()
//            }

            for (actorref <- getAllMessagesRequests) {
                println(s"Delevering all my messages to ${actorref}")
                actorref ! myMessages
            }
            state = "stopped"

        case default => println(s"${context.self.toString()}  Got unknown message: $default")
    }
}