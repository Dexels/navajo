package com.dexels.navajo.test;

public class TestSubBean {

	public String beanName = "MyBean";

	public TestSubBean() {
		
	}
	
	public TestSubBean(String name) {
		this.beanName = name;
	}
	
	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
}
