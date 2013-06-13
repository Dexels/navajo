package com.dexels.navajo.mapping.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.script.api.Access;

public class DomainObjectMapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateArray() throws Exception {
		Relation[] relations = new Relation[5];
		for (int i = 0; i < relations.length; i++) {
			relations[i] = new Relation();
			relations[i].setId(i + "");
		}
		DomainObjectMapper[] doms = DomainObjectMapper.createArray(relations);

		assertEquals(5, doms.length);
		assertEquals(((Relation) doms[1].getMyObject()).getId(), "1");

		DomainObjectMapper[] doms2 = DomainObjectMapper.createArray(null);
		assertNull(doms2);
	}

	@Test
	public void testSetMethodReference() throws Exception {
		TestPOJO tp = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper(tp);
		Method m = dom.setMethodReference(TestPOJO.class, "name",
				new Class[] { String.class });
		assertNotNull(m);
		m.invoke(tp, new Object[] { "Nak" });
		assertEquals("Nak", tp.getName());

		// Multiple parameters.
		Relation r = new Relation();
		r.setId("basis");
		m = dom.setMethodReference(TestPOJO.class, "name", new Class[] {
				Relation.class, String.class });
		assertNotNull(m);
		m.invoke(tp, new Object[] { r, new String("school") });
		assertEquals("basisschool", tp.getName());
	}

	@Test
	public void testSetObjectName() throws Exception {
		DomainObjectMapper dom = new DomainObjectMapper();
		dom.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		assertNotNull(dom.getMyObject());
		assertEquals(com.dexels.navajo.mapping.bean.Relation.class, dom
				.getMyObject().getClass());
	}

	@Test
	public void testSetAttributeName() throws Exception {
		DomainObjectMapper dom = new DomainObjectMapper(new Relation());
		dom.setAttributeName("id");
		assertEquals("id", dom.getAttributeName());
	}

	@Test
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

	@Test
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
		assertEquals("hallo", ((TestPOJO) dom3.getMyObject()).getRelation()
				.getId());

		// Again...
		dom3.setObjectName("com.dexels.navajo.mapping.bean.TestPOJO");
		dom3.setDomainObjectAttribute("relation", "hallo");
		assertEquals("hallo", ((TestPOJO) dom3.getMyObject()).getRelation()
				.getId());

		// Object as setter.
		TestPOJO tp = new TestPOJO();
		dom3 = new DomainObjectMapper(tp);
		Relation r = new Relation();
		r.setId("me");
		dom3.setDomainObjectAttribute("relation", r);
		assertEquals("me", ((TestPOJO) dom3.getMyObject()).getRelation()
				.getId());
	}

	@Test
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

		// Again.
		tp = (TestPOJO) dom.getMyObject();
		tp.setName("Suk");
		assertNotNull(dom.getMethod("name", null));
		result = dom.getDomainObjectAttribute("name", null);
		assertNotNull(result);
		assertEquals("Suk", result.toString());

		result = dom.getDomainObjectAttribute("name",
				new Object[] { new String("Hallo") });
		assertNotNull(result);
		assertEquals("Hallo", result.toString());

		// Again to assert that method was properly cached.
		assertNotNull(dom.getMethod("name", new Class[] { String.class }));

		result = dom.getDomainObjectAttribute("name", new Object[] {
				new String("soep"), new String("kip") });
		assertNotNull(result);
		assertEquals("soepkip", result.toString());

		// With null domain object.
		DomainObjectMapper dom2 = new DomainObjectMapper(null);
		assertNull(dom2.getDomainObjectAttribute("something", null));
	}

	@Test
	public void testGetMyObject() throws Exception {
		TestPOJO pojo = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper(pojo);
		assertEquals(pojo, dom.getMyObject());

		DomainObjectMapper dom2 = new DomainObjectMapper();
		assertNull(dom2.getMyObject());

	}

	@Test
	public void testStore() throws Exception {
		// With null domain object and null Access object.
		DomainObjectMapper dom2 = new DomainObjectMapper(null);
		dom2.store();

		// With null domain object and an Access object.
		dom2 = new DomainObjectMapper(null);
		dom2.load(new Access());
		boolean exception = false;
		try {
			dom2.store();
		} catch (Exception e) {
			exception = true;
		}
		assertTrue(exception);

		// With some domain object and an Access object
		dom2 = new DomainObjectMapper(new Relation());
		dom2.load(new Access());
		exception = false;
		try {
			dom2.store();
		} catch (Exception e) {
			exception = true;
		}
		assertTrue(exception);

		// With some domain object and an Access object and a valid Navajo.
		dom2 = new DomainObjectMapper(new Relation());
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Access a = new Access();
		a.setOutputDoc(doc);
		a.setCurrentOutMessage(m);
		dom2.load(a);
		dom2.store();
		assertNotNull(doc.getProperty("/MyMessage/Id"));

		// The other way around, with an input Navajo.
		doc = NavajoFactory.getInstance().createNavajo();
		m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Property p = NavajoFactory.getInstance().createProperty(doc, "Id",
				"string", "hello", 0, "", "in");
		m.addProperty(p);
		a = new Access();
		a.setInDoc(doc);

		dom2 = new DomainObjectMapper();
		dom2.setCurrentMessageName("MyMessage");
		dom2.load(a);
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		dom2.store();
		Object o = dom2.getMyObject();
		assertNotNull(o);
		assertEquals(Relation.class, o.getClass());
		assertEquals("hello", ((Relation) o).getId());

		// The other way around, with an input Navajo with property that does
		// have an associated attribute.
		doc = NavajoFactory.getInstance().createNavajo();
		m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "Id",
				"string", "hello", 0, "", "in");
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"NonExisting", "string", "hello", 0, "", "in");
		m.addProperty(p1);
		m.addProperty(p2);
		a = new Access();
		a.setInDoc(doc);

		dom2 = new DomainObjectMapper();
		dom2.setCurrentMessageName("MyMessage");
		dom2.load(a);
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		exception = false;
		try {
			dom2.store();
		} catch (Exception e) {
			exception = true;
		}
		// Expect exception because Navajo contains property NonExisting that
		// does not exist in domain object.
		assertTrue(exception);

		// o = dom2.getMyObject();
		// assertNotNull(o);
		// assertEquals(Relation.class, o.getClass());
		// assertEquals("hello", ((Relation) o).getId());

	}

	@Test
	public void testStoreWithNonExistingAttributesCheck() throws Exception {

		// The other way around, with an input Navajo with property that does
		// have an associated attribute.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "Id",
				"string", "hello", 0, "", "in");
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"NonExisting", "string", "hello", 0, "", "in");
		m.addProperty(p1);
		m.addProperty(p2);
		Access a = new Access();
		a.setInDoc(doc);

		DomainObjectMapper dom2 = new DomainObjectMapper();
		dom2.setCurrentMessageName("MyMessage");
		dom2.load(a);
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");
		dom2.setIgnoreNonExistingAttributes(true);
		boolean exception = false;
		try {
			dom2.store();
		} catch (Exception e) {
			exception = true;
		}
		// DO NOT Expect exception because Navajo contains property NonExisting
		// that does not exist in domain object and
		// setIgnoreNonExistingAttributes is set.
		assertFalse(exception);

		Object o = dom2.getMyObject();
		assertNotNull(o);
		assertEquals(Relation.class, o.getClass());
		assertEquals("hello", ((Relation) o).getId());
	}

	@Test
	public void testStoreWithSelectionProperty() throws Exception {

		// The other way around, with an input Navajo with property that does
		// have an associated attribute.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "Id",
				"string", "hello", 0, "", "in");
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"Selection", "1", "", "in");
		m.addProperty(p1);
		m.addProperty(p2);
		// Add selections...
		Selection s1 = NavajoFactory.getInstance().createSelection(doc, "aap",
				"AAP", false);
		Selection s2 = NavajoFactory.getInstance().createSelection(doc, "noot",
				"NOOT", true);
		p2.addSelection(s1);
		p2.addSelection(s2);

		Access a = new Access();
		a.setInDoc(doc);

		DomainObjectMapper dom2 = new DomainObjectMapper();
		dom2.setCurrentMessageName("MyMessage");
		dom2.load(a);
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");

		dom2.store();

		Object o = dom2.getMyObject();
		assertNotNull(o);
		assertEquals(Relation.class, o.getClass());
		assertEquals("hello", ((Relation) o).getId());
		assertEquals("NOOT", ((Relation) o).getSelection());
	}

	@Test
	public void testStoreWithMultipleSelectionProperty() throws Exception {

		// The other way around, with an input Navajo with property that does
		// have an associated attribute.
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "MyMessage");
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "Id",
				"string", "hello", 0, "", "in");
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"Selection", "+", "", "in");
		m.addProperty(p1);
		m.addProperty(p2);
		// Add selections...
		Selection s1 = NavajoFactory.getInstance().createSelection(doc, "aap",
				"AAP", true);
		Selection s2 = NavajoFactory.getInstance().createSelection(doc, "noot",
				"NOOT", true);
		Selection s3 = NavajoFactory.getInstance().createSelection(doc, "mies",
				"MIES", false);
		p2.addSelection(s1);
		p2.addSelection(s2);
		p2.addSelection(s3);

		Access a = new Access();
		a.setInDoc(doc);

		DomainObjectMapper dom2 = new DomainObjectMapper();
		dom2.setCurrentMessageName("MyMessage");
		dom2.load(a);
		dom2.setObjectName("com.dexels.navajo.mapping.bean.Relation");

		boolean exception = false;

		try {
			dom2.store();
		} catch (Exception e) {
			exception = true;
		}

		// Multiple cardinality is not yet supported, hence exception.
		assertTrue(exception);

		// Object o = dom2.getMyObject();
		// assertNotNull(o);
		// assertEquals(Relation.class, o.getClass());
		// assertEquals("hello", ((Relation) o).getId());
		// assertEquals("NOOT", ((Relation) o).getSelection());
	}

	@Test
	public void testSetExcludedProperties() throws Exception {
		// TestPOJO tp = new TestPOJO();
		DomainObjectMapper dom = new DomainObjectMapper();
		dom.setObjectName("com.dexels.navajo.mapping.bean.TestPOJO");
		Navajo doc = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(doc, "Test");
		System.err.println("m = " + m);
		doc.addMessage(m);
		Property p1 = NavajoFactory.getInstance().createProperty(doc, "name",
				Property.STRING_PROPERTY, "nice", 0, "", "out");
		m.addProperty(p1);
		java.util.Date d = new java.util.Date();
		Property p2 = NavajoFactory.getInstance().createProperty(doc,
				"birthdate", Property.DATE_PROPERTY, null, 0, "", "out");
		p2.setAnyValue(d);
		m.addProperty(p2);
		Property p3 = NavajoFactory.getInstance().createProperty(doc,
				"something", Property.STRING_PROPERTY, "notnice", 0, "", "out");
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

	@Test
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
