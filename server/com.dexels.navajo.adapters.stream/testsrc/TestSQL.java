import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.junit.Test;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.github.davidmoten.rx.jdbc.Database;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Flowable;

public class TestSQL {

	@Test
	public void testBackpressure() {
		RxJavaInterop.toV2Flowable(Database
		.fromDataSource(resolveDataSource("dummy","something"))
		.select("select * from ORGANIZATION")
		.get(SQL::resultSet))
		.map(rs->rs.columnValue("NAME"))
		.zipWith(Flowable.interval(100, TimeUnit.MILLISECONDS), (rs,i)->{
			return rs;
		});
//		. (s->System.err.println("res: "+s));
		
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
