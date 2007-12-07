package com.dexels.navajo.adapter.ibatis;

import java.sql.SQLException;
import java.util.List;

public class Person extends SqlMap {

	// Fields
	private String id;
	private String firstName;
	private String lastName;
	private String infix;
	
	// Methods
	private Person person;
	private Person [] manyPersons;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getInfix() {
		return infix;
	}
	
	public void setInfix(String infix) {
		this.infix = infix;
	}

	public Person getPerson() {
		Person p;
		try {
			p = (Person) sqlMapper.queryForObject("selectPersonById", this.id );
			return p;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Person [] getManyPersons() {
		// selectAllPersons
		try {
			List all = sqlMapper.queryForList("selectAllPersons");
			Person [] pa = new Person[all.size()];
			pa = (Person []) all.toArray(pa);
			return pa;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String [] args) {
		System.err.println("Starting...");
		
		Person dummy = new Person();
		dummy.setId("CHGP12Y");
		Person p = dummy.getPerson();
		System.err.println(">> " + p.getFirstName() + " " + p.getLastName());
		
		Person [] ps = dummy.getManyPersons();
		for (int i = 0; i < ps.length; i++) {
			System.err.println(ps[i].getFirstName() + " " + ps[i].getLastName());
		}
		
	}

}