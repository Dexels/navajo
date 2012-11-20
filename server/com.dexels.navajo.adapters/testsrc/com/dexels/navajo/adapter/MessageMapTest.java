package com.dexels.navajo.adapter;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.dexels.navajo.adapter.messagemap.ResultMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;

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
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
			} else {
				Property p1 = NavajoFactory.getInstance().createProperty(n, "Product", Property.STRING_PROPERTY, "PC", 0, "", "out");
				Property p1a = NavajoFactory.getInstance().createProperty(n, "Sub", Property.STRING_PROPERTY, "Desktop", 0, "", "out");
				Property p2 = NavajoFactory.getInstance().createProperty(n, "Age", Property.INTEGER_PROPERTY, (i * 20 )+ "", 0, "", "out");
				String d3 = "2012-01-" + ( (i+1) < 10 ? "0" + (i+1) : (i+1) );
				Property p3 = NavajoFactory.getInstance().createProperty(n, "Date", Property.DATE_PROPERTY,d3, 0, "", "out");
				c.addProperty(p1);
				c.addProperty(p1a);
				c.addProperty(p2);
				c.addProperty(p3);
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
		
		//n.write(System.err);
		
	}

}
