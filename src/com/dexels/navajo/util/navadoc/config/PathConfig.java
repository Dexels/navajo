package com.dexels.navajo.util.navadoc.config;

/**
 * <p>
 * Title: NavaDoc
 * </p>
 * <p>
 * Description: Navajo Web Services Automated Documentation Facility
 * configuration of path information
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

import java.io.File;
import java.util.Map;
import java.util.HashMap;

// DOM
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

// logging
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.w3c.dom.Node;
import java.util.Set;
import java.util.Iterator;

public class PathConfig {

    public static final String vcIdent = "$Id$";

    public static final Logger logger = Logger.getLogger( PathConfig.class
            .getName() );

    private String configUri;

    private Element elem;

    private File base = new File( "." );

    private Map pathMap = new HashMap();

    // --------------------------------------------------------------
    // constructors

    public PathConfig( Element e, String uri ) throws ConfigurationException {
        this.elem = e;
        this.configUri = uri;

        this.setBase();
        this.setPaths();

    } // public PathConfig( Element e, String uri )

    // ------------------------------------------------------------ public
    // methods

    /**
     * get the path property given the property's name path property always
     * include the base directory
     * 
     * @param String
     *            name of path property
     * @return path property as File
     */
    public File getPath( String name ) {
        return ( (File) this.pathMap.get( name ) );
    }

    public String toString() {
        final StringBuffer s = new StringBuffer( "<path-config>" );

        final Set keys = this.pathMap.keySet();
        final Iterator iter = keys.iterator();
        while ( iter.hasNext() ) {
            final String name = (String) iter.next();
            final File path = (File) this.pathMap.get( name );
            s.append( " " + name + "-path = '" + path.toString() + "'" );
        }

        return ( s.toString() );
    }

    // ----------------------------------------------------------- private
    // methods

    /**
     * sets the base directory for this set of paths
     * 
     * @throws ConfigurationException
     */

    private void setBase() throws ConfigurationException {
        // check the system environment for the base directory
        final String x = System
                .getProperty( NavaDocConstants.BASE_SYS_PROPERTY );
        if ( ( x != null ) && ( x.length() > 0 ) ) {
            PathConfig.logger.log( Priority.DEBUG,
                    "base directory system property set to '" + x + "'" );
            this.base = new File( x );
            if ( !( this.base.exists() && this.base.isDirectory() ) ) {
                throw new ConfigurationException( "base directory set to '" + x
                        + "' but it is not a valid directory", this.configUri );
            }
        } else {

            // see if base directory is set as an attribute of path
            final String y = this.elem
                    .getAttribute( NavaDocConstants.BASE_ATTR );
            if ( ( y != null ) && ( y.length() > 0 ) ) {
                this.base = new File( y );
                if ( !( this.base.exists() && this.base.isDirectory() ) ) {
                    throw new ConfigurationException( "base directory set to '"
                            + x + "' but it is not a valid directory",
                            this.configUri );
                }
            }
        }

    }

    /**
     * sets all the pertinent paths required for the configuration
     * 
     * @throws ConfigurationException
     */

    private void setPaths() throws ConfigurationException {
        final NodeList l = elem.getChildNodes();
        for ( int i = 0; i < l.getLength(); i++ ) {
            // skip any other type children except elements
            if ( l.item( i ).getNodeType() == Node.ELEMENT_NODE ) {
                final Element e = (Element) l.item( i );
                final String name = e.getNodeName();
                final String value = e
                        .getAttribute( NavaDocConstants.VALUE_ATTR );
                if ( ( value != null ) && ( value.length() > 0 ) ) {
                    final File path = new File( this.base, value );
                    if ( name.equals( NavaDocConstants.SVC_PATH_ELEMENT )
                            && ( !( path.exists() && path.isDirectory() ) ) ) {
                        throw new ConfigurationException(
                                "path directory for property '" + name
                                        + "' set to '" + value
                                        + "' but it is not a valid directory",
                                this.configUri );
                    } else if ( name
                            .equals( NavaDocConstants.STYLE_PATH_ELEMENT )
                            && ( !( path.exists() && path.isFile() ) ) ) {
                        throw new ConfigurationException( "path for property '"
                                + name + "' set to '" + value
                                + "' but it is not a valid file",
                                this.configUri );

                    } else if ( name
                            .equals( NavaDocConstants.TARGET_PATH_ELEMENT )
                            && !path.exists() ) {
                        if ( !path.mkdir() ) {
                            throw new ConfigurationException(
                                    "path directory for property '"
                                            + name
                                            + "' does not exist and could not create it",
                                    this.configUri );
                        }
                        PathConfig.logger.log( Priority.DEBUG,
                                "created directory '" + path + "'" );

                    } else if ( name
                            .equals( NavaDocConstants.TARGET_PATH_ELEMENT )
                            && path.exists() && !path.isDirectory() ) {
                        throw new ConfigurationException(
                                "path directory for property '"
                                        + name
                                        + "' set to '"
                                        + value
                                        + "' exists but it is not a valid directory",
                                this.configUri );

                    }
                    this.pathMap.put( name, path );
                    PathConfig.logger.log( Priority.DEBUG, "'" + name
                            + "' directory set to '" + path.toString() + "'" );

                }
            }

        }

        // check that all required paths have been provided
        final String[] required = { NavaDocConstants.SVC_PATH_ELEMENT,
                NavaDocConstants.STYLE_PATH_ELEMENT,
                NavaDocConstants.TARGET_PATH_ELEMENT };
        for ( int j = 0; j < required.length; j++ ) {
            if ( this.pathMap.get( required[j] ) == null ) {
                throw new ConfigurationException( "'" + required[j]
                        + "' path property is required", this.configUri );
            }
        }

    }

} // public class PathConfig
// EOF: $RCSfile$ //
