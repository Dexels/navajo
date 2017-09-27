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

abstract class ScalaScript extends CompiledScript {
  var runtime: NavajoRuntime = null

  val validations = new ListBuffer[NavajoRuntime => Boolean]

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
    for (e <- validations) {
      if (!e.apply(runtime)) {
        print("VALIDATION ERRRRR")
      }
    }
    run()
  }

  def run(): Unit = throw new RuntimeException("Please override run()!")

  def input = new NavajoDocument(myAccess.getInDoc)
  def output = new NavajoDocument(myAccess.getOutputDoc)
  def access = myAccess

  def callScript(script: String)(withResult: NavajoDocument => Unit) {
    val in = NavajoFactory.create()
    val header = NavajoFactory.createHeader(in, script)
    in.wrapped.addHeader(header.wrapped);
    val outdoc = new NavajoDocument(DispatcherFactory.getInstance.handle(in.wrapped,access.getTenant, true));
    withResult(outdoc);
  }

  def callScript(script: String, in: NavajoDocument)(withResult: NavajoDocument => Unit) {
    val header = NavajoFactory.createHeader(in, script)
    in.wrapped.addHeader(header.wrapped);
    val outdoc = new NavajoDocument(DispatcherFactory.getInstance.handle(in.wrapped,access.getTenant, true));
    if (outdoc.message("error") != null) {
      output.addMessage(outdoc.message("error"))
     
    } else {
       withResult(outdoc);
    }
   
  }



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
		
		params.foreach(param => f.insertOperand(param))
		f.evaluateWithTypeChecking();
		
  }

}