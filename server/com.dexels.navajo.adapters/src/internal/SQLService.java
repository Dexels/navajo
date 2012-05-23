package internal;

import java.util.List;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.adapter.sqlmap.RecordMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.serviceplugin.JavaPlugin;

public class SQLService extends JavaPlugin {

	@Override
	public Navajo process(Navajo in) throws Exception {
		SQLMap sql = new SQLMap();
		try {
			sql.load(getAccess());
			// Datasource.
			Message rm = in.getAllMessages().get(0);
			String datasource = rm.getProperty("datasource").getValue();
			sql.setDatasource(datasource);
			String query = rm.getProperty("query").getValue();
			sql.setQuery(query);
			// Get parameters.
			List<Message> parameters = rm.getMessages("parameter");
			for (Message message : parameters) {
				Object paramValue = message.getProperty("value").getTypedValue();
				sql.setParameter(paramValue);
			}

			Navajo response = NavajoFactory.getInstance().createNavajo();
			Message res = NavajoFactory.getInstance().createMessage(response, "resultSet");
			res.setType(Message.MSG_TYPE_ARRAY);
			response.addMessage(res);
			// Perform.
			ResultSetMap [] resultSet = sql.getResultSet();
			for ( int i = 0; i < resultSet.length; i++ ) {
				RecordMap [] records = resultSet[i].getRecords();
				Message m = NavajoFactory.getInstance().createMessage(response, "resultSet");
				res.addElement(m);
				for ( int j = 0; j < records.length; j++ ) {
					String name = records[j].getRecordName();
					Object value = records[j].getRecordValue();
					Property prop = NavajoFactory.getInstance().createProperty(response, name, "1", "", Property.DIR_OUT);
					prop.setAnyValue(value);
					m.addProperty(prop);
				}
			}
			return response;
		} catch (Exception e) {
			sql.kill();
			throw e;
		} finally {		
			sql.store();
		}
	}

}
