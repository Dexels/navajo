package com.dexels.navajo.adapter;

import java.util.ArrayList;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class GenericFieldMap implements Mappable {
	public String query = "";
	public String update = "";
	public String propertyPrefix = "GF_";
	public String primaryKey = "fieldid";
	public String valueColumn = "fieldvalue";
	public Object singleValue;
	public String table;
	public String codeTable;
	public Object parameter;
	public String parameterName;
	public Object parameterValue;
	public String datasource;
	public int transactionContext;
	public boolean doQuery = false;
	public boolean doUpdate = false;
	private SQLMap sql;
	private Message currentOutputMessage = null;
	public Message inputMessage = null;
	public String currentInputMessage;
	private ArrayList<Object[]> updateParameters = new ArrayList<Object[]>();
	private Navajo inDoc;

	public void kill() {
		sql.kill();
	}

	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		currentOutputMessage = access.getCurrentOutMessage();
		inDoc = access.getInDoc();
		sql = new SQLMap();
		sql.load(access);
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		sql.store();
	}

	// The result must hold four columns: id, name, value, type
	public void setQuery(String query) throws UserException {
		this.query = query;
		sql.setQuery(query);
		System.err.println("Query: " + query);
	}

	public void setUpdate(String update) throws UserException {
		this.update = update;
		sql.setUpdate(update);
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}

	public void setParameter(Object parameter) throws UserException {
		this.parameter = parameter;
		sql.setParameter(parameter);
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
		sql.setDatasource(datasource);
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setCodeTable(String codeTable) {
		this.codeTable = codeTable;
	}

	public void setTransactionContext(int context) throws UserException {
		sql.setTransactionContext(context);
	}

	public void setCurrentInputMessage(String path) {
		this.currentInputMessage = path;
		inputMessage = inDoc.getMessage(path);
	}

	public void setDoQuery(boolean b) throws UserException {
		if (b) {
			ResultSetMap[] result = sql.getResultSet();
			if (result.length > 0) {
				for (int i = 0; i < result.length; i++) {
					Object id = result[i].getColumnValue("id");
					Object name = result[i].getColumnValue("name");
					Object value = result[i].getColumnValue("value");
					Object type = result[i].getColumnValue("type");
					appendProperty(id.toString(), name.toString(), value, type.toString());
				}
			}
		}
	}

	public Object getSingleValue() throws UserException {
		if (query != null) {
			ResultSetMap[] result = sql.getResultSet();
			if (result.length > 0) {
				return result[0].getColumnValue(0);
			}
		}
		return null;
	}

	public void setParameterName(String paramName) {
		this.parameterName = paramName;
	}

	public void setParameterValue(Object paramValue) {
		this.parameterValue = paramValue;
		updateParameters.add(new Object[] { parameterName, parameterValue });
	}

	public void setDoUpdate(boolean b) throws UserException {
		if (currentInputMessage != null && table != null) {

			String checkQuery = "SELECT COUNT(" + primaryKey + ") FROM " + table + " WHERE " + primaryKey + " = ?";
			for (int i = 0; i < updateParameters.size(); i++) {
				checkQuery = checkQuery + " AND " + updateParameters.get(i)[0].toString() + " = ?";
			}

			ArrayList<Property> allProps = inputMessage.getAllProperties();
			for (int i = 0; i < allProps.size(); i++) {
				if (allProps.get(i).getName().startsWith(propertyPrefix)) {
					String operation = "update";
					String propName = allProps.get(i).getName();
					String strVal = propName.substring(propertyPrefix.length());

					sql.setQuery(checkQuery);

					sql.setParameter(strVal);
					for (int j = 0; j < updateParameters.size(); j++) {
						sql.setParameter(updateParameters.get(j)[1]);
					}

					Object result = getSingleValue();
					if (result != null && result instanceof Integer) {
						if (((Integer) result).intValue() == 0) {
							operation = "insert";
						}
					}

					if ("update".equals(operation)) {
						String updateQuery = "UPDATE " + table + " SET " + valueColumn + " = ? WHERE " + primaryKey + " = ?";
						for (int k = 0; k < updateParameters.size(); k++) {
							updateQuery = updateQuery + " AND " + updateParameters.get(k)[0].toString() + " = ?";
						}
						sql.setUpdate(updateQuery);
						if (allProps.get(i).getType().equals("clocktime")) {
							sql.setParameter(allProps.get(i).getTypedValue().toString());
						} else if (allProps.get(i).getType().equals("selection")) {
							try {
								sql.setParameter(allProps.get(i).getSelected().getValue());
							} catch (NavajoException e) {
								throw new UserException(1200, e.getMessage());
							}
						} else {
							sql.setParameter(allProps.get(i).getTypedValue());
						}
						sql.setParameter(strVal);
						for (int j = 0; j < updateParameters.size(); j++) {
							sql.setParameter(updateParameters.get(j)[1]);
						}
						sql.setDoUpdate(true);

					} else {
						String insertQuery = "INSERT INTO " + table + " ( " + primaryKey + ", " + valueColumn;
						for (int k = 0; k < updateParameters.size(); k++) {
							insertQuery = insertQuery + ", " + updateParameters.get(k)[0].toString();
						}
						insertQuery = insertQuery + ") VALUES (?, ?";
						for (int k = 0; k < updateParameters.size(); k++) {
							insertQuery = insertQuery + ", ?";
						}
						insertQuery = insertQuery + ")";
						sql.setUpdate(insertQuery);
						sql.setParameter(strVal);

						// clocktime bypass
						if (allProps.get(i).getType().equals("clocktime")) {
							sql.setParameter(allProps.get(i).getTypedValue().toString());
						} else if (allProps.get(i).getType().equals("selection")) {
							try {
								sql.setParameter(allProps.get(i).getSelected().getValue());
							} catch (NavajoException e) {
								throw new UserException(1200, e.getMessage());
							}
						} else {
							sql.setParameter(allProps.get(i).getTypedValue());
						}
						for (int j = 0; j < updateParameters.size(); j++) {
							sql.setParameter(updateParameters.get(j)[1]);
						}
						sql.setDoUpdate(true);
					}
				}
			}
		}
	}

	private void appendProperty(String id, String name, Object value, String storedType) throws UserException {
		try {
			String type = storedType.toLowerCase();
			String sVal = "";
			if (value != null) {
				sVal = value.toString();
			}

			// Correct to true/false if sVal 1/0
			if ("boolean".equalsIgnoreCase(type) && ("1".equals(sVal) || "0".equals(sVal))) {
				sVal = ("1".equals(sVal) ? "true" : "false");
			}

			// if selection, the value is the selected option, get the rest opf
			// the options too.
			if ("selection".equalsIgnoreCase(type)) {
				Property p = constructSelectionProperty(id, name, sVal);
				currentOutputMessage.addProperty(p);
			} else {
				Property p = NavajoFactory.getInstance().createProperty(currentOutputMessage.getRootDoc(), "GF_" + id, type, sVal, 1024, name, Property.DIR_IN);
				currentOutputMessage.addProperty(p);
			}
		} catch (NavajoException e) {
			throw new UserException(1200, e.getMessage());
		}
	}

	private Property constructSelectionProperty(String id, String name, String selectedValue) throws UserException {
		try {
			Property p = NavajoFactory.getInstance().createProperty(currentOutputMessage.getRootDoc(), "GF_" + id, "1", name, Property.DIR_IN);

			String query = "SELECT code, description FROM " + codeTable + " WHERE fieldid = ?";
			sql.setQuery(query);
			sql.setParameter(id);
			ResultSetMap[] result = sql.getResultSet();
			for (int i = 0; i < result.length; i++) {
				ResultSetMap m = result[i];
				String selectionValue = (String) m.getColumnValue("code");
				String selectionName = (String) m.getColumnValue("description");
				boolean selected = selectedValue.equals(selectionValue);
				Selection sel = NavajoFactory.getInstance().createSelection(currentOutputMessage.getRootDoc(), selectionName, selectionValue, selected);
				p.addSelection(sel);
			}
			return p;
		} catch (Exception e) {
			throw new UserException(1200, e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			ClockTime c = new ClockTime("12:00");
			Property p = NavajoFactory.getInstance().createProperty(NavajoFactory.getInstance().createNavajo(), "test", "clocktime", "12:00", 12, "tijd", "out");

			System.err.println("Clocktime: " + c.toString() + ", through property: " + p.getTypedValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
