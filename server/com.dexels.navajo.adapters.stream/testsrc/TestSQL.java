import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;

public class TestSQL {

	@Test
	public void testSQL() {
		SQL.query("dummy", "tenant", "select * from ORGANIZATION")
			.doOnNext(e->System.err.println("||:: "+e))
			.map(rs->Optional.ofNullable(rs.columnValue("NAME")))
			.filter(e->e.isPresent())
			.blockingForEach(e->{
				System.err.println(":: "+e.get());
			});
	}
	
	
	public static DataSource resolveDataSource(String dataSourceName, String tenant) {
		MySqlDataSourceComponent dsc = new MySqlDataSourceComponent();
        Map<String,Object> props = new HashMap<>();
        props.put("type", "mysql");
        props.put("name", "authentication");
        props.put("url", "jdbc:mysql://10.0.0.1/competition");
        props.put("user", "authentication");
        props.put("password", "authentication");
//	        dsc.activate(props);
        Properties p = new Properties();
        p.putAll(props);
        try {
			return dsc.createDataSource(p);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return dsc;
	}
}
