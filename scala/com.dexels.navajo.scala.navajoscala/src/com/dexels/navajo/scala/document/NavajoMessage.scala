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

  def property(name: String): NavajoProperty = {

    val prop = parent.getProperty(name)
    if (prop == null) {
      parent.write(System.err)
      throw new NullPointerException("No property found: " + name);
    }
    return new NavajoProperty(prop)
  }

  def propertyValue(name: String): Any = {
    val p = property(name)
    if (p == null) {
      return null
    }
    return p.value
  }

  //    public abstract Property createProperty(Navajo tb, String name, String type,
  //                                          String value, int length,
  //                                          String description, String direction) throws

  def addProperty(name: String): NavajoProperty = {
    val p = com.dexels.navajo.document.NavajoFactory.getInstance().createProperty(parent.getRootDoc(), name, Property.STRING_PROPERTY, null, 0, null, Property.DIR_IN)
    parent.addProperty(p)
    return new NavajoProperty(p)
  }

  def addProperty(p: NavajoProperty): NavajoProperty = {
    parent.addProperty(p.parent)
    return p;
  }
}