/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dexels.navajo.document.types.ClockTime;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class MessageTable extends Table {

	private static final long serialVersionUID = 9005864145330315869L;
	private transient final SimpleDateFormat dateFormat3 = new SimpleDateFormat( com.dexels.navajo.document.Property.DATE_FORMAT3 );
	
	@Override
	  protected String formatPropertyValue(Object rowId, Object colId,
              Property property) {
          Object v = property.getValue();
//          logger.warn("Formatting: "+rowId+" col: "+colId+" prop: "+property+" value: "+v);
          if (v instanceof Date) {
              Date dateValue = (Date) v;
              return dateFormat3.format(dateValue);
          }
          if(v instanceof ClockTime) {
        	  ClockTime ct = (ClockTime)v;
        	  return ct.toShortString();
          }
          if(v instanceof Number) {
        	  return ""+v.toString();
          }
          if(v instanceof Boolean) {
        	  boolean b = (Boolean)v;
        	  return b?"ja":"nee";
          }
          return super.formatPropertyValue(rowId, colId, property);
      }

	
}
