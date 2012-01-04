package com.dexels.navajo.client.nql;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.client.nql.command.CallCommand;
import com.dexels.navajo.client.nql.command.FormatCommand;
import com.dexels.navajo.client.nql.command.OutputCommand;
import com.dexels.navajo.client.nql.command.ServiceCommand;
import com.dexels.navajo.client.nql.command.SetValueCommand;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class NQLContext {
	private final NavajoContext context;
	private Navajo current = null;
	private Object content = null;
//	private Writer outputWriter = null;
	private String mimeType = null;
	private OutputCallback callback = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NQLContext.class);
	
	public String getMimeType() {
		if(content!=null && (content instanceof Property)) {
			Property p = (Property)content;
			if(Property.BINARY_PROPERTY.equals(p.getType())) {
				Binary b = (Binary) p.getTypedValue();
				return b.guessContentType();
			}
		}
		return mimeType;
	}

	public void setCallback(OutputCallback callback) {
		this.callback = callback;
	}


	public NQLContext() {
		context = new NavajoContext();
		reset();
	}
	
	public Navajo getNavajo() {
		return current;
	}
	
	private void reset() {
		context.reset();
		content = null;
		current = null;
	}

	public void setup(String server, String username, String password) {
		context.setupClient(server, username, password);
	}
	
	/**
	 * 
	 * @param server Server can be null, then a suitable* server will be assembled from the requestParams
	 * @param username
	 * @param password
	 * @param requestServerName
	 * @param requestServerPort
	 * @param requestContextPath
	 */
	public void setupClient(String server, String username, String password,String requestServerName,int requestServerPort, String requestContextPath,String postmanPath) {
		context.setupClient(server, username, password,requestServerName,requestServerPort,requestContextPath,postmanPath, false);
	}


	public void output(String path) {
		if(current==null) {
			return;
		}
		Message m = current.getMessage(path);
		if(m!=null) {
			logger.info("Message found");
			content = m;
			return;
		}
		Property p = current.getProperty(path);
		if(p!=null) {
			logger.info("Property found");
			content = p.getTypedValue();
			return;
		}
		content = null;
	}

	// tml, btml, csv, tsv, json
	
	public void format(String type) throws IOException, NavajoException {
		if(callback==null) {
			throw new UnsupportedOperationException("No outputWriter set.");
		}
		if(content==null) {
			content = current;
		}
		logger.info("Type: "+type);
		if(type==null) {
			type="tml";
		}


		if("json".equals(type)) {
			setMimeType("application/json");
			
			writeJSON();
			return;
		}
		if("csv".equals(type)) {
			setMimeType("text/comma-separated-values");
			writeCSV(callback.getOutputStream(),",","\n","\"");
			return;
		}
		if("tsv".equals(type)) {
			setMimeType("text/tab-separated-values");
			writeCSV(callback.getOutputStream(),"\t","\n","");
			return;
		}
		if("btml".equals(type)) {
			setMimeType("text/xml");
			writeBTML(callback.getOutputStream());
			return;
		}
		if("tml".equals(type)) {
			setMimeType("text/xml");
			writeTML(callback.getOutputStream());
			return;
		}
		if("binary".equals(type)) {
			writeBinary(callback.getOutputStream());
			return;
		}
	}

	public void setMimeType(String mime) {
		this.mimeType = mime;
		if(callback!=null) {
			callback.setOutputType(mime);
		}
	}
	
	public void setContentLength(long l) {
		
		if(callback!=null) {
			callback.setContentLength(l);
		}
	}
	
	protected void writeJSON() throws IOException {
		Message m = null;
		if(content instanceof Navajo) {
			Navajo n = (Navajo)content;
			m = n.getRootMessage();
		} else {
			m = (Message)content;
		}
		Writer w = new OutputStreamWriter(callback.getOutputStream());
		m.writeJSON(w);
		w.flush();
		setMimeType("application/json");
	}

	private void writeBinary(OutputStream outputStream) throws IOException {
		Binary b = (Binary)content;
		setMimeType( b.getMimeType());
		if ( b != null && b.getLength() > 0 ) {
			setContentLength(b.getLength());
		}
		b.write(callback.getOutputStream());
		outputStream.write(b.getData());
	}

	private void writeTML(OutputStream outputStream) throws NavajoException, IOException {
		if(content instanceof Navajo) {
			Navajo m = (Navajo)content;
			m.write(outputStream);
		}
		if(content instanceof Message) {
			Message m = (Message)content;
			outputStream.write("<tml>\n".getBytes());
			m.write(outputStream);
			outputStream.write("</tml>\n".getBytes());
		}
	}
	
	private void writeBTML(OutputStream outputStream) throws IOException {
		if(!(content instanceof Message)) {
			throw new UnsupportedOperationException("Can not return entire Navajo message as BTML at this point. TODO: Add. Or use output.");
		}
		Message m = (Message)content;
		Writer w = new OutputStreamWriter(outputStream);
		NavajoLaszloConverter.writeBirtXml(m, w);
		w.flush();
	}

	private void writeCSV(OutputStream outputStream, String separator,String lineseparator, String embed) throws IOException {
		if(!(content instanceof Message)) {
			throw new UnsupportedOperationException("Can not return entire Navajo message as CSV/TSV. Use output.");
		}
		Writer w = new OutputStreamWriter(outputStream);

		Message m = (Message)content;
		assert("array".equals(m.getType()));
		for (Message e : m.getAllMessages()) {
			writeMessageLineCSV(w,e,separator,embed);
			w.write("\n");

		}
		w.flush();
	}
	
