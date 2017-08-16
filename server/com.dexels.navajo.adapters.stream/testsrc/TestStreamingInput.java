import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.stream.ByteSource;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.XML;
import com.github.davidmoten.rx.jdbc.Database;

import rx.Observer;

public class TestStreamingInput  {
	private final static Logger logger = LoggerFactory.getLogger(TestStreamingInput.class);

	@Test
	public void simpleTest() throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream("simpleresponse.xml");

		Database
			.fromDataSource(SQL.resolveDataSource("dummy",""))
			.select("SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'")
			.get(SQL::resultSet)
			.map(SQL::defaultSqlResultToMsg)
			.doOnNext(m->System.err.println("Message: "+m))
			.flatMap(m->m.stream())
			.compose(NavajoStreamOperators.inArray("Organizations"))
			.compose(NavajoStreamOperators.inNavajo("dummu", "username", "password"))
			.lift(NAVADOC.serialize())
			.doOnCompleted(()->System.err.println("Done query method"))
			.subscribe(new Observer<byte[]>() {

				@Override
				public void onCompleted() {
					try {
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Throwable arg0) {
					logger.error("Error: ", out);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						System.err.println("Item: "+new String(b));
						out.write(b);
					} catch (IOException e) {
						e.printStackTrace();
					}				
				}
			});
		
	}
	
	@Test
	public void testSingleQuery() throws FileNotFoundException {
		FileOutputStream out = new FileOutputStream("singleresponse.xml");

		
		SQL.queryToMessage("","dummy", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = 'BBKV29N'")
			.doOnNext(m->System.err.println("Message: "+m))
			.doOnCompleted(()->System.err.println("Done!"))
			.flatMap(m->m.stream())
			.compose(NavajoStreamOperators.inArray("Organizations"))
			.compose(NavajoStreamOperators.inNavajo("dummu", "username", "password"))
			.lift(NAVADOC.serialize())
			.subscribe(new Observer<byte[]>() {

				@Override
				public void onCompleted() {
					try {
						System.err.println("Done");
						out.flush();
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Throwable arg0) {
					logger.error("Error: ", out);
				}

				@Override
				public void onNext(byte[] b) {
					try {
						System.err.println("Item: "+new String(b));
						out.write(b);
					} catch (IOException e) {
						e.printStackTrace();
					}				
				}
			});
	}
	@Test
	public void testSQLFromFile() throws IOException {
		FileOutputStream out = new FileOutputStream("response.xml");
		
		ByteSource.fromAbsoluteClassPath("org2.xml")
		.lift(XML.parse())
		.lift(NAVADOC.parse(Collections.emptyMap()))
		.filter(e->e.type()==NavajoEventTypes.ARRAY_ELEMENT)
		.map(e->e.message())
		.map(m->m.value("ORGANIZATIONID"))
		.cast(String.class)
		.flatMap(clubId->SQL.queryToMessage("","dummy", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID = ?",clubId))
		.doOnNext(msg->System.err.println("Msg§: "+msg))
		.doOnCompleted(()->System.err.println("Complete"))
		.flatMap(m->m.stream())
		.doOnNext(event->System.err.println("Event: "+event))
		.compose(NavajoStreamOperators.inArray("Organizations"))
		.compose(NavajoStreamOperators.inNavajo("dummu", "username", "password"))
		.lift(NAVADOC.serialize())
		.subscribe(new Observer<byte[]>() {

			@Override
			public void onCompleted() {
				try {
					System.err.println("Done");
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(Throwable arg0) {
				logger.error("Error: ", out);
			}

			@Override
			public void onNext(byte[] b) {
				try {
					System.err.println("Item: "+new String(b));
					out.write(b);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		});
		
	}


}
