package com.dexels.navajo.document.test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestProperty.class, 
	TestSelection.class, 
	TestMessage.class,
	TestNavajo.class,
	TestBinary.class,
	TestCheckTypes.class,
	TestXmlParser.class})  
public class NavajoDocument  {

  public NavajoDocument() {
  }
}
