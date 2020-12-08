/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.report;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class TipiReport {
	public static Binary getArrayMessageReport(ClientInterface client, Message m, String[] propertyNames,
			String[] propertyTitles, int[] columnWidths, String format, String orientation, int[] margins) throws NavajoException {
		// Message m = in.getMessage(messagePath);
		if (m == null) {
			throw NavajoFactory.getInstance().createNavajoException("Message not found. Can not run report.");
		}
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message cp = m.copy(n);
		Header h = NavajoFactory.getInstance().createHeader(n, "Irrelevant", "Irrelevant", "Irrelevant", -1);
		n.addHeader(h);
		h.setHeaderAttribute("sourceScript", "Irrelevant");
		n.addMessage(cp);
		Message repDef = NavajoFactory.getInstance().createMessage(n, "__ReportDefinition");
		n.addMessage(repDef);
		StringBuffer sz = new StringBuffer();
		for (int i = 0; i < columnWidths.length; i++) {
			if (i != 0) {
				sz.append(",");
			}
			sz.append(columnWidths[i]);
		}
		Property sizeProp = NavajoFactory.getInstance().createProperty(n, "PropertySizes", Property.STRING_PROPERTY,
				sz.toString(), 0, "", Property.DIR_IN);
		repDef.addProperty(sizeProp);

		sz = new StringBuffer();
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0) {
				sz.append(",");
			}
			sz.append(propertyNames[i]);
		}
		String propertyNamesString = sz.toString();
		Property namesProp = NavajoFactory.getInstance().createProperty(n, "PropertyNames", Property.STRING_PROPERTY,
				propertyNamesString, 0, "", Property.DIR_IN);
		repDef.addProperty(namesProp);

		sz = new StringBuffer();
		if (propertyTitles != null) {
			for (int i = 0; i < propertyTitles.length; i++) {
				if (i != 0) {
					sz.append(",");
				}
				sz.append(propertyTitles[i]);
			}
		} else {
			// If no titles supplied, use property names
			sz.append(propertyNamesString);
		}
		Property titlesProp = NavajoFactory.getInstance().createProperty(n, "PropertyTitles", Property.STRING_PROPERTY,
				sz.toString(), 0, "", Property.DIR_IN);
		repDef.addProperty(titlesProp);

		Property messagePathProp = NavajoFactory.getInstance().createProperty(n, "MessagePath",
				Property.STRING_PROPERTY, cp.getName(), 0, "", Property.DIR_IN);
		repDef.addProperty(messagePathProp);

		Property reportFormatProp = NavajoFactory.getInstance().createProperty(n, "OutputFormat",
				Property.STRING_PROPERTY, format, 0, "", Property.DIR_IN);
		repDef.addProperty(reportFormatProp);

		if (margins != null) {
			Property marginProperty = NavajoFactory.getInstance().createProperty(n, "Margin", Property.STRING_PROPERTY,
					margins[0] + "," + margins[1] + "," + margins[2] + "," + margins[3], 0, "", Property.DIR_IN);
			repDef.addProperty(marginProperty);
		}
		if (orientation != null) {
			Property orientationProperty = NavajoFactory.getInstance().createProperty(n, "Orientation",
					Property.STRING_PROPERTY, orientation, 0, "", Property.DIR_IN);
			repDef.addProperty(orientationProperty);
		}

		try {
			Navajo result = client.doSimpleSend(n, "ProcessPrintTableBirt");
			Property data = result.getProperty("/Result/Data");
			if (data == null) {
				result.write(System.err);
				throw NavajoFactory.getInstance().createNavajoException("No report property found.");
			}
			Binary b = (Binary) data.getTypedValue();
			return b;
		} catch (ClientException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
	}
}
