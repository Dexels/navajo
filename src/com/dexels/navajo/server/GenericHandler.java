package com.dexels.navajo.server;

import java.util.*;
import javax.naming.*;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.document.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class GenericHandler extends ServiceHandler {

    private static String adapterPath = "";

    private final static Logger logger = Logger.getLogger( GenericHandler.class );

    public GenericHandler() {}

    public String getAdapterPath() {
        return this.adapterPath;
    }

    public Navajo doService()
            throws NavajoException, UserException, SystemException {
        // TODO: implement this com.dexels.navajo.server.NavajoServerServlet abstract method

        Navajo outDoc = null;

//        String scriptPath = properties.getScriptPath();

        adapterPath = properties.getAdapterPath();

        XmlMapperInterpreter mi = null;

        try {
            mi = new XmlMapperInterpreter(access.rpcName, requestDocument, parms, properties, access);
        } catch (java.io.IOException ioe) {
            logger.log(Priority.ERROR, "IO Exception", ioe);
            throw new SystemException(-1, ioe.getMessage());
        } catch (org.xml.sax.SAXException saxe) {
            logger.log(Priority.ERROR, "XML parse exception", saxe);
            throw new SystemException(-1, saxe.getMessage());
        }

        Util.debugLog(this, "Created MapperInterpreter version 10.0");
        try {
            Util.debugLog(this, "Before calling interpret() version 10.0");
            // long start = System.currentTimeMillis();
            outDoc = mi.interpret(access.rpcName);
            // long end = System.currentTimeMillis();
            // Util.debugLog(this, "Finished interpret(). Interpretation took " + (end - start)/1000.0 + " secs.");
        } catch (MappingException me) {
            //Util.debugLog("MappingException occured: " + me.getMessage());
            System.gc();
            throw new SystemException(-1, me.getMessage());
        } catch (MappableException mme) {
            //Util.debugLog("MappableException occured: " + mme.getMessage());
            System.gc();
            throw new SystemException(-1, "Error in Mappable object: " + mme.getMessage());
        }
        return outDoc;
    }

}
