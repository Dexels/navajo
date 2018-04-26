package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Message
import com.dexels.navajo.document.Property
import java.util.Date
import collection.JavaConverters._
import java.util.StringTokenizer
import com.dexels.navajo.document.Selection
import java.util.ArrayList

class NavajoMessage(val parent: Message) {

  def rootDoc: NavajoDocument = {
    new NavajoDocument(parent.getRootDoc())
  }
  def message(name: String): NavajoMessage = {
    new NavajoMessage(parent.getMessage(name));
  }

  def message(index: Int): NavajoMessage = {
    new NavajoMessage(parent.getMessage(index));
  }

  def name: String = {
    parent.getName()
  }

  def name(n: String): NavajoMessage = {
    parent.setName(n)
    this
  }

  def addMessage(name: String): NavajoMessage = {
    val message = NavajoFactory.createMessage(rootDoc, name)
    new NavajoMessage(parent.addMessage(message.parent))
  }

  def addMessage(message : NavajoMessage ): NavajoMessage = {
    message.parent.setRootDoc(parent.getRootDoc)
    parent.addElement(message.parent)
    message
  }
  
  def copyMessage(message : NavajoMessage ): NavajoMessage = {
    addMessage(new NavajoMessage(message.parent.copy(this.parent.getRootDoc)))
  }

  def addMessage(f: NavajoMessage => Unit): NavajoMessage = {
    val message = NavajoFactory.createMessage(rootDoc, this.name)
    parent.addElement(message.parent)
    f(message)
    message
  }
  
  def addArrayMessage(name: String): NavajoMessage = {
    val message = NavajoFactory.createArrayMessage(rootDoc, name)
    new NavajoMessage(parent.addMessage(message.parent))
  }
  
  def removeMessage(name: String) : NavajoMessage = {
    parent.removeMessage(parent.getMessage(name))
    this
  }
  
  def removeMessage(message: NavajoMessage) : NavajoMessage = {
    parent.removeMessage(message.parent)
    this
  }
  
  def each(f: NavajoMessage => Unit) : Unit = {
    if (parent == null) {
      return;
    }
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
  
  def sort(sort: (NavajoMessage, NavajoMessage) => Boolean)(f: NavajoMessage => Unit) : Unit = {
    parent.getElements.asScala
      .sortWith((a, b) => sort(new NavajoMessage(a), new NavajoMessage(b)))
      .foreach(m => f(new NavajoMessage(m)))
  }
  
  def filter(f: NavajoMessage => Boolean) = {
    parent.getElements.forEach(m => if (!f(new NavajoMessage(m))) {
      parent.removeMessage(m)
    })
    this
  }
  
    // Sort the messages
  def sort(orderBy:String)(f: NavajoMessage => Unit) : Unit = {
    this.sort((msg1, msg2) => {
       var result = true
       var checkNext = true
       val st = new StringTokenizer(orderBy, ",")
       while (st.hasMoreElements() && checkNext) {
        var elem = st.nextToken().trim();
        var asc = true;
        if (elem.contains(" ")) {
          val splitted = elem.split(" ")
          elem = splitted(0)
          asc = !splitted(1).equals("DESC")
        }
        val prop1 = msg1.property(elem)
        val prop2Value = msg2.property(elem).getOrElse(null)
        if (prop1.isDefined) {
          val res = if (asc) prop1.get.compareTo(prop2Value) else prop2Value.compareTo(prop1.get)
          if (res != 0) {
            checkNext = false 
            result = res < 0;
          } 
        }
      }
      result
    })(f)
  }

  def one(f: NavajoMessage => Unit) = {
    f(this)
  }
  
  
  /* Property methods */
  
  def getAny(key : String) : Option[Any] = {
    val prop = parent.getProperty(key)
    if (prop != null && prop.getValue != null) {
      return Some(prop.getTypedValue)
    }
    None
  }
  def getString(key : String) : Option[String] = {
    val prop = parent.getProperty(key)
    if (prop != null && prop.getValue != null) {
      val value : Any = prop.getTypedValue
      value match {
        case value : String => return Some(value)
        case value : Integer => return Some(value.toString)
        case _ =>  new RuntimeException("Invalid string: ")
      }
    }
    None
  }
  
  
  
  def getDate(key : String) : Option[Date] = {
    val prop = parent.getProperty(key)
    if (prop != null && prop.getValue != null) {
      return Some(prop.getTypedValue.asInstanceOf[Date])
    }
    None
  }
  
  def getInt(key : String) : Option[Int] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      val value : Any = prop.getTypedValue
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
       val value : Any =  prop.getTypedValue
        value match {
          case value : Boolean => return Some(value)
          case value : String => {
            value match {
              case "1" => return Some(true)
              case _ => return Some(false)
            }
          }
          case _ =>  new RuntimeException("Invalid boolean: ")
        }
    }
    None
  }
  
  def getList(key : String) : Option[List[String]] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
       if (prop.getType().equals(Property.LIST_PROPERTY)) {
          return Some(prop.getTypedValue.asInstanceOf[java.util.List[String]].asScala.toList)
       }
    }
    None
  }
  
  def getSelections(key : String) : Option[List[Selection]] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      if (prop.getType().equals(Property.SELECTION_PROPERTY)) {        
        return Some(prop.getAllSelections.asInstanceOf[java.util.ArrayList[Selection]].asScala.toList)
      }
    }
    None
  }
  
  def getSelectedValue(key : String) : Option[String] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      if (prop.getType().equals(Property.SELECTION_PROPERTY)) {
        return Some(prop.getSelected.getValue())
      }
    }
    None
  }
  def getSelectedName(key : String) : Option[String] = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      if (prop.getType().equals(Property.SELECTION_PROPERTY)) {
        return Some(prop.getSelected.getName())
      }
    }
    None
  }
  
  def put(key : String, value : Option[Any]) : NavajoMessage = {
    if (value != null && value.isDefined) {
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

  def put(key: String, value: Any, direction: String = Property.DIR_IN, description: String = null) = {
    val prop = parent.getProperty(key)
    if (prop != null) {
      prop.setAnyValue(value)
      prop.setDirection(direction)
      prop.setDescription(description)
    } else {
       val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), key, Property.STRING_PROPERTY, null, 0, description, direction)
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
  
  def eachProperty(f: NavajoProperty => Unit) : Unit = {
    if (parent == null) {
      return;
    }
    parent.getAllProperties().asScala.foreach(p => f(new NavajoProperty(p)))
  }
  
  def nrChildren : Integer = parent.getAllMessages.size()

  def property(name: String): Option[NavajoProperty] = {

    val prop = parent.getProperty(name)
    if (prop == null) {
      None
    } else {
      Some(new NavajoProperty(prop))
    }
  }
  


}