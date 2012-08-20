package com.dexels.navajo.dev.console;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.document.NavajoException;

public class NqlCommand {

	private NqlContextApi nqlContext;

	public NqlCommand() {
	}

	 public void nql(final CommandSession session) {
		 nql(session,"service:club/InitSearchClubs|ClubSearch/ShortName:Hoek|service:club/ProcessSearchClubs|output:Club|format:csv");
	 }
	
		@Descriptor(value = "execute an nql command") 
	 public void nql(final CommandSession session, String command) {
//		 List<NQLCommand> commands = nqlContext.parseCommand(command);
		 final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 
		 OutputCallback oc = new OutputCallback() {
			
			@Override
			public void setOutputType(String mime) {
				session.getConsole().println("Mime type: "+mime);
			}
			
			@Override
			public void setContentLength(long l) {
				session.getConsole().println("Content length: "+l);
			}
			
			@Override
			public OutputStream getOutputStream() {
				return baos;
			}
		};
		 try {
			nqlContext.executeCommand(command, oc);
		} catch (NavajoException e) {
			e.printStackTrace(session.getConsole());
		} catch (ClientException e) {
			e.printStackTrace(session.getConsole());
		} catch (IOException e) {
			e.printStackTrace(session.getConsole());
		}
		String res = new String(baos.toByteArray());
		session.getConsole().println(res);
	 }

	public void setNqlContext(NqlContextApi nqlContext) {
		this.nqlContext = nqlContext;
	}
}
