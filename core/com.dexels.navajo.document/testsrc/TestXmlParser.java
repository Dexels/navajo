import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;


public class TestXmlParser {

	@Test
	public void testXml() {
		StringReader sr = new StringReader("<tml><message name=\"aap\"/></tml>");
		Navajo n = NavajoFactory.getInstance().createNavajo(sr);
		Assert.assertNotNull(n);
	}

	@Test
	public void testXmlWithComment() {
		StringReader sr = new StringReader("<tml> <!-- abc --> <message name=\"aap\"/></tml>");
		Navajo n = NavajoFactory.getInstance().createNavajo(sr);
		Assert.assertNotNull(n);
	}
	
	@Test
	public void testDataSourceFileWithComment() throws IOException {
		InputStream is = TestXmlParser.class.getResourceAsStream("datasources.xml");
		Navajo n = NavajoFactory.getInstance().createNavajo(is);
		Assert.assertNotNull(n);
		is.close();
		n.write(System.err);
	}
	
	@Test
	public void testDataSourceWithDefinitionMessage() throws IOException {
		InputStream is = TestXmlParser.class.getResourceAsStream("arraymessages.xml");
		Navajo n = NavajoFactory.getInstance().createNavajo(is);
		Assert.assertNotNull(n);
		is.close();
		n.write(System.err);
		Message arr = n.getMessage("SimpleMessage").getMessage("ArrayMessage").getDefinitionMessage();
		Assert.assertNotNull(arr);
		arr.write(System.err);
	}

}
