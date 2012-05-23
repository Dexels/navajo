package com.dexels.navajo.mapping.bean;

import java.util.Date;

public class TestPOJO {
	private String name;
	private Date birthdate;
	private String something;
	private Relation relation;
	@SuppressWarnings("unused")
	private Relation[] relations;

	public Relation getEmptyRelation() {
		return null;
	}

	public String getName(String thisone) {
		return thisone;
	}

	public String getName(String s1, String s2) {
		return s1 + s2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(Relation r, String extra) {
		this.name = r.getId() + extra;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Relation getRelation() {
		return this.relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public void setRelation(String id) {
		this.relation = new Relation();
		this.relation.setId(id);
	}

	public Relation[] getRelations() {
		Relation[] r = new Relation[2];
		r[0] = new Relation();
		r[0].setId("aap");
		r[1] = new Relation();
		r[1].setId("noot");
		return r;
	}

	public void setRelations(Relation[] relations) {
		this.relations = relations;
	}

	public String getSomething() {
		return something;
	}

	public void setSomething(String something) {
		this.something = something;
	}

}
