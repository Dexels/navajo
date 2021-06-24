/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.birt.adapter;

import java.io.IOException;

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class CreateBirtMap implements Mappable {

	private Navajo myNavajo = null;
	public Binary reportTemplate = null;
	public Binary emptyReport = null;
	
	@Override
	public void kill() {

	}
	
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

	public Binary getEmptyReport() throws IOException {
		BirtUtils b = new BirtUtils();
		emptyReport = b.createEmptyReport(myNavajo, getReportTemplate());
		return emptyReport;
	}

	public void setEmptyReport(Binary emptyReport) {
		
		this.emptyReport = emptyReport;
	}

	
}
