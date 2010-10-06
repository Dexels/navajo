package com.dexels.navajo.client.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.DateFormatter;

import com.dexels.navajo.client.logger.ClientLogger;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public class BaseNavajoClientLogger implements ClientLogger {

	private File log;
	public BaseNavajoClientLogger() {
		File tmp = new File(System.getProperty("java.io.tmpdir"));
		log = new File(tmp,"NavajoLog");
		log.delete();
		log.mkdirs();
		System.err.println("Logger: "+log.getAbsolutePath());
	}
	
	public void logInput(String service, Navajo in) {
		File infile = new File(getLogLocation(service),getInputName());
		dump(infile,in);

	}

	private void dump(File file, Navajo in) {
		
		try {
			FileWriter fw = new FileWriter(file);
			if(in!=null) {
				in.write(fw);
			} else {
				fw.write("null navajo!\n");
			}
			fw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logOutput(String service, Navajo out) {
		File outputfile = new File(getLogLocation(service),getOutputName());
		dump(outputfile,out);

	}

	public void logOutput(String service, Navajo in, Navajo out) {
		logInput(service, in);
		logOutput(service, out);

	}

	private String convertServiceName(String service) {
		return service.replaceAll("/", "_");
	}
	
	private File getLogLocation(String service) {
		File f = new File(log,convertServiceName(service));
		if(!f.exists()) {
			f.mkdirs();
		}
		return f;
	}

	
	private String getInputName() {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.S");
		String formatted = formatter.format(d);
		return formatted + ".in.txt";
	}

	private String getOutputName() {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.S");
		String formatted = formatter.format(d);
		return formatted + ".out.txt";
	}
	
	
	public static void main(String[] args) {
		Date d = new Date();
		String service = "init/App/Blarara";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.S");
		String formatted = formatter.format(d);
		
		BaseNavajoClientLogger instance = new BaseNavajoClientLogger();
		System.err.println(instance.convertServiceName(service));
		System.err.println(">> "+formatted);
		Navajo n = NavajoFactory.getInstance().createNavajo();
		instance.logInput("club/InitAap", n);
		
	}
}
