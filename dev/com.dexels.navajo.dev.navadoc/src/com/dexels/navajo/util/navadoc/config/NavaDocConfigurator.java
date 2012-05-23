package com.dexels.navajo.util.navadoc.config;

/**
 * <p>
 * Title: NavaDocConfigurator
 * </p>
 * <p>
 * Description: convenience class for configuration of of the NavaDoc
 * documentation automation runner
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Matthew Eichler
 * @version $Revision$
 */

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.util.navadoc.NavaDocConstants;

public class NavaDocConfigurator {

    public static final String vcIdent = "$Id$";

    private String configUri = System.getProperty( "configUri" );

//    private File targetPath = null;

    private Document configDOM = null;

    private Element navConf = null;

    private Element loggerConfig = null;

//    private NodeList docProps = null;

    private Map setMap = new HashMap();

    private DocumentSet defaultSet = null;

    // --------------------------------------------------------------
    // constructors

    public NavaDocConfigurator() {
    }

    public NavaDocConfigurator( final String uri ) {
        this.configUri = uri;
    }

    // ------------------------------------------------------------ public
    // methods

    public void configure() throws ConfigurationException {

        // get configuration as DOM
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            this.configDOM = dBuilder.parse( this.configUri );
        } catch ( Exception e ) {
            ConfigurationException ce = new ConfigurationException( e
                    .toString(), this.configUri );

            throw ( ce );
        }

        // get NavaDoc configuration from DOM
        this.navConf = (Element) this.configDOM.getElementsByTagName(
                NavaDocConstants.CONFIGURATION_ELEMENT ).item( 0 );
        if ( this.navConf == null ) {
            throw new ConfigurationException(
                    "configuration does not have a valid '"
                            + NavaDocConstants.CONFIGURATION_ELEMENT
                            + "' element'", this.configUri );
        }

        final NodeList sList = this.navConf
                .getElementsByTagName( NavaDocConstants.DOCSET_ELEMENT );
        if ( ( sList == null ) || ( sList.getLength() == 0 ) ) {
            throw new ConfigurationException( "must define at least one '"
                    + NavaDocConstants.DOCSET_ELEMENT + "' element'",
                    this.configUri );
        }

        for ( int i = 0; i < sList.getLength(); i++ ) {
            if ( sList.item( i ).getNodeType() != Node.ELEMENT_NODE ) {
                throw new ConfigurationException( "malformed '"
                        + NavaDocConstants.DOCSET_ELEMENT + "' element'",
                        this.configUri );

            }
            final DocumentSet set = new DocumentSet( (Element) sList.item( i ),
                    this.configUri );
            this.setMap.put( set.getName(), set );
            if ( ( i == 0 ) || set.isDefault() ) {
                this.defaultSet = set;
            }
           
        }

    } // configure()

    /**
     * @return the default DocumentSet. This will be the first one defined if no
     *         others are flagged as the default. Typically this will be used by
     *         the Web documenter.
     */
    public DocumentSet getDefaultSet() {
        return ( this.defaultSet );
    }

    // ------------------------------------------------------------ public
    // methods

    /**
     * @return a Map containing all the configured DocumentSets
     */
    public Map getDocumentSetMap() {
        return ( this.setMap );
    }

    /**
     * @return the configuration URI that is the source of this configuration
     */

    public String getConfigUri() {
        return ( this.configUri );
    }

    // ----------------------------------------------------------- private
    // methods

} // public class NavaDocConfigurator
// EOF: $RCSfile$ //
