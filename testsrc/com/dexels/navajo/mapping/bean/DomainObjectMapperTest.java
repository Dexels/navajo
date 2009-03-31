package com.dexels.navajo.mapping.bean;

import java.lang.reflect.Method;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.server.Access;

import junit.framework.TestCase;

public class DomainObjectMapperTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateArray() throws Exception {
		Relation [] relations = new Relation[5];
		for (int i = 0; i < relations.length; i++) {
			relations[i] = new Relation();
			relations[i].setId(i+"");
		}
		DomainObjectMapper [] doms =  DomainObjectMapper.createArray(relations);
		
		assertEquals(5, doms.length);
		assertEquals( ((Relation) doms[1].getMyObject()).getId(), "1"); 
		
		DomainObjectMapper [] doms2 =  DomainObjectMapper.createArray(null);
		assertNull(doms2);
	}

	public void testSetMethodReference() throws Exception {
		TestPOJO tp = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper(tp);
		Method m = dom.setMethodReference(TestPOJO.class, "name", new Class[]{String.class});
		assertNotNull(m);
		m.invoke(tp, new Object[]{"Nak"});
		assertEquals("Nak", tp.getName());
		
		// Multiple parameters.
		Relation r = new Relation();
		r.setId("basis");
		m = dom.setMethodReference(TestPOJO.class, "name", new Class[]{Relation.class, String.class});
		assertNotNull(m);
		m.invoke(tp, new Object[]{r, new String("school")});
		assertEquals("basisschool", tp.getName());
	}

	public void testSetObjectName() throws Exception {
		DomainObjectMapper dom = new DomainObjectMapper();
		dom.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		assertNotNull(dom.getMyObject());
		assertEquals(com.dexels.navajo.mapping.bean.Relation.class, dom.getMyObject().getClass());
	}

	public void testSetAttributeName() throws Exception {
		DomainObjectMapper dom = new DomainObjectMapper(new Relation());
		dom.setAttributeName("id");
		assertEquals("id", dom.getAttributeName());
	}

	public void testSetAttributeValue() throws Exception {
		Relation r = new Relation();
		DomainObjectMapper dom = new DomainObjectMapper(r);
		dom.setAttributeName("id");
		dom.setAttributeValue("Dogmatici");
		assertEquals("Dogmatici", r.getId());
		
		DomainObjectMapper dom2 = new DomainObjectMapper();
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		dom2.setAttributeName("id");
		dom2.setAttributeValue("Dogmatici");
		assertEquals("Dogmatici", ((Relation) dom2.getMyObject()).getId());
		
	}

	public void testSetDomainObjectAttribute() throws Exception {
		DomainObjectMapper dom2 = new DomainObjectMapper();
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		dom2.setDomainObjectAttribute("id", "Dogmatici");
		assertEquals("Dogmatici", ((Relation) dom2.getMyObject()).getId());
		
		// Exception.
		DomainObjectMapper dom3 = new DomainObjectMapper();
		boolean exception = false;
		try {
			dom3.setDomainObjectAttribute("id", "Dogmatici");
		} catch (Exception e) {
			exception = true;
		}
		assertEquals(true, exception);
		
		// String as setter.
		dom3 = new DomainObjectMapper();
		dom3.setObjectName("com.dexels.navajo.mapping.bean.TestPOJO");
		dom3.setDomainObjectAttribute("relation", "hallo");
		assertEquals("hallo", ((TestPOJO) dom3.getMyObject()).getRelation().getId());
		
		// Again...
		dom3.setObjectName("com.dexels.navajo.mapping.bean.TestPOJO");
		dom3.setDomainObjectAttribute("relation", "hallo");
		assertEquals("hallo", ((TestPOJO) dom3.getMyObject()).getRelation().getId());
		
		// Object as setter.
		TestPOJO tp = new TestPOJO();
		dom3 = new DomainObjectMapper(tp);
		Relation r = new Relation();
		r.setId("me");
		dom3.setDomainObjectAttribute("relation", r);
		assertEquals("me", ((TestPOJO) dom3.getMyObject()).getRelation().getId());
	}

	public void testGetDomainObjectAttribute() throws Exception {
		TestPOJO tp = new TestPOJO();
		tp.setName("Soepkippen");
		Relation r = new Relation();
		r.setId("Zeker");
		tp.setRelation(r);
		DomainObjectMapper dom = new DomainObjectMapper(tp);
		
		Object result = dom.getDomainObjectAttribute("name", null);
		assertNotNull(result);
		assertEquals("Soepkippen", result.toString());
		
		//  Again.
		tp = (TestPOJO) dom.getMyObject();
		tp.setName("Suk");
		assertNotNull(dom.getMethod("name", null));
		result = dom.getDomainObjectAttribute("name", null);
		assertNotNull(result);
		assertEquals("Suk", result.toString());
		
		result = dom.getDomainObjectAttribute("name", new Object[]{new String("Hallo")});
		assertNotNull(result);
		assertEquals("Hallo", result.toString());
		
		// Again to assert that method was properly cached.
		assertNotNull(dom.getMethod("name", new Class[]{String.class}));
		
		result = dom.getDomainObjectAttribute("name", new Object[]{new String("soep"), new String("kip")});
		assertNotNull(result);
		assertEquals("soepkip", result.toString());
	}

	public void testGetMyObject() throws Exception {
		TestPOJO pojo = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper(pojo);
	    assertEquals(pojo, dom.getMyObject());
	    
	    DomainObjectMapper dom2 = new DomainObjectMapper();
	    assertNull(dom2.getMyObject());
	    
	}

	public void testSetExcludedProperties() throws Exception {
		//TestPOJO tp = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper();
		dom.setObjectName("com.dexels.navajo.mapping.bean.TestPOJO");
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "Test");
		System.err.println("m = " + m);
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "name", Property.STRING_PROPERTY, "nice", 0, "", "out");
		m.addProperty(p1);
		java.util.Date d = new java.util.Date();
		Property p2 = NavajoFactory.getInstance().createProperty(doc, "birthdate", Property.DATE_PROPERTY, null, 0, "", "out");
		p2.setAnyValue(d);
		m.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc, "something", Property.STRING_PROPERTY, "notnice", 0, "", "out");
		m.addProperty(p3);
		Access a = new Access();
		
		a.setInDoc(doc);
		a.setOutputDoc(NavajoFactory.getInstance().createNavajo());
		dom.load(a);
		dom.setExcludedProperties("something");
		dom.setCurrentMessageName("Test");
		dom.store();
		
		TestPOJO tp = (TestPOJO) dom.getMyObject();
		assertEquals("nice", tp.getName());
		assertEquals(d, tp.getBirthdate());
		assertNull(tp.getSomething());
	}

	public void testSetInputProperties() throws Exception {
		TestPOJO tp = new TestPOJO();
		tp.setName("hallo");
		DomainObjectMapper dom = new DomainObjectMapper(tp);
		
		Access a = new Access();
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		a.setOutputDoc(doc);
		Message m = NavajoFactory.getInstance().createMessage(doc, "Test");
		doc.addMessage(m);
		a.setCurrentOutMessage(m);
		dom.load(a);
		dom.setInputProperties("name;birthdate");
		dom.store();
		
		assertNotNull(doc.getProperty("/Test/Name"));
		assertEquals("in", doc.getProperty("/Test/Name").getDirection());
		assertEquals("hallo", doc.getProperty("/Test/Name").getValue());
		
		assertNotNull(doc.getProperty("/Test/Something"));
		assertEquals("out", doc.getProperty("/Test/Something").getDirection());
		
	}

}
