package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Message
import com.dexels.navajo.document.Property

class NavajoMessage(val parent: Message) {

  def rootDoc: NavajoDocument = {
    new NavajoDocument(parent.getRootDoc())
  }
  def message(name: String): NavajoMessage = {
    return new NavajoMessage(parent.getMessage(name));
  }

  def message(index: Int): NavajoMessage = {
    return new NavajoMessage(parent.getMessage(index));
  }

  def name: String = {
    parent.getName()
  }

  def name(n: String): NavajoMessage = {
    parent.setName(n)
    return this
  }

  def addMessage(name: String): NavajoMessage = {
    val message = NavajoFactory.createMessage(rootDoc, name)
    message.parent.write(System.out);
    new NavajoMessage(parent.addMessage(message.parent))
  }

  def addMessage(): NavajoMessage = {
    val message = NavajoFactory.createMessage(rootDoc, this.name)
    parent.addElement(message.parent)
    return message
  }

  def addMessage(f: NavajoMessage => Unit): NavajoMessage = {
    val message = NavajoFactory.createMessage(rootDoc, this.name)
    parent.addElement(message.parent)
    f(message)
    return message
  }
  
  def addArrayMessage(name: String): NavajoMessage = {
    val message = NavajoFactory.createArrayMessage(rootDoc, name)
    new NavajoMessage(parent.addMessage(message.parent))
  }

  def each(f: NavajoMessage => Unit) = {
    val it = parent.getAllMessages().iterator();
    while (it.hasNext()) {
      f(new NavajoMessage(it.next()));
    }
  }

  def freejoin(join: NavajoMessage, matchCondition: (NavajoMessage, NavajoMessage) => Unit) = {
    val it = parent.getAllMessages().iterator();
    while (it.hasNext()) {
      val inner_it = join.parent.getAllMessages().iterator();
      val outer = new NavajoMessage(it.next())
      while (inner_it.hasNext()) {
        val inner = new NavajoMessage(inner_it.next())
        matchCondition(outer, inner)
      }
    }
  }

  def equijoin(join: NavajoMessage, matchCondition: (NavajoMessage, NavajoMessage) => Unit) = {
    val it = parent.getAllMessages().iterator();
    while (it.hasNext()) {
      val inner_it = join.parent.getAllMessages().iterator();
      val outer = new NavajoMessage(it.next())
      while (inner_it.hasNext()) {
        val inner = new NavajoMessage(inner_it.next())
        matchCondition(outer, inner)
      }
    }
  }

  def one(f: NavajoMessage => Unit) = {
    f(this)
  }
  
  
  /* Property methods */
  
  def getString(key : String) : Option[String] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      return Some(prop.getValue.asInstanceOf[String])
    }
    None
  }
  
  def getInt(key : String) : Option[Int] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      val value : Any =  prop.getTypedValue
      value match {
        case value : Integer => return Some(value)
        case value : String => return Some(value.toInt)
        case _ =>  new RuntimeException("Invalid integer: ")
      }
    }
    None
  }
  
  def getBoolean(key : String) : Option[Boolean] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      return Some(prop.getValue.asInstanceOf[Boolean])
    }
    None
  }
  
  
  def put(key : String, value : Option[Any]) : NavajoMessage = {
    if (value.isDefined) {
      put(key, value.get)
    } else {
      put(key)
    }
    this
  }
  
  def put(key : String) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(null)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.STRING_PROPERTY, null, 0, null, Property.DIR_IN)
       parent.addProperty(p)
    }
    this
  }
  
  def put(key : String, value : String) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(value)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.STRING_PROPERTY, value, 0, null, Property.DIR_IN)
       parent.addProperty(p)
    }
    this
  }
  
  def put(key : String, value : Int) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(value)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.INTEGER_PROPERTY, null, 0, null, Property.DIR_IN)
       p.setAnyValue(value)
       parent.addProperty(p)
    }
    this
  }
  
  def put(key : String, value : Boolean) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(value)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.BOOLEAN_PROPERTY, null, 0, null, Property.DIR_IN)
       p.setAnyValue(value)
       parent.addProperty(p)
    }
    this
  }
  
  def put(key : String, value : Any) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(value)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.STRING_PROPERTY, null, 0, null, Property.DIR_IN)
       value match {
         case v : Option[Any] => {
            if (v.isDefined) p.setAnyValue(v.get) 
         }
         case _ =>  p.setAnyValue(value)
       }
       parent.addProperty(p)
    }
    this
  }

//  def property(name: String): NavajoProperty = {
//
//    val prop = parent.getProperty(name)
//    if (prop == null) {
//      parent.write(System.err)
//      //throw new NullPointerException("No property found: " + name);
//      return null
//    }
//    new NavajoProperty(prop)
//  }
//
//  def propertyValue(name: String): Any = {
//    val p = property(name)
//    if (p == null) {
//      return null
//    }
//    return p.value
//  }



//  def addProperty(name: String): NavajoProperty = {
//    val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), name, Property.STRING_PROPERTY, null, 0, null, Property.DIR_IN)
//    parent.addProperty(p)
//    return new NavajoProperty(p)
//  }

//  def addProperty(p: NavajoProperty): NavajoProperty = {
//    parent.addProperty(p.parent)
//    return p;
//  }
}