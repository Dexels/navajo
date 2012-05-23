package com.dexels.navajo.util.navadoc.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * <p>
 * Title: NavaDoc
 * </p>
 * <p>
 * Description: Navajo Web Services Automated Documentation Facility servet to
 * document a given web service from a given document set on the fly, Requires
 * the following params: sname = web service name, set = document set name
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002 - 2003
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Matthew Eichler meichler@dexels.com
 * @version $Id$
 */

import com.dexels.navajo.util.navadoc.NavaDocConstants;
import com.dexels.navajo.util.navadoc.NavaDocTransformer;
import com.dexels.navajo.util.navadoc.NavaDocOutputter;

import com.dexels.navajo.util.navadoc.config.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;
import com.dexels.navajo.util.navadoc.config.DocumentSet;



public class DocumentWebService extends HttpServlet {

    public static final String vcIdent = "$Id$";

    private String configUri;

    private NavaDocConfigurator config;

    //Initialize global variables
    public void init() throws ServletException {

    	System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl");
    	
        this.configUri = this
                .getInitParameter( NavaDocConstants.WEB_CONFIG_INITPARAM );
        if ( ( this.configUri == null ) || ( this.configUri.length() == 0 ) ) {
            throw new ServletException(
                    "path to configuration file required to start servlet, "
                            + "check the 'web.xml' file" );
        }

        this.configure();
    }

    //Process the HTTP Get request
    public void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        String sname = null;
        String set = null;
        DocumentSet dset = null;

        // parameters
        HttpSession sess = request.getSession( true );
        set = request.getParameter( NavaDocConstants.WEB_SET_PARAMETER );
        if ( ( set == null ) || ( set.length() == 0 ) ) {
            set = (String) sess
                    .getAttribute( NavaDocConstants.WEB_SET_PARAMETER );
        }
        if ( ( set == null ) || ( set.length() == 0 ) ) {
            dset = this.config.getDefaultSet();
            if ( dset == null ) {
                throw new ServletException(
                        "could not determine default document set" );
            }
            set = dset.getName();
        } else {

            dset = (DocumentSet) this.config.getDocumentSetMap().get( set );
            if ( dset == null ) {
                throw new ServletException( "provided invalid document set '"
                        + set + "'" );
            }
        }

        final NavaDocTransformer transformer = this.getTransformer( dset );
        
        response.setContentType( NavaDocConstants.WEB_CONTENT_TYPE );

        sname = request.getParameter( NavaDocConstants.WEB_SNAME_PARAMETER );
        if ( ( sname != null ) && ( sname.length() > 0 ) ) {

            transformer.transformWebService( sname );
            final NavaDocOutputter outputter = new NavaDocOutputter(transformer, (PrintWriter) null);
            
            response.sendRedirect("doc/" + sname + ".html");
            return;
        }
     
        sess.setAttribute( NavaDocConstants.WEB_SET_PARAMETER, set );
    }

    private NavaDocTransformer getTransformer( final DocumentSet dset )
            throws ServletException {

     

            final File sPath = dset.getPathConfiguration().getPath(
                    NavaDocConstants.SVC_PATH_ELEMENT );
            final File styleSheet = dset.getPathConfiguration().getPath(
                    NavaDocConstants.STYLE_PATH_ELEMENT );
            final String indent = ( dset.getProperty( NavaDocConstants.INDENT ) != null ) ? dset
                    .getProperty( NavaDocConstants.INDENT )
                    : NavaDocConstants.DEFAULT_INDENT_AMOUNT;
            final String cssUri = dset.getProperty( "css-uri" );

            try {
            	NavaDocTransformer t = new NavaDocTransformer( styleSheet, sPath, indent );

                // set optional parameters, nulls OK
                t.setProjectName( dset.getName() );
                t.setCssUri( cssUri );
   
                return t;
            } catch ( Exception e ) {
                throw new ServletException( e.getMessage() );

            }
    } 

    /**
     * reads the configuration from the specific NavaDoc XML configuration using
     * a base filesystem path if found
     * 
     * @throws ServletException
     */

    private void configure() throws ServletException {

        final String base = this
                .getInitParameter( NavaDocConstants.WEB_BASE_INITPARAM );
        
        System.err.println(">>>>>>>>>>>>>>>>> FOUND BASE: " + base);
        if ( ( base != null ) && ( base.length() > 0 ) ) {
            System.setProperty( NavaDocConstants.BASE_SYS_PROPERTY, base );
        }
        
        System.err.println("BASE = " + base + ", configUri = " + configUri);

        final NavaDocConfigurator conf = new NavaDocConfigurator(
                this.configUri );
        try {
            conf.configure();
        } catch ( ConfigurationException ex ) {
        	ex.printStackTrace(System.err);
            throw new ServletException( ex.toString() );
        }

        this.setConfiguration( conf );
    }

    /**
     * stores the NavaDoc configuration
     * 
     * @param NavaDocConfigurator
     */

    private synchronized void setConfiguration( NavaDocConfigurator conf ) {
        this.config = conf;
    }


 
}