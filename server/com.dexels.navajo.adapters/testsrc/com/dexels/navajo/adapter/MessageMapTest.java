package com.dexels.navajo.adapter;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;


import org.junit.Test;

import com.dexels.navajo.adapter.messagemap.ResultMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;

public class MessageMapTest {

	@Test
	public void test() throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "Array");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);
		for ( int i = 0; i < 10; i++ ) {
			Message c = NavajoFactory.getInstance().createMessage(n, "Array");
			array.addElement(c);
			if ( i % 2 == 0) {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 10 )+ "", 0, "", "out");
				String d3 = "2012-01-" + ( (i+1) < 10 ? "0" + (i+1) : i);
				Property p3 = NavajoFactory.getInstance().createProperty(n, "Date", Property.DATE_PROPERTY,d3, 0, "", "out");
				Property p4 = NavajoFactory.getInstance().createProperty(n, "Gender", "1", "Geslacht", "");
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Man", "Man", false));
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Vrouw", "Vrouw", true));
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
				c.addProperty(p4);
			} else {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 20 )+ "", 0, "", "out");
				String d3 = "2012-01-" + ( (i+1) < 10 ? "0" + (i+1) : (i+1) );
				Property p3 = NavajoFactory.getInstance().createProperty(n, "Date", Property.DATE_PROPERTY,d3, 0, "", "out");
				Property p4 = NavajoFactory.getInstance().createProperty(n, "Gender", "1", "Geslacht", "");
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Man", "Man", false));
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Vrouw", "Vrouw", true));
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
				c.addProperty(p4);
			}
		}
		n.write(System.err);
		
		Access a = new Access();
		a.setOutputDoc(n);
		
		MessageMap mm = new MessageMap();
		mm.load(a);
		mm.setGroupBy("Product,Sub");
		
		mm.setJoinMessage1("Array");
		mm.setJoinType("outer");
		//mm.setRemoveSource(true);
		
		Message resultMessage = NavajoFactory.getInstance().createMessage(n, "ResultingMessage");
		resultMessage.setType("array");
		n.addMessage(resultMessage);
		
		a.setCurrentOutMessage(resultMessage);
		
		ResultMessage [] result = mm.getResultMessage();
		
