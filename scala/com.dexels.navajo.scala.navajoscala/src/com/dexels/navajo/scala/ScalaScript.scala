package com.dexels.navajo.scala

import com.dexels.navajo.document.Navajo
import com.dexels.navajo.mapping.CompiledScript
import com.dexels.navajo.script.api.Access
import com.dexels.navajo.script.api.Mappable
import com.dexels.navajo.scala.document.NavajoDocument
import com.dexels.navajo.scala.document.NavajoRuntime
import scala.collection.mutable.ListBuffer
import scala.reflect.ManifestFactory
import com.dexels.navajo.scala.document.NavajoMessage
import com.dexels.navajo.scala.document.NavajoFactory
import com.dexels.navajo.server.DispatcherFactory
import scala.runtime
import com.dexels.navajo.parser.FunctionInterface
import com.dexels.navajo.scala.document.ScalaMessage
import com.dexels.navajo.document.json.conversion.JsonTmlFactory
import com.dexels.navajo.document.base.BaseMessageImpl
import com.dexels.navajo.scala.document.ValidationError

abstract class ScalaScript(var printRequest: Boolean = false, var printResponse: Boolean = false) extends CompiledScript {
  var runtime: NavajoRuntime = null
  val validations = new ListBuffer[NavajoDocument => Any]

  def input = new NavajoDocument(myAccess.getInDoc)
  def output = new NavajoDocument(myAccess.getOutputDoc)
  def access = myAccess

  def addValidation(validation: NavajoDocument => Any) {
    validations.append(validation)
  }

  override def execute(a: Access) {
    myAccess = a
    runtime = new NavajoRuntime(a)

    if (printRequest) println(a.getInDoc().write(Console.err))

    if (processValidations()) {
      return ;
    }

    try {
      run()
      if (printResponse) println(a.getOutputDoc().write(Console.err))
      myAccess.setExitCode(Access.EXIT_OK)

    } catch {
      case e: Exception => {
        myAccess.setExitCode(Access.EXIT_EXCEPTION)
        throw e
      }
    }
  }

  def run(): Unit = throw new RuntimeException("Please override run()!")

  def processValidations(): Boolean = {
    val conditionErrMsg = createMessage("ConditionErrors");
    conditionErrMsg.parent.setType("array")

    for (e <- validations) {
      e.apply(input) match {
        case t: ValidationError => conditionErrMsg.addMessage(
          msg => msg.put("Id", t.code).put("Description", t.description))
        case _ => // nothing
      }
    }
    if (conditionErrMsg.parent.getElements().size() > 0) {
      // we have validations
      myAccess.setExitCode(Access.EXIT_VALIDATION_ERR)
      output.addMessage(conditionErrMsg)
      true;
    } else {
      false
    }
  }

  def callScript(script: String)(withResult: NavajoDocument => Unit) {
    val in = NavajoFactory.create()
    val header = NavajoFactory.createHeader(in, script)
    in.wrapped.addHeader(header.wrapped);
    val outdoc = new NavajoDocument(DispatcherFactory.getInstance.handle(in.wrapped, access.getTenant, true));
    withResult(outdoc);
  }

  def callScript(script: String, in: NavajoDocument)(withResult: NavajoDocument => Unit) {
    val header = NavajoFactory.createHeader(in, script)
    in.wrapped.addHeader(header.wrapped);
    val outdoc = new NavajoDocument(DispatcherFactory.getInstance.handle(in.wrapped, access.getTenant, true));
    if (outdoc.message("error") != null) {
      output.addMessage(outdoc.message("error"))
    } else {
      withResult(outdoc);
    }
  }

  def createMessage(name: String) = NavajoFactory.createMessage(NavajoFactory.create, name)

  def callRemoteScript(resource: String, input: NavajoDocument)(withResult: NavajoDocument => Unit) {

  }

  def callAdapter(adapter: Mappable) = {
    adapter.load(myAccess)
    adapter
  }

  def callNavajoFunction(f: FunctionInterface, params: Any*) = {
    f.setInMessage(myAccess.getInDoc)
    f.setCurrentMessage(currentInMsg)
    f.setAccess(myAccess)

    f.reset()

    params.foreach(param => {
      param match {
        case s: Option[Any] => {
          if (s.isDefined) {
            f.insertOperand(s.get)
          } else {
            f.insertOperand(null)
          }
        }
        case _ => f.insertOperand(param)
      }
    })

    f.evaluateWithTypeChecking();

  }

  def global(key: String): String = input.message("__globals__").getString(key).getOrElse(null)
  def aaa(key: String): String = input.message("__aaa__").getString(key).getOrElse(null)

  override def finalBlock(a: Access) {

  }

  override def setValidations() {

  }

}