//	protected void writeOutput(Navajo resultMessage, java.io.OutputStreamWriter out, String serviceName) throws NavajoException {
//		Document laszlo = NavajoLaszloConverter.createLaszloFromNavajo(resultMessage,"navajoDataSource");
//		XMLDocumentUtils.write(laszlo,out,false);
//	}


	private void writeMessageLineCSV(Writer writer, Message e, String separator,String embed) throws IOException {
		int index = 0;
		for (Property p : e.getAllProperties()) {
			if(index!=0) {
				writer.write(separator);
			}
			if(embed!=null && !"".equals(embed)) {
				writer.write(embed);
			}
			String value = p.getValue();
			if(value==null) {
				value ="";
			}
			writer.write(value);
			if(embed!=null && !"".equals(embed)) {
				writer.write(embed);
			}

			index++;
		}
	}

	public void call(String service, boolean force) throws ClientException {
		 boolean present = context.hasNavajo(service);
		 if(!present || force) {
			 context.callService(service,current);
		 }
		 current = context.getNavajo(service);
	}

	
	
	public void set(String path, String value) {
		if(current==null) {
			current = NavajoFactory.getInstance().createNavajo();
		}
		Property p = current.getProperty(path);
		if(p==null) {
			// TODO append property if missing
			return;
		} 
		p.setValue(value);
	}
	
	public List<NQLCommand> parseCommand(String nql) {
		List<NQLCommand> cmds = new ArrayList<NQLCommand>();
		String[] elt = nql.split("\\|");
		for (String command : elt) {
			String[] parts = command.split(":");
			String cmd = parts[0];
			NQLCommand c = null;
			if(cmd.equals("call")) {
				c = new CallCommand();
			} else if(cmd.equals("output")) {
				c = new OutputCommand();
			} else if(cmd.equals("service")) {
				c = new ServiceCommand();
			} else if(cmd.equals("format")) {
				c = new FormatCommand();
			} else {
				c = new SetValueCommand();
			}
			c.parse(command);
			cmds.add(c);
		}
		return cmds;
	}
	
	

	public static void main(String[] args) throws NavajoException, ClientException, IOException {
		NQLContext nq = new NQLContext();
//		StringWriter sw = new StringWriter();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		nq.setCallback(new OutputCallback(){

			public OutputStream getOutputStream() {
				return baos;
			}

			public void setOutputType(String mime) {
				logger.info("Output detected: "+mime);
			}
			
			public void setContentLength(long l) {
				logger.info("Content length detected: "+l);
			}
		});
		
		
		nq.setup("penelope1.dexels.com/sportlink/knvb1_test/servlet/Postman", "ROOT", "R20T");
		String nql = "service:club/InitSearchClubs|ClubSearch/ShortName:Hoek|service:club/ProcessSearchClubs|output:Club|format:csv";
	
		nq.executeCommand(nql);
		
		String nql2 = "service:club/InitUpdateClub|Club/ClubIdentifier:BBFW63X|call:club/ProcessQueryClub|output:ClubData/Logo|format:binary";
		nq.executeCommand(nql2);
		logger.info("TYPE: "+nq.mimeType);
		logger.info("Bytes written: "+baos.size());
//		logger.info(sw.toString());
		//	nq.getNavajo().write(System.err);
	}

	public void executeCommand(String nql) throws ClientException, NavajoException, IOException {
		List<NQLCommand>aa =  parseCommand(nql);
		for (NQLCommand nqlCommand : aa) {
			nqlCommand.execute(this);
		}
	}


}
