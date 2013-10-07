package com.dexels.navajo.birt.adapter;

import java.io.*;

import com.dexels.navajo.birt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class CreateBirtMap implements Mappable {

	private Navajo myNavajo = null;
	public Binary reportTemplate = null;
	public Binary emptyReport = null;
	
	@Override
	public void kill() {

	}
	//Property marginProperty = inNavajo.getProperty("/__ReportDefinition/Margin");
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		myNavajo = access.getInDoc();
	}

	@Override
	public void store() throws MappableException, UserException {

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
