package com.dexels.navajo.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

public class TestHttpClient {

	private static Random rand = new Random(System.currentTimeMillis());
	
	public static String printHeader(Map<String, List<String>> header) {
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = header.keySet().iterator();
		while ( keys.hasNext() ) {
			String key = keys.next();
			List<String> value = header.get(key);
			sb.append(key+"=");
			for ( int i = 0; i < value.size(); i++ ) {
				sb.append(value.get(i)+",");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static String call(String host) throws Exception {
		
		 Navajo d = NavajoFactory.getInstance().createNavajo();
		 Message m = NavajoFactory.getInstance().createMessage(d, "MatchForm");
		 Property p = NavajoFactory.getInstance().createProperty(d, "MatchId", "integer", rand.nextInt(1000000)+"", 1, "", "in");
		 m.addProperty(p);
		 d.addMessage(m);
		 // matchreport/test/ReadSingleMatchForm
		 Header h = NavajoFactory.getInstance().createHeader(d, "matchreport/test/InsertIntoMongo", "ROOT", "R20T", -1);
		 d.addHeader(h);
//		 Header h = NavajoFactory.getInstance().createHeader(d, "InitSimple", "ROOT", "R20T", -1);
//		 d.addHeader(h);
		 
		 //URL url = new URL("http://10.10.10.61/sportlink/knvb/Comet"); -> 500
		URL url = new URL(host); //-> 500
		 //URL url = new URL("http://hera1.dexels.com/sportlink/knvb/Comet"); -> 500
		// URL url = new URL("http://hera2:90/sportlink/knau/Comet"); -> OK
		 //URL url = new URL("http://hera2/sportlink/knkv/Comet"); -> OK
		 //URL url = new URL("http://hera3:81/sportlink/knvb/Comet"); //-> 60ms voor InitBM!!!
		 //URL url = new URL("http://10.10.10.44:81/sportlink/knvb/PostmanLegacy");
		//URL url = new URL("http://localhost:8080/sportlink/Postman");
		 
		    HttpURLConnection con = null;
		      con = (HttpURLConnection) url.openConnection();
		      con.setRequestMethod("POST");
		      con.setConnectTimeout(5000);
		    
		    // Omit fancy http stuff for now
		    con.setDoOutput(true);

		    con.setDoInput(true);
		    con.setUseCaches(false);
		    con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
		    
		    //con.setRequestProperty("Connection", "Keep-Alive");

		    con.setRequestProperty("Host", "localhost");
		    con.setRequestProperty("Content-Encoding", "jzlib");
        	con.setRequestProperty("Accept-Encoding", "jzlib");
        	
		    java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[]{int.class});
		    chunked.invoke( con, new Object[]{new Integer(1024)});
		    con.setRequestProperty("Transfer-Encoding", "chunked" );
		    
		    //con.connect();
		    con.setRequestProperty("noCompression", "true");
		    BufferedWriter os = null;
		    try {
		    	//		    		logger.info("Using no compression!");

		    	os = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), "UTF-8"));
//		    	os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
//		    	//		    		os.write("apenootjes");

		    	d.write(os, false, d.getHeader().getRPCName());    	
		    } finally {
		    	if ( os != null ) {
		    		try {
		    			os.flush();
		    			os.close();
		    		} catch (IOException e) {
		    			System.err.println("Error: " +  e);
		    		}
		    	}
		    }

//		    String contentEncoding = con.getContentEncoding();
		    //logger.info("Content encoding: "+contentEncoding);

		    String result = "";
		    
		    // Check for errors.
		    if ( con.getResponseCode() >= 400 ) {
	    		//throw new IOException(readErrorStream(con));
	    		System.err.println("Got error code: " + con.getResponseCode());
	    	}
		    
		    if ( false ) {
		    InputStream in = null;
		    Navajo n = null;
		    try {
		    	
		    	//} else {
		    		in = new InflaterInputStream(con.getInputStream());
		    		//in = con.getInputStream();
		    	//}
		    	if ( in != null ) {
		    		result = printHeader(con.getHeaderFields());
		    		try {
		    		n = NavajoFactory.getInstance().createNavajo(in);
		    		StringWriter sb = new StringWriter();
		    		n.write(sb);
		    		result = result + "\n" + sb.toString();
		    		} catch (Throwable t) {
		    			result = result + t.getLocalizedMessage();
		    		}
		    	}
		    } finally {
		    	if ( in != null ) {
		    		in.close();
		    		in = null;
		    	}
		    	//con.disconnect();
		    }
		    }
		    
		    return result;
		  }
	
//	private static final void sendMail(String content, Session session, String recipients, boolean debug)  {
//
//		try {
//			
//			javax.mail.Message msg = new MimeMessage(session);
//
//			msg.setFrom(new InternetAddress("noreply@sportlink.com"));
//
//			String [] recipientArray = recipients.split(",");
//			
//			InternetAddress[] addresses = new InternetAddress[recipientArray.length];
//
//			for (int i = 0; i < recipientArray.length; i++) {
//				addresses[i] = new InternetAddress(recipientArray[i]);
//			}
//
//			msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
//
//			msg.setSubject( (debug ? "Debug ACE Testscript" : "ACE Testscript failed" ));
//			msg.setSentDate(new java.util.Date());
//
//			msg.setText(content);
//			
//			Transport.send(msg);
//
//		} catch (Exception e) {
//			
//			e.printStackTrace(System.err);
//			
//		}
//	}
	
	public static void main(String [] args) throws Exception {
		
		boolean debug = false;
		
		if ( args.length < 5 ) {
			System.err.println("Usage: TestHttpClient [Postman URL] [Mail ontvangers] [Mail Server] [Aantal iteraties] [Max rate] {[debug]}");
			System.exit(1);
		}
		String url = args[0];
		String mail = args[1];
		String mailserver = args[2];
		String loopCount = args[3];
		Integer maxRate = Integer.parseInt(args[4]);
		double minTime = (1.0/maxRate)*1000.0;
		if ( args.length > 5 ) {
			debug = true;
			loopCount = "1";
		}
		System.err.println("url  = " + url);
		System.err.println("mail = " + mail);
		System.err.println("mailserver = " + mailserver);
		System.err.println("loopCount = " + loopCount);
		System.err.println("maxRate = " + maxRate);
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailserver);
		props.put("mail.smtp.port", "25");
		//Session session = Session.getInstance(props);
		
		double total = 0;
		for (int i = 0; i < Integer.parseInt(loopCount); i++) {
			try {
			 long start = System.currentTimeMillis();
			 String header = TestHttpClient.call(url);
			 long end = System.currentTimeMillis();
			 double sleepTime = (minTime - ( end - start ));
			 //System.err.println("sleepTime: " + sleepTime);
			 if ( sleepTime > 0 ) {
				 Thread.sleep((long) sleepTime);
				 end = System.currentTimeMillis();
			 }
			 total += ( end - start )/1000.0;
			 if ( 1 == 2 && ( debug || header.indexOf("accessId") == -1 )) {
					 System.err.println(header+"---------------------------------\n");
					 // SEND MAIL FOR FAILURE
					 //sendMail(header, session, mail, debug);
					 
				// }
				 //System.exit(1);
			   }
			} catch (Exception e) {
				e.printStackTrace(System.err);
				//System.exit(1);
			}
			 if ( i % 100 == 0) {
				 System.err.println("i: " + i + ", rate: " + 1.0/(total/100));
				 total = 0;
			 }
		}
		
	}
}
