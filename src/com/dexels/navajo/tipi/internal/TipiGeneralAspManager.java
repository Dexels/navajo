/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;

public class TipiGeneralAspManager implements TipiStorageManager {
    
    public static final String STORAGE_UPDATE_SERVICE = "ProcessUpdateDocument";
    public static final String STORAGE_QUERY_SERVICE = "ProcessQueryDocument";
    public final String scriptPrefix;    
//    public final String authorId;
    private String instanceId;    
    
    public static final String TYPE_SETTING = "TIPI_SETTING";

    
    public TipiGeneralAspManager(String scriptPrefix, String instanceId) {
        this.scriptPrefix = scriptPrefix;
        this.instanceId = instanceId;
    }
    
    public Navajo getStorageDocument(String id) throws TipiException {
        Navajo reply = null;
        try {
            Navajo request = constructRequest(id);
            reply = NavajoClientFactory.getClient().doSimpleSend(request, scriptPrefix+STORAGE_QUERY_SERVICE);
            Message err = reply.getMessage("error");
            if (err!=null) {
                System.err.println("SERVER ERROR: ");
                err.write(System.err);
                throw new TipiException("Server-side error while storing settings: "+err.toString());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            throw new TipiException("Client exception while storing settings: ",e);
        } catch (NavajoException e) {
            e.printStackTrace();
            throw new TipiException("Client side exception while preparing to store settings: ",e);
        }
        if (reply==null) {
            throw new TipiException("Unknown problem while storing settings. ");
        }

        Message document = reply.getMessage("Document");
        if (document==null) {
            throw new TipiException("Unknown problem while storing settings. ");
        }
        Property data = document.getProperty("Data");
        if (data==null) {
            throw new TipiException("Unknown problem while storing settings. ");
        }
        Binary b = (Binary)data.getTypedValue();
        if (b==null) {
            return null;
        }
        Navajo response = NavajoFactory.getInstance().createNavajo(new ByteArrayInputStream(b.getData()));
        return response;
    }

    public void setStorageDocument(String id, Navajo n) throws TipiException {
        try {
            Navajo request = constructUpdateRequest(id, n);
            Navajo reply = NavajoClientFactory.getClient().doSimpleSend(request, scriptPrefix+STORAGE_UPDATE_SERVICE);
            Message err = reply.getMessage("error");
            if (err!=null) {
                   System.err.println("SERVER ERROR: ");
                   err.write(System.err);
                throw new TipiException("Server-side error while storing settings: "+err.toString());
            }
        } catch (ClientException e) {
            e.printStackTrace();
            throw new TipiException("Client exception while storing settings: ",e);
        } catch (NavajoException e) {
            e.printStackTrace();
            throw new TipiException("Client side exception while preparing to store settings: ",e);
        }
        
    }

    private Navajo constructUpdateRequest(String id, Navajo contents) throws NavajoException {
        Navajo n = constructRequest(id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        contents.write(baos);
        Binary b = new Binary(baos.toByteArray());
        Message document = n.getMessage("Document");
        Property contentProp = NavajoFactory.getInstance().createProperty(n, "Data", Property.BINARY_PROPERTY, "", 0, "", Property.DIR_IN, null);
        contentProp.setAnyValue(b);
        document.addProperty(contentProp);
        return n;
    }
    
    private Navajo constructRequest(String id) throws NavajoException {
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message document = NavajoFactory.getInstance().createMessage(n, "Document");
        n.addMessage(document);
        Property authorId = NavajoFactory.getInstance().createProperty(n, "AuthorId", Property.STRING_PROPERTY, this.instanceId, 0, "", Property.DIR_IN, null);
        document.addProperty(authorId);
        Property idProp = NavajoFactory.getInstance().createProperty(n, "ObjectId", Property.STRING_PROPERTY, id, 0, "", Property.DIR_IN, null);
        Property nameProp = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, id, 0, "", Property.DIR_IN, null);
        Property documentId = NavajoFactory.getInstance().createProperty(n, "DocumentId", Property.STRING_PROPERTY, null, 0, "", Property.DIR_IN, null);
        Property objectType = NavajoFactory.getInstance().createProperty(n, "ObjectType", Property.STRING_PROPERTY, "SETTING", 0, "", Property.DIR_IN, null);
        Property description = NavajoFactory.getInstance().createProperty(n, "Description", Property.STRING_PROPERTY, "", 0, "", Property.DIR_IN, null);
        Property mime = NavajoFactory.getInstance().createProperty(n, "MimeType", Property.STRING_PROPERTY, "text/xml", 0, "", Property.DIR_IN, null);
        document.addProperty(idProp);
        document.addProperty(nameProp);
        document.addProperty(documentId);
        document.addProperty(objectType);
        document.addProperty(description);
        document.addProperty(mime);
        return n;
    }

    public void setInstanceId(String id) {
        instanceId = id;
    }

}
