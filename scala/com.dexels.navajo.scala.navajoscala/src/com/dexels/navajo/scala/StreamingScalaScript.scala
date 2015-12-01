package com.dexels.navajo.scala

import com.dexels.navajo.scala.document.NavajoDocument
import com.dexels.navajo.scala.document.streaming.NavajoDocumentStreaming
import com.dexels.navajo.scala.document.NavajoRuntime
import com.dexels.navajo.scala.document.NavajoMessage
import com.dexels.navajo.scala.document.NavajoFactory
import com.dexels.navajo.scala.document.streaming.NavajoDocumentStreaming
import com.dexels.navajo.scala.document.streaming.Channel
import com.dexels.navajo.scala.document.streaming.Stop
import com.dexels.navajo.scala.document.streaming.GetAllMessages
import com.dexels.navajo.scala.document.streaming.GetMessage
import com.dexels.navajo.mapping.CompiledScript
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer
import scala.reflect.ManifestFactory
import akka.actor.Props
import akka.actor.ActorSystem;
import akka.actor.ActorSystem
import akka.util.Timeout
import akka.actor.ActorRef
import akka.pattern.{ ask, pipe }
import scala.util.{ Success, Failure }
import scala.concurrent._
import ExecutionContext.Implicits.global
import com.dexels.navajo.script.api.Access
import com.dexels.navajo.script.api.Mappable


abstract class StreamingScalaScript extends CompiledScript {
   
    var runtime: NavajoRuntime = null
    implicit val timeout = Timeout(60 seconds)

    val validations = new ListBuffer[NavajoRuntime => Boolean]
    val system = ActorSystem("mySystem")

    val input = system.actorOf(Props[Channel], "inputchannel")
    val output = system.actorOf(Props[Channel], "outputchannel")

    def createChannel(channelName: String, parent : ActorRef = null): ActorRef = {
       // parent.
        system.actorOf(Props[Channel], channelName)
    }
    
    def transform (futuremessage : Future[NavajoMessage], f: NavajoMessage => NavajoMessage ) : Future[Unit] = {
        Future[Unit] {
            futuremessage.onSuccess {
                case result => f(result)
            }
        }
    }

    override def finalBlock(a: Access) {

    }

    override def setValidations() {
        //runtime.input.property("")
    }

    def addValidation(validation: NavajoRuntime => Boolean) {
        validations.append(validation)
    }

    override def execute(a: Access) {

        myAccess = a
        runtime = new NavajoRuntime(a)
        run()
//        system.stop(input)
//        system.stop(output)
    }

    def run()

    def callScript(script: String) {
        callScript(script, input, output)
    }

    def callScript(script: String, output: ActorRef) {
        callScript(script, input, output)
    }
    

    def forwardMessage(messageName: String, from: ActorRef, to: ActorRef, transform : (NavajoMessage => NavajoMessage) = null) {
        from ? GetMessage("test1", transform) pipeTo to
    }

    def callScript(script: String, input: ActorRef, output: ActorRef) {
        
        val allMessages = input ? GetAllMessages()

        allMessages.onComplete {
            case msg =>  {
                println(s" Calling script, asked for test1 message  $msg")
            }
        }
    }

    def callRemoteScript(resource: String, input: NavajoDocument)(withResult: NavajoDocument => Unit) {

    }

    def callAdapter(adapter: Class[_ <: Mappable], withMap: Mappable => Unit) {

    }

    def scriptAccess: Access = {
        return myAccess
    }

    def test {
    }

}
