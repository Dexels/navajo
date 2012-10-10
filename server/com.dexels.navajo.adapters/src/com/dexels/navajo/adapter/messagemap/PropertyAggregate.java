package com.dexels.navajo.adapter.messagemap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Property;

public class PropertyAggregate {
	
	private Map<Map<String,Object>,Aggregate> aggregateGroup = new HashMap<Map<String,Object>,Aggregate>();
	
	class Aggregate {
		
		public double sum = 0.0;
		public double min = Double.MAX_VALUE;
		public double max = Double.MIN_VALUE;
		public Date minDate = null;
		public Date maxDate = null;
		public int count = 0;
		public String type;
		
		public double getAvg() {
			return sum / count;
		}
		
		public int getCount() {
			return count;
		}
		
		public double getSum() {
			return sum;
		}
		
		public String getType() {
			return type;
		}
		
		public Object getMin() {
			if ( minDate != null ) {
				return minDate;
			} else if ( min != Double.MAX_VALUE ) {
				return new Double(min);
			} else {
				return null;
			}
		}
		
		public Object getMax() {
			if ( maxDate != null ) {
				return maxDate;
			} else if ( max != Double.MIN_VALUE ) {
				return new Double(max);
			} else {
				return null;
			}
		}
		
		public void addProperty(Property myProp) {
			count++;
			if ( myProp.getTypedValue() != null && ( myProp.getType().equals(Property.INTEGER_PROPERTY) || myProp.getType().equals(Property.FLOAT_PROPERTY) ) ) {
				Double value = new Double(myProp.getTypedValue()+"");
				if ( value < min ) {
					min = value;
				}
				if ( value > max ) {
					max = value;
				}
				sum += value;
			}
			if ( myProp.getTypedValue() != null && myProp.getType().equals(Property.DATE_PROPERTY) ) {
				Date date = (Date) myProp.getTypedValue();
				if ( minDate == null && maxDate == null) {
					minDate = maxDate = date;
				} else if ( date.before(minDate) ) {
					minDate = date;
				} else if ( date.after(maxDate) ) {
					maxDate = date;
				}
			}
		}
		
		public Aggregate(String type) {
			this.type = type;
		}
		
	}
		
	public PropertyAggregate() {	
	}
	
	public void addProperty(Property p, Map<String,Object> group) {
		
		Aggregate a = aggregateGroup.get(group);
		if ( a == null ) {
			a = new Aggregate(p.getType());
			aggregateGroup.put(group, a);
		}
		a.addProperty(p);
	}
		
	public Aggregate getAggregate(Map<String,Object> group) {
		return aggregateGroup.get(group);
	}
	
}