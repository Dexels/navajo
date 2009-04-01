package com.dexels.navajo.mapping.bean;

import junit.framework.TestCase;

public class ServiceMapperTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetServiceObject() throws Exception {
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		Object o = sm.getServiceObject();
		assertNotNull(o);
		assertEquals(TestService.class, o.getClass());
	}

	public void testSetInvoke() throws Exception {
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("mergeBeans");
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setInvoke(true);
	}

	public void testGetResult() throws Exception {
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getStupid");
		sm.setAddParameter(2);
		sm.setInvoke(true);
		Object o = sm.getResult();
		assertNotNull(o);
		assertEquals("Stupid-" + 2, o.toString());
	}

	public void testGetDomainObjectResult() throws Exception {
		
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("mergeBeans");
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setAddDomainObject(new DomainObjectMapper(new TestBean()));
		sm.setInvoke(true);
		DomainObjectMapper dom = sm.getDomainObjectResult();
		assertNotNull(dom);
		assertEquals(TestBean.class, dom.getMyObject().getClass());
		
		// Another method with the same name, but with different parameter signature.
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("mergeBeans");
		TestBean tb1 = new TestBean();tb1.setLastname("1");
		TestBean tb2 = new TestBean();tb2.setLastname("2");
		TestBean tb3 = new TestBean();tb3.setLastname("3");
		
		sm.setAddDomainObject(new DomainObjectMapper(tb1));
		sm.setAddDomainObject(new DomainObjectMapper(tb2));
		sm.setAddDomainObject(new DomainObjectMapper(tb3));
		sm.setInvoke(true);
		dom = sm.getDomainObjectResult();
		assertNotNull(dom);
		assertEquals(TestBean.class, dom.getMyObject().getClass());
		assertEquals("123", ((TestBean) dom.getMyObject()).getLastname());
		
		// Check with empty domain object.
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestPOJO");
		sm.setServiceMethod("getEmptyRelation");
		dom = sm.getDomainObjectResult();
		assertNotNull(dom);
		assertNotNull(dom.getMyObject());
		assertEquals(Object.class, dom.getMyObject().getClass());
	}

	public void testGetDomainObjectResults() throws Exception {
		
		ServiceMapper sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getTestBeansAsList");
		sm.setAddParameter(2);
		sm.setInvoke(true);
		DomainObjectMapper [] doms = sm.getDomainObjectResults();
		assertEquals(2, doms.length);
		assertEquals("Lastname "+0, ((TestBean) doms[0].getMyObject()).getLastname());
		assertEquals("Lastname "+1, ((TestBean) doms[1].getMyObject()).getLastname());
		
		// Get test beans.
		sm = new ServiceMapper();
		sm.setServiceClass("com.dexels.navajo.mapping.bean.TestService");
		sm.setServiceMethod("getTestBeans");
		sm.setAddParameter(2);
		sm.setInvoke(true);
		doms = sm.getDomainObjectResults();
		assertEquals(2, doms.length);
		assertEquals("Lastname "+0, ((TestBean) doms[0].getMyObject()).getLastname());
		assertEquals("Lastname "+1, ((TestBean) doms[1].getMyObject()).getLastname());
	}

}
