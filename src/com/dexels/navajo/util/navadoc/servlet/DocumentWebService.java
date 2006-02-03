package com.dexels.navajo.util.navadoc.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

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
import com.dexels.navajo.util.navadoc.ServicesList;
import com.dexels.navajo.util.navadoc.NavaDocTransformer;
import com.dexels.navajo.util.navadoc.NavaDocOutputter;
import com.dexels.navajo.util.navadoc.NavaDocIndexDOM;

import com.dexels.navajo.util.navadoc.config.NavaDocConfigurator;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;
import com.dexels.navajo.util.navadoc.config.DocumentSet;



public class DocumentWebService extends HttpServlet {

    public static final String vcIdent = "$Id$";

    private String configUri;

    private NavaDocConfigurator config;

    // this caches transformers so we only ever create one transformer per
    // document set requested
    private Map transformMap = new HashMap();

    private Map slistMap = new HashMap();

    private Map indexMap = new HashMap();

    // ------------------------------------------------------------ public
    // methods

    //Initialize global variables
    public void init() throws ServletException {

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

        if ( request.getParameter( NavaDocConstants.WEB_FLUSH_PARAMETER ) != null ) {
            this.flush();
        }

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
        final ServicesList list = (ServicesList) this.slistMap.get( dset
                .getName() );
        if ( list == null ) {
            throw new ServletException( "null services list for document set '"
                    + set + "'" );
        }
        response.setContentType( NavaDocConstants.WEB_CONTENT_TYPE );

        sname = request.getParameter( NavaDocConstants.WEB_SNAME_PARAMETER );
        if ( ( sname != null ) && ( sname.length() > 0 ) ) {
            if ( !list.contains( sname ) ) {
                throw new ServletException( "'" + sname
                        + "' web service not found in '" + set + "'" );
            }

            transformer.transformWebService( sname );
            final NavaDocOutputter outputter = new NavaDocOutputter(
                    transformer, response.getWriter() );
            return;
        }
        // create an index page
        NavaDocIndexDOM idx = getIndexDOM( dset, list, transformer, request );
        final NavaDocOutputter outputter = new NavaDocOutputter( idx, response
                .getWriter() );

        sess.setAttribute( NavaDocConstants.WEB_SET_PARAMETER, set );
    }

    //Clean up resources
    public void destroy() {
    }

    // ----------------------------------------------------------- private
    // methods

    private NavaDocTransformer getTransformer( final DocumentSet dset )
            throws ServletException {

        NavaDocTransformer t = (NavaDocTransformer) this.transformMap.get( dset
                .getName() );
        if ( t == null ) {

            final File sPath = dset.getPathConfiguration().getPath(
                    NavaDocConstants.SVC_PATH_ELEMENT );
            final File styleSheet = dset.getPathConfiguration().getPath(
                    NavaDocConstants.STYLE_PATH_ELEMENT );
            final String indent = ( dset.getProperty( NavaDocConstants.INDENT ) != null ) ? dset
                    .getProperty( NavaDocConstants.INDENT )
                    : NavaDocConstants.DEFAULT_INDENT_AMOUNT;
            final String cssUri = dset.getProperty( "css-uri" );

            try {
                t = new NavaDocTransformer( styleSheet, sPath, indent );

                // set optional parameters, nulls OK
                t.setProjectName( dset.getName() );
                t.setCssUri( cssUri );
               

                // cache the list of webservices found in this set
                final ServicesList l = new ServicesList( sPath );

                this.setCache( dset.getName(), t, l );

            } catch ( Exception e ) {
                throw new ServletException( e.getMessage() );

            }

        }

        return ( t );
    } // private NavaDocTransformer getTransformer(final DocumentSet dset)

    /**
     * reads the configuration from the specific NavaDoc XML configuration using
     * a base filesystem path if found
     * 
     * @throws ServletException
     */

    private void configure() throws ServletException {

        final String base = this
                .getInitParameter( NavaDocConstants.WEB_BASE_INITPARAM );
        if ( ( base != null ) && ( base.length() > 0 ) ) {
            System.setProperty( NavaDocConstants.BASE_SYS_PROPERTY, base );
        }

        final NavaDocConfigurator conf = new NavaDocConfigurator(
                this.configUri );
        try {
            conf.configure();
        } catch ( ConfigurationException ex ) {
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

    /**
     * shared data, needs to be sychronized
     * 
     * @param name
     *            of document set
     * @param NavaDocTransformer
     * @param ServicesList
     */

    private synchronized void setCache( final String name,
            final NavaDocTransformer t, final ServicesList l ) {
        this.transformMap.put( name, t );
        this.slistMap.put( name, l );
  
    }

    /**
     * creates an index page or simply retrieves a cached one
     * 
     * @param DocumentSet
     * @param ServicesList
     * @param NavaDocTransformer
     * @return NavaDocIndexDOM
     * @throws ServletException
     *             if there's any trouble reading services notes
     */

    private NavaDocIndexDOM getIndexDOM( DocumentSet dset, ServicesList list,
            NavaDocTransformer transformer, HttpServletRequest request )
            throws ServletException {
        NavaDocIndexDOM idx = (NavaDocIndexDOM) this.indexMap.get( dset
                .getName() );
        if ( idx == null ) {
            try {
                idx = new NavaDocIndexDOM( dset );
                final Iterator iter = list.iterator();
                while ( iter.hasNext() ) {
                    final String sname = (String) iter.next();
                    idx.addEntry( sname, transformer.getNotes( sname ), request
                            .getRequestURI() );
                }
                this.cacheIndex( dset.getName(), idx );
            } catch ( Exception ex ) {
                throw new ServletException( ex.toString() );
            }
        }
        return ( idx );
    }

    /**
     * cache a newly created index page
     * 
     * @param document
     *            set name
     * @param NavaDocIndexDOM
     */

    private synchronized void cacheIndex( String set, NavaDocIndexDOM idx ) {
        this.indexMap.put( set, idx );
      
    }

    /**
     * flushes all cached objects and re-reads the configuration
     */
    private synchronized void flush() throws ServletException {
        this.transformMap = new HashMap();
        this.slistMap = new HashMap();
        this.indexMap = new HashMap();
        this.configure();
      

    }
}