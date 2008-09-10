package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDocIndexDOM</p>
 * <p>Description: DOM Representing the index of services the
 * documentor has collected and documented in HTML</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.io.File;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.dexels.navajo.util.navadoc.config.DocumentSet;

public class NavaDocIndexDOM extends NavaDocBaseDOM {

	public static final String vcIdent = "$Id$";

	private String title = null;
	private Element table = null;
	private Element tbody = null;
	private Element firstRefRow = null;
	private Element breadcrumb = null;
	
	private String imagesRoot = dset.getProperty(NavaDocConstants.IMAGES_URI_PROPERTY);
	/**
	 * Contruct a new index DOM, document headers will be set-up
	 * 
	 * @param String
	 *          project name, null is OK
	 * @param String
	 *          URI to CSS style-sheet, null is OK
	 */

	public NavaDocIndexDOM(final DocumentSet dset) throws ParserConfigurationException {
		super(dset);

		this.title = "NavaDoc";
		this.setHeaders(this.title);
		this.addBody("navadoc");

		// Breadcrumb
		this.breadcrumb = this.dom.createElement("div");
		breadcrumb.setAttribute("class", "breadcrumb");
		this.divBreadcrumb.appendChild(breadcrumb);
		//

		// Page Body Header
		final Element h1 = this.dom.createElement("h1");
		final Text titleText = this.dom.createTextNode(title);

		h1.appendChild(titleText);
		this.divHeader.appendChild(h1);

		// Project Description
		final Element desc = this.dom.createElement("div");
		desc.setAttribute("class", "page-description");
		final Text dText = this.dom.createTextNode(dset.getDescription());
		desc.appendChild(dText);
		this.bodyWrapper.appendChild(desc);

		// Table
		this.table = this.dom.createElement("table");
		this.table.setAttribute("align", "center");
		this.table.setAttribute("id", "service-list");
		this.table.setAttribute("border", "0");
		this.table.setAttribute("cellspacing", "0");
		this.table.setAttribute("cellpadding", "3");
		this.table.setAttribute("class", "tablesorter");
		this.divMain.appendChild(this.table);
		
		String jsPath = dset.getProperty(NavaDocConstants.JS_URI_PROPERTY);
	
		Element tableSorter = this.dom.createElement("script");
		tableSorter.setAttribute("type", "text/javascript");
		tableSorter.setAttribute("src", jsPath+"footer.js");
	
		bodyWrapper.appendChild(tableSorter);
		
		// Table Header
		Element thead = this.dom.createElement("thead");
		Element thRow = this.dom.createElement("tr");
		Element thRowIndex = this.dom.createElement("th");
		Element thLeft = this.dom.createElement("th");
		Element thRight = this.dom.createElement("th");

		Text textLeft = this.dom.createTextNode("Service");
		Text textRight = this.dom.createTextNode("Description");
		Text textRowIndex = this.dom.createTextNode("T");
		
		thRowIndex.appendChild(textRowIndex);
		thLeft.appendChild(textLeft);
		thRight.appendChild(textRight);
		thRow.appendChild(thRowIndex);
		thRow.appendChild(thLeft);
		thRow.appendChild(thRight);
		thead.appendChild(thRow);
		this.table.appendChild(thead);

		// Table body
		this.tbody = this.dom.createElement("tbody");
		this.table.appendChild(this.tbody);

	} // public NavaDocIndexDOM()

	public void createBreadCrumb(String bc) {

		// Remove last entry.
		if (bc.indexOf(File.separator) > 0) {
			// bc = bc.substring( bc.lastIndexOf(File.separator));
		}

		StringTokenizer tok = new StringTokenizer(bc, File.separator);
		int count = tok.countTokens();

		Element a = this.dom.createElement("a");
		a.setAttribute("class", "breadcrumb-up-href");
		String up = "./";
		for (int i = 0; i < count; i++) {
			up = up + "../";
		}

		a.setAttribute("href", this.baseUri + up + "index.html");
		a.setTextContent("..");
		breadcrumb.appendChild(a);

		while (tok.hasMoreTokens()) {

			String token = tok.nextToken();

			Element b = this.dom.createElement("a");
			up = "./";
			for (int i = 0; i < count - 1; i++) {
				up = up + "../";
			}
			count--;

			b.setAttribute("href", this.baseUri + up + "index.html");
			b.setTextContent("/" + token);
			if (count > 0) {
				b.setAttribute("class", "breadcrumb-up-href");
			} else {
				b.setAttribute("class", "breadcrumb-end-href");
			}

			breadcrumb.appendChild(b);
		}

	}

