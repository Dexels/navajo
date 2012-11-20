import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;


public class TestReplace {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestReplace.class);
	
	public static void main(String[] args) throws IOException {

		com.dexels.navajo.document.Navajo pp =NavajoFactory.getInstance().createNavajo(new FileInputStream("tmlexample.xml"));
		
		URL u = parkMultipart(pp,true);
		logger.info("UR:: "+u);
//		Thread.sleep(20000);
	}

	private static URL parkMultipart(com.dexels.navajo.document.Navajo pp,boolean doDeleteOnExit) throws IOException {
		Map<String,String> replacementMap = new HashMap<String, String>();
		File mailFile = File.createTempFile("index",".html");
		Message parts = pp.getMessage("Mail/Parts");
		Binary body = (Binary) parts.getAllMessages().get(0).getProperty("Content").getTypedValue();
//		if(!destinationFolder.exists()) {
//			destinationFolder.mkdir();
//		}
		for (int i = 1; i < parts.getAllMessages().size(); i++) {
			Message currentPart = parts.getAllMessages().get(i);
			String fileName = currentPart.getProperty("FileName").getValue();
			String nextName = "cid:attach-nr-"+(i-1);
			replacementMap.put(nextName, fileName);
			Binary attach = (Binary) currentPart.getProperty("Content").getTypedValue();
			File currentFile = new File(mailFile.getParentFile(),fileName);
			attach.write(new FileOutputStream(currentFile));
			if(doDeleteOnExit) {
				currentFile.deleteOnExit();
			}
		}
		String bodyText = new String(body.getData());
		String replaced = replaceAttributes("src",bodyText,replacementMap);
		
		logger.info("RESULT:\n"+replaced);
		
		
		PrintWriter fos = new PrintWriter( new FileWriter(mailFile));
		fos.print(replaced);
		fos.flush();
		fos.close();
		if(doDeleteOnExit) {
			mailFile.deleteOnExit();
		}
		return mailFile.toURI().toURL();
		
	}

	public static String replaceAttributes(String attributeName, String htmlString,Map<String,String> replacementMap) {
		  Pattern patt = Pattern.compile(attributeName+"=\"([^<]*)\"");
		  Matcher m = patt.matcher(htmlString);
		  StringBuffer sb = new StringBuffer(htmlString.length());
		  while (m.find()) {
		    String text = m.group(1);
		    // ... possibly process 'text' ...
		    logger.info("String: "+text);
		    m.appendReplacement(sb, Matcher.quoteReplacement(attributeName+"=\""+replacementMap.get(text)+"\""));
		  }
		  m.appendTail(sb);
		  return sb.toString();
		}

	

}