//		for (int i = 0; i < result.length; i++) {
//			result[i].load(a);
//			result[i].getMsg().write(System.err);
//			System.err.println("Count: " + result[i].getCount("Age"));
//			System.err.println("Sum: " + result[i].getSum("Age"));
//			System.err.println("Average: " + result[i].getAvg("Age"));
//			result[i].store();
//		}
		a.setCurrentOutMessage(null);
		mm.store();
		
		assertEquals(2, result.length);
		assertEquals(5, result[0].getCount("Age"));
		
		assertEquals(200.0, result[0].getSum("Age"),1);
		assertEquals(500.0, result[1].getSum("Age"),1);
		
		assertEquals(40.0, result[0].getAvg("Age"),1);
		assertEquals(100.0, result[1].getAvg("Age"),1);
		
		assertEquals(80.0, result[0].getMax("Age"));
		assertEquals(0.0, result[0].getMin("Age"));
		assertEquals(180.0, result[1].getMax("Age"));
		assertEquals(20.0, result[1].getMin("Age"));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-01-01"), result[0].getMin("Date"));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-01-02"), result[1].getMin("Date"));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-01-09"), result[0].getMax("Date"));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parseObject("2012-01-10"), result[1].getMax("Date"));
		
		
	}
	
	@Test
	public void test2() throws Exception {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "Array");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);
		for ( int i = 0; i < 10; i++ ) {
			Message c = NavajoFactory.getInstance().createMessage(n, "Array");
			array.addElement(c);
			if ( i % 2 == 0) {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 10 )+ "", 0, "", "out");
				String d3 = "2012-01-" + ( (i+1) < 10 ? "0" + (i+1) : i);
				Property p3 = NavajoFactory.getInstance().createProperty(n, "Date", Property.DATE_PROPERTY,d3, 0, "", "out");
				Property p4 = NavajoFactory.getInstance().createProperty(n, "Gender", "1", "Geslacht", "");
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Man", "Man", false));
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Vrouw", "Vrouw", true));
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
				c.addProperty(p4);
			} else {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 20 )+ "", 0, "", "out");
				String d3 = "2012-01-" + ( (i+1) < 10 ? "0" + (i+1) : (i+1) );
				Property p3 = NavajoFactory.getInstance().createProperty(n, "Date", Property.DATE_PROPERTY,d3, 0, "", "out");
				Property p4 = NavajoFactory.getInstance().createProperty(n, "Gender", "1", "Geslacht", "");
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Man", "Man", false));
				p4.addSelection(NavajoFactory.getInstance().createSelection(n, "Vrouw", "Vrouw", true));
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
				c.addProperty(p4);
			}
		}
		n.write(System.err);
		
		Access a = new Access();
		a.setOutputDoc(n);
		
		MessageMap mm = new MessageMap();
		mm.load(a);
		//mm.setGroupBy("Product,Sub");
		
		mm.setJoinMessage1("Array");
		mm.setJoinType("outer");
		//mm.setRemoveSource(true);
		
		Message resultMessage = NavajoFactory.getInstance().createMessage(n, "ResultingMessage");
		resultMessage.setType("array");
		n.addMessage(resultMessage);
		
		a.setCurrentOutMessage(resultMessage);
		
		ResultMessage [] result = mm.getResultMessage();
		
		a.setCurrentOutMessage(null);
		mm.store();
		
		assertEquals("Vrouw", result[0].getProperty("Gender"));
		//n.write(System.err);
		
	}
	
	
	

	@Test
	public void test3() throws Exception {
		//message1 array and message2 simple message test but with one access obj
		
		/* building message 1 array*/
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "Array");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);
		for ( int i = 0; i < 4; i++ ) {
			Message c = NavajoFactory.getInstance().createMessage(n, "Array");
			
			c.setIndex(i);
			c.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
			
			array.addElement(c);
			if ( i % 2 == 0) {
				
				
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 10 )+ "", 0, "", "out");
				
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
			} else {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 20 )+ "", 0, "", "out");
				
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
			}
		}
		n.write(System.err);
		
		
		/* building message 2 simple message */
		Navajo n2 = NavajoFactory.getInstance().createNavajo();
		Message simpleMsg = NavajoFactory.getInstance().createMessage(n, "Simple");
		
		simpleMsg.setType(Message.MSG_TYPE_SIMPLE);		
		
		Property p1 = NavajoFactory.getInstance().createProperty(n, "Prop", Property.STRING_PROPERTY, "Test1", 0, "", "out");
		Property p1a = NavajoFactory.getInstance().createProperty(n, "SubProp", Property.STRING_PROPERTY, "Test2", 0, "", "out");
		
		simpleMsg.addProperty(p1);
		simpleMsg.addProperty(p1a);
		n2.addMessage(simpleMsg);
		/*end building messages*/
		
		n2.write(System.err);
		
		
		Access a = new Access();
		a.setOutputDoc(n);
		
		
		MessageMap mm = new MessageMap();
		
		mm.load(a);
		mm.setJoinMessage1("Array");
		
		/*for the 2nd message*/
		a.setOutputDoc(n2);
		mm.load(a);
		mm.setJoinMessage2("Simple");
		mm.setJoinType("outer");
		
		Message resultMessage = NavajoFactory.getInstance().createMessage(n, "ResultingMessage");
		resultMessage.setType("array");
		n.addMessage(resultMessage);
		
		a.setCurrentOutMessage(resultMessage);
		
		ResultMessage [] result = mm.getResultMessage();
		
		a.setCurrentOutMessage(null);
		
		mm.store(); 
		
		for(int l=0; l < result.length; l++) {
			assertEquals("PC", result[l].getProperty("Product"));
			assertEquals("Test1", result[l].getProperty("Prop"));
			assertEquals("Test2", result[l].getProperty("SubProp"));
		}
		
	}
	
	
	@Test
	public void test4() throws Exception {
		//message1 array and message2 array test 
		
		/* building message 1 array*/
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(n, "productArray");
		n.addMessage(array);
		array.setType(Message.MSG_TYPE_ARRAY);
		
		
		/*Creating the first element of the message array productArray*/
		Message c1e1 = NavajoFactory.getInstance().createMessage(n, "productArray");
		c1e1.setIndex(0);
		c1e1.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
		array.addElement(c1e1);
		
		Property c1e1p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property c1e1p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
		Property c1e1p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (0)+ "", 0, "", "out");
		
		c1e1.addProperty(c1e1p1);
		c1e1.addProperty(c1e1p1a);
		c1e1.addProperty(c1e1p2);
		
		/*Creating the second element of the message array productArray */
		Message c1e2 = NavajoFactory.getInstance().createMessage(n, "productArray");
		c1e2.setIndex(1);
		c1e2.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
		array.addElement(c1e2);
		
		Property c1e2p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property c1e2p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
		Property c1e2p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (20)+ "", 0, "", "out");
		
		c1e2.addProperty(c1e2p1);
		c1e2.addProperty(c1e2p1a);
		c1e2.addProperty(c1e2p2);
		
		n.write(System.err);
		
		
		/* building message 2 array*/
		Navajo n2 = NavajoFactory.getInstance().createNavajo();
		Message array2 = NavajoFactory.getInstance().createMessage(n2, "coreArray");
		n2.addMessage(array2);
		array2.setType(Message.MSG_TYPE_ARRAY);
		
		
		/*Creating the first element of the message array coreArray */
		Message c2e1 = NavajoFactory.getInstance().createMessage(n2, "coreArray");
		
		c2e1.setIndex(0);
		c2e1.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
		
		array2.addElement(c2e1);
		
		Property c2e1p1 = NavajoFactory.getInstance().createProperty(n2, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property c2e1p2 = NavajoFactory.getInstance().createProperty(n2, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
		Property c2e1p3 = NavajoFactory.getInstance().createProperty(n2, "Core", Property.STRING_PROPERTY, "I", 0, "", "out");
		
		c2e1.addProperty(c2e1p1);
		c2e1.addProperty(c2e1p2);
		c2e1.addProperty(c2e1p3);
		
		/*Creating the second element of the message array coreArray */	
		Message c2e2 = NavajoFactory.getInstance().createMessage(n2, "coreArray");
		
		c2e2.setIndex(0);
		c2e2.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
		
		array2.addElement(c2e2);
		
		Property c2e2p1 = NavajoFactory.getInstance().createProperty(n2, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
		Property c2e2p2 = NavajoFactory.getInstance().createProperty(n2, "Sub", Property.STRING_PROPERTY, "Laptop", 0, "", "out");
		Property c2e2p3 = NavajoFactory.getInstance().createProperty(n2, "Core", Property.STRING_PROPERTY, "A", 0, "", "out");
		
		c2e2.addProperty(c2e2p1);
		c2e2.addProperty(c2e2p2);
		c2e2.addProperty(c2e2p3);
		
		
		n2.write(System.err);
		
		
		Access a = new Access();
		a.setOutputDoc(n);
		
		
		MessageMap mm = new MessageMap();
		
		mm.load(a);
		mm.setJoinMessage1("productArray");
		
		/*for the 2nd message*/
		a.setOutputDoc(n2);
		mm.load(a);
		
		mm.setJoinMessage2("coreArray");
		mm.setJoinType("inner");
		mm.setJoinCondition("Sub=Sub");
		
		Message resultMessage = NavajoFactory.getInstance().createMessage(n, "ResultingMessage");
		resultMessage.setType("array");
		n.addMessage(resultMessage);
		
		a.setCurrentOutMessage(resultMessage);
		
		ResultMessage [] result = mm.getResultMessage();
		
		a.setCurrentOutMessage(null);
		
		mm.store(); 
		
		for(int l=0; l < result.length; l++) {
			assertEquals("Laptop", result[l].getProperty("Sub"));
		}
		
		
		
	}


}
