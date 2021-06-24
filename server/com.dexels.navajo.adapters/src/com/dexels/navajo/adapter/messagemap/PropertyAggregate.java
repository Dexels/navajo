/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.messagemap;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class PropertyAggregate {
	
	private Map<Map<String,Object>,Aggregate> aggregateGroup = new HashMap<Map<String,Object>,Aggregate>();
	
	class Aggregate {
		
		public double sum = 0.0;
		public double min = Double.MAX_VALUE;
		public double max = Double.MIN_VALUE;
		public Date minDate = null;
		public Date maxDate = null;
		public int count = 0;
		public Object any = null;
		public String concatenated = null;
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
		
        public String getConcatenated() {
            return concatenated;
        }

        public Object getAny() {
            return any;
        }

		public Object getMin() {
			if ( minDate != null ) {
				return minDate;
			} else if ( min != Double.MAX_VALUE ) {
				return Double.valueOf(min);
			} else {
				return null;
			}
		}
		
		public Object getMax() {
			if ( maxDate != null ) {
				return maxDate;
			} else if ( max != Double.MIN_VALUE ) {
				return Double.valueOf(max);
			} else {
				return null;
			}
		}
		
		public void addProperty(Property myProp) {
		    if( count == 0 )
		    {
		        // ensure we take only one value. Hence take the "first". But we don't order so no guarantee which we take, hence any
		        any = myProp.getTypedValue();
		    }
			count++;
			if ( myProp.getTypedValue() != null && ( myProp.getType().equals(Property.INTEGER_PROPERTY) || myProp.getType().equals(Property.FLOAT_PROPERTY) || myProp.getType().equals(Property.MONEY_PROPERTY) ) ) {
				Double value = Double.valueOf(myProp.getTypedValue()+"");
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
				} else {
					if (minDate!=null) {
						if ( date.before(minDate) ) {
							minDate = date;
						}						
					}
					if(maxDate!=null) {
						if ( date.after(maxDate) ) {
							maxDate = date;
						}
					}
				}
			}
			StringBuilder sb = new StringBuilder();
            if( concatenated != null )
            {
                sb.append( concatenated ).append( ";" );
            }
			if( myProp.getType().equals( Property.SELECTION_PROPERTY ) )
			{
			    for( Selection s : myProp.getAllSelectedSelections() )
			    {
			        sb.append( s.getValue() ).append( ";" );
			    }
			    // Remove the trailing ';'
		        sb.delete( sb.length() - 1, sb.length() );
			}
			else
			{
			    sb.append( myProp.getValue() );
			}
			concatenated = sb.toString();
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