package com.dexels.navajo.birt.adapter;

import java.io.IOException;

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class CreateBirtMap implements Mappable {

	private Navajo myNavajo = null;
	public Binary reportTemplate = null;
	public Binary emptyReport = null;
	
	public void kill() {
		// TODO Auto-generated method stub

	}
	//Property marginProperty = inNavajo.getProperty("/__ReportDefinition/Margin");
	
	public void load(Access access) throws MappableException, UserException {
		myNavajo = access.getInDoc();
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public Binary getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(Binary reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public Binary getEmptyReport() throws IOException, NavajoException {
		BirtUtils b = new BirtUtils();
		emptyReport = b.createEmptyReport(myNavajo, getReportTemplate());
		return emptyReport;
	}

	public void setEmptyReport(Binary emptyReport) {
		
		this.emptyReport = emptyReport;
	}

	
}
