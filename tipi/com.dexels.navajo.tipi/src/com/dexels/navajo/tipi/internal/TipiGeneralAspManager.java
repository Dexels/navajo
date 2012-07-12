/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStorageManager;

public class TipiGeneralAspManager implements TipiStorageManager, Serializable {

	private static final long serialVersionUID = 6208612779791555408L;
	public static final String STORAGE_UPDATE_SERVICE = "ProcessUpdateBinary";
	public static final String STORAGE_QUERY_SERVICE = "ProcessQueryBinary";
	public final String scriptPrefix;
	// public final String authorId;
	private String instanceId;
	private TipiContext myContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiGeneralAspManager.class);
	public static final String TYPE_SETTING = "TIPI_SETTING";

	public TipiGeneralAspManager(String scriptPrefix) {
		this.scriptPrefix = scriptPrefix;
	}

	public Navajo getStorageDocument(String id) throws TipiException {
		Navajo reply = null;
		try {
			Navajo request = constructRequest(id);
			reply = myContext.getClient().doSimpleSend(request,
					scriptPrefix + STORAGE_QUERY_SERVICE);
			Message err = reply.getMessage("error");
			if (err != null) {
				logger.error("SERVER ERROR: ");
				err.write(System.err);
				throw new TipiException(
						"Server-side error while storing settings: "
								+ err.toString());
			}
		} catch (ClientException e) {
			e.printStackTrace();
			throw new TipiException(
					"Client exception while storing settings: ", e);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException(
					"Client side exception while preparing to store settings: ",
					e);
		}

		Message document = reply.getMessage("Document");
		if (document == null) {
			throw new TipiException("Unknown problem while storing settings. ");
		}
		Property data = document.getProperty("Data");
		if (data == null) {
			throw new TipiException("Unknown problem while storing settings. ");
		}
		Binary b = (Binary) data.getTypedValue();
		if (b == null) {
			return null;
		}
		InputStream is = b.getDataAsStream();
		Navajo response = NavajoFactory.getInstance().createNavajo(is);
		try {
			is.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return response;
	}

	public void setStorageDocument(String id, Navajo n) throws TipiException {
		try {
			Navajo request = constructUpdateRequest(id, n);
			Navajo reply = myContext.getClient().doSimpleSend(request,
					scriptPrefix + STORAGE_UPDATE_SERVICE);
			Message err = reply.getMessage("error");
			if (err != null) {
				logger.error("SERVER ERROR: ");
				err.write(System.err);
				throw new TipiException(
						"Server-side error while storing settings: "
								+ err.toString());
			}
		} catch (ClientException e) {
			e.printStackTrace();
			throw new TipiException(
					"Client exception while storing settings: ", e);
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException(
					"Client side exception while preparing to store settings: ",
					e);
		}

	}

	private Navajo constructUpdateRequest(String id, Navajo contents)
			throws NavajoException {
		Navajo n = constructRequest(id);
		Binary b = new Binary();
		OutputStream baos = b.getOutputStream();
		contents.write(baos);
		try {
			baos.flush();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Message document = n.getMessage("Document");
		Property contentProp = NavajoFactory.getInstance().createProperty(n,
				"Data", Property.BINARY_PROPERTY, "", 0, "", Property.DIR_IN,
				null);
		contentProp.setAnyValue(b);
		document.addProperty(contentProp);
		return n;
	}

	private Navajo constructRequest(String id) throws NavajoException {
		// TODO Clean up a bit. Only ObjectId and ClubIdentifier needed
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message document = NavajoFactory.getInstance().createMessage(n,
				"Document");
		n.addMessage(document);
		Property authorId = NavajoFactory.getInstance().createProperty(n,
				"AuthorId", Property.STRING_PROPERTY, this.instanceId, 0, "",
				Property.DIR_IN, null);
		document.addProperty(authorId);
		Property idProp = NavajoFactory.getInstance().createProperty(n,
				"ObjectId", Property.STRING_PROPERTY, id, 0, "",
				Property.DIR_IN, null);
		Property nameProp = NavajoFactory.getInstance().createProperty(n,
				"Name", Property.STRING_PROPERTY, id, 0, "", Property.DIR_IN,
				null);
		Property documentId = NavajoFactory.getInstance().createProperty(n,
				"DocumentId", Property.STRING_PROPERTY, null, 0, "",
				Property.DIR_IN, null);
		Property objectType = NavajoFactory.getInstance().createProperty(n,
				"ObjectType", Property.STRING_PROPERTY, "SETTING", 0, "",
				Property.DIR_IN, null);
		Property description = NavajoFactory.getInstance().createProperty(n,
				"Description", Property.STRING_PROPERTY, "", 0, "",
				Property.DIR_IN, null);
		Property mime = NavajoFactory.getInstance().createProperty(n,
				"MimeType", Property.STRING_PROPERTY, "text/xml", 0, "",
				Property.DIR_IN, null);
		document.addProperty(idProp);
		document.addProperty(nameProp);
		document.addProperty(documentId);
		document.addProperty(objectType);
		document.addProperty(description);
		document.addProperty(mime);
		Message club = NavajoFactory.getInstance().createMessage(n, "Storage");
		n.addMessage(club);
		Property clubId = NavajoFactory.getInstance().createProperty(n,
				"DbIdentifier", Property.STRING_PROPERTY, this.instanceId, 0,
				"", Property.DIR_IN, null);
		club.addProperty(clubId);
		return n;
	}

	public void setInstanceId(String id) {
		logger.info("Setting sublocale to: " + id);
		instanceId = id;
	}

	public void setContext(TipiContext tc) {
		this.myContext = tc;

	}

}
