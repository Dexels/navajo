package com.dexels.navajo.test;

import java.util.Date;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;

public class TestBean {

	/**
	 * Objects.
	 */
	public String stringField;
	public Boolean booleanField;
	public Float floatField;
	public Double doubleField;
	public Date dateField;
	public Integer integerField;
	public Long longField;
	public ClockTime clocktimeField;
	public Money moneyField;
	public Binary binaryField;
	public Percentage percentageField;
	
	/**
	 * User defined
	 */
	public TestSubBean subBean;
	public TestSubBean [] subBeans;
	
	/**
	 * Primitive types.
	 */
	public int primitiveInt;
	public long primitiveLong;
	public float primitiveFloat;
	public double primitiveDouble;
	public boolean primitiveBoolean;
	
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	public Boolean getBooleanField() {
		return booleanField;
	}
	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}
	public Float getFloatField() {
		return floatField;
	}
	public void setFloatField(Float floatField) {
		this.floatField = floatField;
	}
	public Double getDoubleField() {
		return doubleField;
	}
	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}
	public Date getDateField() {
		return dateField;
	}
	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}
	public Integer getIntegerField() {
		return integerField;
	}
	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}
	public Long getLongField() {
		return longField;
	}
	public void setLongField(Long longField) {
		this.longField = longField;
	}
	public int getPrimitiveInt() {
		return primitiveInt;
	}
	public void setPrimitiveInt(int primitiveInt) {
		this.primitiveInt = primitiveInt;
	}
	public long getPrimitiveLong() {
		return primitiveLong;
	}
	public void setPrimitiveLong(long primitiveLong) {
		this.primitiveLong = primitiveLong;
	}
	public float getPrimitiveFloat() {
		return primitiveFloat;
	}
	public void setPrimitiveFloat(float primitiveFloat) {
		this.primitiveFloat = primitiveFloat;
	}
	public double getPrimitiveDouble() {
		return primitiveDouble;
	}
	public void setPrimitiveDouble(double primitiveDouble) {
		this.primitiveDouble = primitiveDouble;
	}
	public boolean getPrimitiveBoolean() {
		return primitiveBoolean;
	}
	public void setPrimitiveBoolean(boolean primitiveBoolean) {
		this.primitiveBoolean = primitiveBoolean;
	}
	public ClockTime getClocktimeField() {
		return clocktimeField;
	}
	public void setClocktimeField(ClockTime clocktimeField) {
		this.clocktimeField = clocktimeField;
	}
	public Money getMoneyField() {
		return moneyField;
	}
	public void setMoneyField(Money moneyField) {
		this.moneyField = moneyField;
	}
	public Binary getBinaryField() {
		return binaryField;
	}
	public void setBinaryField(Binary binaryField) {
		this.binaryField = binaryField;
	}
	public Percentage getPercentageField() {
		return percentageField;
	}
	public void setPercentageField(Percentage percentageField) {
		this.percentageField = percentageField;
	}
	public TestSubBean getSubBean() {
		return new TestSubBean();
	}
	public void setSubBean(TestSubBean subBean) {
		this.subBean = subBean;
	}
	public TestSubBean[] getSubBeans() {
		return new TestSubBean[]{new TestSubBean("aap"), new TestSubBean("noot"), new TestSubBean("mies")};
	}
	public void setSubBeans(TestSubBean[] subBeans) {
		this.subBeans = subBeans;
	}
	
}
