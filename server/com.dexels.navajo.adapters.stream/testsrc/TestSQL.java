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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.resource.jdbc.mysql.MySqlDataSourceComponent;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class TestSQL {

	
	private final static Logger logger = LoggerFactory.getLogger(TestSQL.class);

	@Test
	public void testSQL() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
		AtomicInteger count = new AtomicInteger();
		SQL.query("dummy", "tenant", "select * from ORGANIZATION")
			.map(msg->msg.without(Arrays.asList("SHORTNAME,UPDATEBY,REMARKS".split(","))))
//			.doOnNext(e->System.err.println(new String(ReplicationFactory.getInstance().serialize(e))))
			.map(msg->StreamDocument.replicationToMessage(msg, "Organization", true))
			.flatMapSingle(e->getOrganizationAttributes(e),false,5)
//			.map(e->set("'ORGANIZATIONID'","ToLower([ORGANIZATIONID])"))
//			.map(e->delete("LASTUPDATE").apply(e))
			.map(e->rename("ORGANIZATIONID","ID").apply(e))
			.map(msg->StreamDocument.messageToReplication(msg))
			.flatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, false))
			
			//			.concatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, true))
			
			.compose(StreamDocument.inArray("Organization"))
			
//			.map(msg->StreamDocument.s(msg))
//			.
			.lift(StreamDocument.serialize())
			
			.blockingForEach(e->{
				System.err.println(":: "+new String(e));
				count.incrementAndGet();
			});
		System.err.println("Total: "+count.get());
	}
	
//	private ReplicationMessage repl(ReplicationMessage m) {
//		Message mm = StreamDocument.replicationToMessage(m, "", true);
//		return (ReplicationMessage)(Expression.evaluate("Without('Remarks')", null, null, m).value));
//	}
//	

	public Single<Message> getOrganizationAttributes(Message msg) throws TMLExpressionException, SystemException {
		return SQL.query("dummy", "tenant", "select * from ORGANIZATIONATTRIBUTE WHERE ORGANIZATIONID = ?", msg.getProperty("ORGANIZATIONID").getValue())
			.doOnNext(e->System.err.println(new String(ReplicationFactory.getInstance().serialize(e))))
			.map(m->StreamDocument.replicationToMessage(m, "Organization", false))
			.reduce(msg, this.reduce());
			
	}

//	.reduce(msg, (e,r)->{
//		set("[item:ATTRIBNAME]", "")
//		String attribute = (String) r.columnValue("ATTRIBNAME");
//		Object value = r.columnValue("ATTRIBVALUE");
//		ReplicationMessage res = e.with(attribute, value, r.columnType("ATTRIBVALUE")); 
//		return res;
//	});
//
	
	public BiFunction<Message,Message,Message> reduce() throws TMLExpressionException, SystemException {
		return set("[item|ATTRIBNAME]", "[item|ATTRIBVALUE]");
	}
	public static ReplicationMessage empty() {
		return ReplicationFactory.createReplicationMessage(null, System.currentTimeMillis(), ReplicationMessage.Operation.NONE, Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap(),Collections.emptyMap(),Collections.emptyMap(),Optional.of(()->{}));
	}
	
	
	public BiFunction<Message,Message,Message> set(String keyExpression, String valueExpression) throws TMLExpressionException, SystemException {
		return (reduc,item)->{
			try {
				String key = (String)evaluate(keyExpression,e->item);
//				System.err.println("Key: "+key);
				Object value = evaluate(valueExpression, e->item);
//				System.err.println("Value: "+value);
//				reduc.setValue(key,value);
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
			return reduc;
		};
//		return in;
	}

	private Object evaluate(String valueExpression, Function<String, Message> m) throws SystemException {
//		System.err.println("Evaluating: "+valueExpression);
		return Expression.evaluate(valueExpression, null).value;
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

	public Function<Message,Message> rename(String key, String to) throws TMLExpressionException, SystemException {
		return in->{
			Property p = in.getProperty(key);
			if(p!=null) {
				in.removeProperty(p);
			}
			p.setName(to);
			in.addProperty(p);
//			in.getProperty(key).setAnyValue(Expression.evaluate(valueExpression, null, null, in).value);
			return in;
		};
	}
}
