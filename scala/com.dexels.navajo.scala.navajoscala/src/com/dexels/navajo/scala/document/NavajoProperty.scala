package com.dexels.navajo.scala.document

import com.dexels.navajo.document.Property

class NavajoProperty(val parent: Property) {

  def value() = {
    parent.getTypedValue()
  }

  def value(o: Any): NavajoProperty = {
    parent.setAnyValue(o)
    return this
  }

  def propertyType: String = {
    parent.getType()
  }

  def propertyType(propertyType: String): NavajoProperty = {
    parent.setType(propertyType)
    return this
  }

  def description(): String = {
    parent.getDescription()
  }

  def description(o: String): NavajoProperty = {
    parent.setDescription(o)
    return this
  }

  def cardinality(): String = {
    parent.getCardinality()
  }

  def cardinality(o: String): NavajoProperty = {
    parent.setCardinality(o)
    return this
  }

  def direction(): String = {
    parent.getDirection()
  }

  def direction(o: String): NavajoProperty = {
    parent.setDirection(o)
    return this
  }

  def subtype(): String = {
    parent.getSubType()
  }

  def subtype(o: String): NavajoProperty = {
    parent.setSubType(o)
    return this
  }

  def length(): Int = {
    parent.getLength()
  }

  def length(o: Int): NavajoProperty = {
    parent.setLength(o)
    return this
  }

}