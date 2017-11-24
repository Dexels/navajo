package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Property

class NavajoProperty(val parent: Property) extends Comparable[NavajoProperty] {

  def name() = parent.getName()
  
  def value() = parent.getTypedValue()
  

  def value(o: Any): NavajoProperty = {
    parent.setAnyValue(o)
    return this
  }

  def propertyType: String = {
    parent.getType()
  }

  def propertyType(propertyType: String): NavajoProperty = {
    parent.setType(propertyType)
    this
  }

  def description(): String = {
    parent.getDescription()
  }

  def description(o: String): NavajoProperty = {
    parent.setDescription(o)
    this
  }

  def cardinality(): String = {
    parent.getCardinality()
  }

  def cardinality(o: String): NavajoProperty = {
    parent.setCardinality(o)
    this
  }

  def direction(): String = {
    parent.getDirection()
  }

  def direction(o: String): NavajoProperty = {
    parent.setDirection(o)
    this
  }

  def subtype(): String = {
    parent.getSubType()
  }

  def subtype(o: String): NavajoProperty = {
    parent.setSubType(o)
    this
  }

  def length(): Int = {
    parent.getLength()
  }

  def length(o: Int): NavajoProperty = {
    parent.setLength(o)
    this
  }

  def compareTo(p2: NavajoProperty): Int = {
    return parent.compareTo(p2.parent)
  }
  

}