	/**
	 * Adds an web services as an index entry to the DOM, constructing the correct
	 * HREF link
	 * 
	 * @param String
	 *          name of web service
	 * @param String
	 *          optional notes/description, null is OK
	 */

	public void addEntry(String sname, String notes) {
		if (sname.indexOf(File.separator) > 0) {
			sname = sname.substring(sname.lastIndexOf(File.separator) + 1);
		}
		final String href = this.baseUri + sname + ".html";
		this.addEntryRow(sname, notes, href);

	} // public void addEntry()

	/**
	 * Adds an web services as an index entry to the DOM, constructing the correct
	 * HREF link from the HttpServlet
	 * 
	 * @param String
	 *          name of web service
	 * @param String
	 *          optional notes/description, null is OK
	 * @param additional
	 *          path info from the servlet [HttpServletRequest.getPathInfo()]
	 */
	public void addEntry(String sname, String notes, String uri) {
		final String href = uri + "?sname=" + sname;
		this.addEntryRow(sname, notes, href);
	}

	// ----------------------------------------------------------- private methods

	/**
	 * creates the row in the index table given the service name, notes and link
	 * information
	 * 
	 * @param String
	 *          name of web service
	 * @param String
	 *          optional notes/description, null is OK
	 * @param URI
	 *          link data
	 */

	public void addEntryRow(String sname, String notes, String href) {
		final Element tr = this.dom.createElement("tr");
		
		final Element tdRowIndex = this.dom.createElement("td");
		tr.appendChild(tdRowIndex);
		Element img = this.dom.createElement("img");
		img.setAttribute("src", imagesRoot + "document.png");
		img.setAttribute("border", "0");
		tdRowIndex.appendChild(img);
		
		final Element tdLeft = this.dom.createElement("td");
		

		tdLeft.setAttribute("class", "service-name");
		final Element a = this.dom.createElement("a");

		a.setAttribute("href", href);
		final Text serviceText = this.dom.createTextNode(sname);

		a.appendChild(serviceText);

		final Element tdRight = this.dom.createElement("td");
		tdRight.setAttribute("class", "service-description");

		if (notes == null || "".equals(notes)) {
			notes = "&nbsp;";
		}
		Text notesText = this.dom.createTextNode(notes);

		tdLeft.appendChild(a);
		tdRight.appendChild(notesText);
		tr.appendChild(tdLeft);
		tr.appendChild(tdRight);
		this.tbody.appendChild(tr);

		if (this.firstRefRow == null) {
			this.firstRefRow = tr;
		}

	}

	public void addSubFolderRow(String sname, String notes, String href) {
		final Element tr = this.dom.createElement("tr");

		final Element tdRowIndex = this.dom.createElement("td");
		tr.appendChild(tdRowIndex);
		Element img = this.dom.createElement("img");
		img.setAttribute("src", imagesRoot + "folder.png");
		img.setAttribute("border", "0");
		tdRowIndex.appendChild(img);

		
		final Element tdLeft = this.dom.createElement("td");

		tdLeft.setAttribute("class", "folder-name");
		// tdLeft.setAttribute( "colspan", "2" );
		final Element a = this.dom.createElement("a");

		a.setAttribute("href", href);
		final Text serviceText = this.dom.createTextNode(sname);

		a.appendChild(serviceText);

		final Element tdRight = this.dom.createElement("td");
		tdRight.setAttribute("class", "folder-description");

		if (notes == null || "".equals(notes)) {
			notes = "&nbsp;";
		}
		Text notesText = this.dom.createTextNode(notes);

		tdLeft.appendChild(a);
		tdRight.appendChild(notesText);
		tr.appendChild(tdLeft);
		tr.appendChild(tdRight);
		this.tbody.appendChild(tr);

		if (this.firstRefRow == null) {
			this.firstRefRow = tr;
		}

	}

} // public class NavaDocIndexDOM

// EOF: $RCSfile$ //
