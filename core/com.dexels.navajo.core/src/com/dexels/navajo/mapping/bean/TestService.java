package com.dexels.navajo.mapping.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestService {

	DomainObjectMapper dom;
	TestBean myBean;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TestService.class);
	
	public String getLastname(TestBean dom) throws Exception {
		return dom.getLastname();
	}
	
	/**
	 * @param tb the first bean to merge
	 * @param tb2  the second bean to merge
	 */
	public TestBean mergeBeans(TestBean tb, TestBean tb2) {
		logger.info("In mergBeans");
		return tb;
	}
	
	public TestBean mergeBeans(TestBean tb, TestBean tb2, TestBean tb3) {
		logger.info("In mergBeans");
		String lastname = tb.getLastname() + tb2.getLastname() + tb3.getLastname();
		TestBean tb4 = new TestBean();
		tb4.setLastname(lastname);
		return tb4;
	}
	
	public TestBean getEmptyBean() {
		return null;
	}
	
	public TestBean getABean(String b) {
		TestBean tb = new TestBean();
		tb.setLastname(b);
		return tb;
	}
	
	public String getStupid(Integer i) {
		return "Stupid-"+i;
	}
	
	public TestBean [] getTestBeans(Integer count) {
		TestBean [] tb = new TestBean[count];
		for (int i = 0; i < count; i++) {
			tb[i] = new TestBean();
			tb[i].setLastname("Lastname " + i);
		}
		return tb;
	}
	
	public List<TestBean> getTestBeansAsList(Integer count) {
		List<TestBean> tb = new ArrayList<TestBean>();
		for (int i = 0; i < count; i++) {
			TestBean t = new TestBean();
			t.setLastname("Lastname " + i);
			tb.add(t);
		}
		return tb;
	}
	
	public void setTestBean(DomainObjectMapper d) throws Exception {
		this.dom = d;
		this.myBean = (TestBean) d.getMyObject();
		logger.info("TestBean: lastname: " + myBean.getLastname());
	}

	public DomainObjectMapper getTestBean() throws Exception {
		myBean = new TestBean();
		myBean.setFirstname("Harry");
		myBean.setLastname("Nak");
		logger.info("In getTestBean. LastName: " + myBean.getLastname());
		return new DomainObjectMapper(myBean);
	}
	
	public DomainObjectMapper [] getTestBeans() throws Exception {
		TestBean [] beans = new TestBean[2];
		beans[0] = new TestBean();
		beans[0].setLastname("Bergman");
		beans[1] = new TestBean();
		beans[1].setLastname("Posthumus");
		
		DomainObjectMapper [] domains = new DomainObjectMapper[2];
		domains[0] = new DomainObjectMapper(beans[0]);
		domains[1] = new DomainObjectMapper(beans[1]);
		
		return domains;
		
	}
	
	public void setTestBeans(DomainObjectMapper [] doms) throws Exception {
		logger.info("In setTestBeans(). Received " + doms.length + " objects");
		for (int i = 0; i < doms.length; i++) {
			TestBean tb = (TestBean) doms[i].getMyObject();
			logger.info("RECEIVED: " + tb.getFirstname() + " " + tb.getLastname());
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		TestBean tb = new TestBean();
		tb.setLastname("Lyaruu");
		
		DomainObjectMapper dom = new DomainObjectMapper(tb);
		
		Object value = dom.getDomainObjectAttribute("lastname", null);
		logger.info("value = " + value);
		
		dom.setDomainObjectAttribute("lastname", "Philip");
		value = dom.getDomainObjectAttribute("lastname", null);
		logger.info("new value = " + value);
		
		value = dom.getDomainObjectAttribute("appendedLastname", new Object[]{"hallo"});
		logger.info("appendedLastname value = " + value);
		
		java.lang.reflect.Method[] all = TestBean.class.getMethods();
		for (int i = 0; i < all.length; i++) {
				logger.info(all[i].getName() + ", params=" + all[i].getParameterTypes().length);
			
		}
	}
}
