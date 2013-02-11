package com.dexels.navajo.function;

import static org.junit.Assert.assertNotNull;
import navajocore.Version;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

public class FunctionsTest {
	FunctionFactoryInterface fff;
	ClassLoader cl;

	@Before
	public void setUp() throws Exception {
		fff = FunctionFactoryFactory.getInstance();
		cl = getClass().getClassLoader();
//		NavajoCoreAdapterLibrary ncal = new NavajoCoreAdapterLibrary();
		Version v = new Version();
		v.start(null);
	}
	@After
	public void tearDown() throws Exception {
	}
	
	private Navajo createTestNavajo() throws Exception {
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message array = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.setType(Message.MSG_TYPE_ARRAY);
		Message array1 = NavajoFactory.getInstance().createMessage(doc, "Aap");
		array.addElement(array1);
		doc.addMessage(array);
		Property p = NavajoFactory.getInstance().createProperty(doc, "Noot",
				Property.INTEGER_PROPERTY, "10", 10, "", "in");
		array1.addProperty(p);

		Message single = NavajoFactory.getInstance().createMessage(doc,
				"Single");
		doc.addMessage(single);
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"Selectie", "1", "", "in");
		p2.addSelection(NavajoFactory.getInstance().createSelection(doc, "key",
				"value", true));
		single.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "Vuur",
				Property.INTEGER_PROPERTY, "10", 10, "", "out");
		single.addProperty(p3);

		return doc;
	}

	
	@Test
	public void testExecuteScript() throws Exception {

		new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));

		FunctionInterface fi = fff.getInstance(cl, "ExecuteScript");
		fi.reset();
		Navajo doc = createTestNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "aap", "noot",
				"mies", -1);
		doc.addHeader(h);

		fi.setInMessage(doc);

		fi.insertOperand("<tsl/>");

		try {
			Object o = fi.evaluateWithTypeChecking();
		} catch (Exception cnfe) {
		}

	}

	@Test
	public void testEvaluateExpression() throws Exception {

		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(
				new TestNavajoConfig()));

		FunctionInterface fi = fff.getInstance(cl, "EvaluateExpression");
		fi.reset();
		Navajo doc = createTestNavajo();
		Header h = NavajoFactory.getInstance().createHeader(doc, "aap", "noot",
				"mies", -1);
		doc.addHeader(h);

		fi.setInMessage(doc);

		fi.insertOperand("true");

		Object o = fi.evaluateWithTypeChecking();
		assertNotNull(o);
	}
}
