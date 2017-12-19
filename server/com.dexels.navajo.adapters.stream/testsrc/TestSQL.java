import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.script.api.SystemException;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class TestSQL {

	
//	private final Map<String,BiFunction<Message, Message, Message>> reduceFunctions = new HashMap<>();
	
	@Before
	public void setup() {
	}
	@Test(timeout=15000) 
	public void testSQL() {
		Expression.compileExpressions = true;
		AtomicInteger count = new AtomicInteger();
		SQL.query("dummy", "tenant", "select * from ORGANIZATION WHERE ROWNUM <50")
			.map(msg->msg.without(Arrays.asList("SHORTNAME,UPDATEBY,REMARKS".split(","))))
			.flatMapSingle(e->getOrganizationAttributes(e))
			.map(e->rename("ORGANIZATIONID","ID").apply(e))
			.flatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, false))
			.compose(StreamDocument.inArray("Organization"))
			.lift(StreamDocument.serialize())
			.blockingForEach(e->{
				count.incrementAndGet();
			});
		System.err.println("Total: "+count.get());
	}

	public Single<ImmutableMessage> getOrganizationAttributes(ImmutableMessage msg) throws TMLExpressionException, SystemException {
		return SQL.query("dummy", "tenant", "select * from ORGANIZATIONATTRIBUTE WHERE ORGANIZATIONID = ?", msg.columnValue("ORGANIZATIONID"))
			.observeOn(Schedulers.io())
			.subscribeOn(Schedulers.io())
			.reduce(msg, set("[ATTRIBNAME]", "[ATTRIBVALUE]"));
	}

	public static ImmutableMessage empty() {
		return ImmutableFactory.create(Collections.emptyMap(), Collections.emptyMap());
	}
	
	
	public BiFunction<ImmutableMessage,ImmutableMessage,ImmutableMessage> set(String... params) throws TMLExpressionException, SystemException {
		return (reduc,item)->{
			String keyExpression = params[0];
			String valueExpression = params[1];
			String key = (String)evaluate(keyExpression,item).value;
			Operand evaluated = evaluate(valueExpression, item);
			Object value = evaluated.value;
			String valueType = evaluated.type;
			return reduc.with(key,value,valueType);
		};
	}
	
	

	private Operand evaluate(String valueExpression, ImmutableMessage m) throws SystemException {
		return Expression.evaluate(valueExpression, null, null, null,null,null,null,null,Optional.of(m),Optional.empty());
	}
	
	public Message getMessage(String prefix, Message core) {
		return core;
	}

	public Function<Message,Message> delete(String key) throws TMLExpressionException, SystemException {
		return in->{
			Property p = in.getProperty(key);
			if(p!=null) {
				in.removeProperty(p);
			}
			return in;
		};
	}

	public Function<ImmutableMessage,ImmutableMessage> rename(String key, String to) throws TMLExpressionException, SystemException {
		return in->{
			Object value = in.columnValue(key);
			String type = in.columnType(key);
			return in.without(key)
					.with(key, value, type);
		};
	}
	
	public static DataSource resolveDataSource(String dataSourceName, String tenant) {
		MySqlDataSourceComponent dsc = new MySqlDataSourceComponent();
        Map<String,Object> props = new HashMap<>();
        props.put("type", "mysql");
        props.put("name", "authentication");
        props.put("url", "jdbc:mysql://localhost/competition");
        props.put("user", "username");
        props.put("password", "password");
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
