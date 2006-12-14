package com.dexels.navajo.util.navadoc;


/**
 * <p>Title: NavaDocTransformer</p>
 * <p>Description: performs the XSLT transformation on Navajo Web Services
 * (does the nitty-gritty work of the NavaDoc facility)</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author Matthew Eichler
 * @version $Id$
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.server.listener.soap.wsdl.Generate;


public class NavaDocTransformer extends NavaDocBaseDOM {

  public static final String vcIdent = "$Id$";

  // paths
  private File styleSheetPath = null;
  private File servicesPath = null;

  // current service we last worked on
  private String serviceName = null;

  // XML transformation
  private static DocumentBuilder dBuilder;
  private TransformerFactory tFactory = TransformerFactory.newInstance();
  protected Transformer transformer = null;

  // notes/description of the current web service
  private String notes = null;

  // error information
  private String errorText = null;

  // indent while transforming?
  private Integer indent = new Integer( 0 );

  public NavaDocTransformer( File styPath, File svcPath )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    super();

    NavaDocTransformer.dBuilder =
        ( DocumentBuilderFactory.newInstance() ).newDocumentBuilder();

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;

    // get an XSLT transformer for our style sheet
    System.err.println("stylesheetpath: " + this.styleSheetPath);
    this.transformer =
        tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );
   
    this.dumpProperties();

  } // public NavaDocTransformer

  public NavaDocTransformer( File styPath, File svcPath, String ind )
    throws TransformerConfigurationException,
      ParserConfigurationException {

    super();

    NavaDocTransformer.dBuilder =
        ( DocumentBuilderFactory.newInstance() ).newDocumentBuilder();

    // path housekeeping
    this.styleSheetPath = styPath;
    this.servicesPath = svcPath;

    // set indentation
    this.setIndent( ind );

    // get an XSLT transformer for our style sheet
    System.err.println("stylesheetpath: " + this.styleSheetPath);
    this.transformer =
        tFactory.newTransformer( new StreamSource( this.styleSheetPath ) );

    this.setOutputProperties();

  } // public NavaDocTransformer [2]

  /**
   * a convenience method for directly accessing the
   * XML transformer object
   *
   * @return the XML transformer
   */

  public Transformer getTransformer() {
    return ( this.transformer );
  }

  /**
   * Returns the notes/description of the current
   * transformed web service.  The notes may occur in either
   * of the BPFL or BPCL scripts; if both exist, the
   * former takes precedence over the later
   *
   * @return String notes/description of web service
   */


  /**
   * used for creating an index on the fly for NavaDocWeb
   * @param String name of web service
   * @return any notes data as a String
   * @throws IOException
   * @throws SAXException
   */
  
  public String getNotes( final String sname ) throws IOException, SAXException {
	  
	  File sFile = new File(
		        this.servicesPath  + File.separator + sname + "." + NavaDocConstants.NAVASCRIPT_EXT );
	  
	    final Document sDoc = NavaDocTransformer.dBuilder.parse( sFile );
	    final Element root = sDoc.getDocumentElement();
	    return ( root.getAttribute( NavaDocConstants.NOTES_ATTR ) );
	  }
 
  
  public String getNotes( final File sFile ) throws IOException {
 
	try {
	    final Document sDoc = NavaDocTransformer.dBuilder.parse( sFile );
	    final Element root = sDoc.getDocumentElement();
	    return ( root.getAttribute( NavaDocConstants.NOTES_ATTR ) );
	} catch (SAXException e) {
		return "";
	}
  }

  /**
   * sets indentation property based on any positive hints
   * from the configuration
   * @param indentation property as a String
   */

  public void setIndent( String i ) {
    this.indent = new Integer(Integer.parseInt(i));
  }

  /**
   * @return whether transformer should indent if necessary as boolean
   */

  public boolean shouldIndent() {
    return ( this.indent.intValue() > 0 );
  }

  /**
   * Using the transformer based on the given style-sheet,
   * transforms all related BPFL and BPCL into a single document.
   * This method can be called repeatedly as it generated a
   * new DOM for each transformation result
   *
   * @param logical name of web service as a string (no file extensions
   * please)
   */

  public boolean up2dateCheck (  final String sname, String outputPath, NavaDocIndexDOM index ) throws IOException {
	  
	  long scriptModified = -1;
	  long docModified = -1;
	  File sFile = new File(
		        this.servicesPath  + File.separator + sname + "." + NavaDocConstants.NAVASCRIPT_EXT );
	  if ( sFile.exists() ) {
		  scriptModified =  sFile.lastModified();
	  } 
	  File docFile = new File( outputPath  + File.separator + sname + ".html" );
	  if ( docFile.exists() ) {
		  docModified = docFile.lastModified();
	  }
	  //System.err.println("in up2dateCheck(), sFile is " + sFile.getAbsolutePath() + ", docFile is " + docFile.getAbsolutePath());
	  if ( scriptModified == -1 ) {
		  System.err.println("Could not find script " + sname + ", deleting doc");
		  docFile.delete();
		  return true;
	  }
	  if ( docModified == -1 ) {
		  index.addEntry(sname, getNotes(sFile));
		  return false;
	  }
	 
	  if ( scriptModified > docModified ) {
		  index.addEntry(sname, getNotes(sFile));
		  return false;
	  }
	  index.addEntry(sname, getNotes(sFile));
	  return true;
	  
  }
  
  private static String rewriteComment( String in, int tslBegin, int tslEnd ) {
	  
	StringBuffer result = new StringBuffer ( in.length() );
	
	int beginIndx;
	int endIndx;
	if ( (beginIndx = in.indexOf("<!--")) == -1 ) {
		return in;
	} else {
		if ( in.indexOf("$RCSfile", beginIndx) != -1) {
			return in;
		}
		if ( beginIndx < tslBegin || beginIndx > tslEnd ) {
			return in;
		}
	}
	endIndx = in.indexOf("-->", beginIndx);
	if ( endIndx < tslBegin || endIndx > tslEnd ) {
		return in;
	}
	String commentString = in.substring(beginIndx+4, endIndx);
	commentString = NavaDoc.replaceString( commentString, "<", "&lt;");
	commentString = NavaDoc.replaceString( commentString, ">", "&gt;");
	commentString = NavaDoc.replaceString( commentString, "\"", "&quot;");
	//commentString = NavaDoc.replaceString( commentString, "\\", "\\\\");
	
	result.append(in.substring(0, beginIndx));
	result.append("<comment value=\"");
	result.append( commentString );
	result.append("\"/>");
	result.append(in.substring(endIndx + 3));
	
	return rewriteComment( result.toString(), tslBegin, tslEnd );
	
  }
  
  public void transformWebService( final String sname ) {

    this.setOutputProperties();

    // new web service, new document
    this.newDocument();

    this.baseName = sname;
    this.setHeaders();
    this.addBody( "document-body" );

    final Element span = this.dom.createElement( "span" );

    span.setAttribute( "class", "navascript" );

    final File sFileOrig = new File( this.servicesPath  + File.separator + sname + "." + NavaDocConstants.NAVASCRIPT_EXT );
    
    System.err.println("sFileOrig: "  + sFileOrig.getAbsolutePath());
    File tempsFile = null;
  
    
    try {
    	
    	// Preprocessing: rewrite <!-- --> construction to <comment value=""/>
    	BufferedReader fr = new BufferedReader ( new FileReader( sFileOrig ) );
    	StringBuffer fileContent = new StringBuffer( (int) sFileOrig.length() );
    	String line = null;
    	while ( ( line = fr.readLine() ) != null ) {
    		fileContent.append(line + "\n");
    	}
    	fr.close();
    	String replacedContent = rewriteComment( fileContent.toString(), fileContent.toString().indexOf("<tsl"), 
    				fileContent.toString().indexOf("/tsl>"));
    	tempsFile = new File(sFileOrig.getParentFile(), sname.replace('/','_') + "_temp.xml" );
    	BufferedWriter bw = new BufferedWriter ( new FileWriter ( tempsFile ) );
    	bw.write( replacedContent );
    	bw.close();
    	
    	final Document sDoc = NavaDocTransformer.dBuilder.parse( tempsFile );
    	Element scriptName = sDoc.createElement("script");
    	sDoc.getDocumentElement().appendChild(scriptName);
    	
    	
    	scriptName.setAttribute("name", NavaDoc.replaceString( sFileOrig.getName(), ".xml", "" ) );
    	DOMSource domSrc = new DOMSource( sDoc );
    	//TransformerFactory.newInstance().newTransformer().transform( domSrc, new StreamResult(System.err));
    	
    	
    	DOMResult domRes = new DOMResult( span );
    	//StreamResult sr = new StreamResult(new File("/home/arjen/aap"));
    	
    	this.errorText = null;
    	//System.err.println("About to generate doc for: " + sname);
    	
    	this.transformer.transform( domSrc, domRes );
    	this.body.appendChild( span );
    	
    	final Element root = sDoc.getDocumentElement();
    	this.notes = root.getAttribute( NavaDocConstants.NOTES_ATTR );
    	
    	// Generate INPUT/OUTPUT PARTS:
    	 
    	newInputOutputDocuments();
    	Generate gen = new Generate();
    	
    	// Generate input TML:
    	inDoc = gen.getInputPart( this.servicesPath  + File.separator + sname );
    	
    	final Element inputNavajo = this.domIn.createElement( "span" );
    	this.domIn.getDocumentElement().appendChild( inputNavajo );
    	
    	Document inSrc = (Document) inDoc.getMessageBuffer();
    	Element scriptTag = inSrc.createElement("script");
    	inSrc.getDocumentElement().appendChild(scriptTag);
    	scriptTag.setAttribute("name", NavaDoc.replaceString( sFileOrig.getName(), ".xml", "" ) );
    	scriptTag.setAttribute("type", "in");
    	
    	DOMSource domSrcIn = new DOMSource( inSrc.getDocumentElement() );
    	DOMResult domResIn = new DOMResult( inputNavajo );
    	this.transformer.transform( domSrcIn, domResIn );
    	
    	// Generate output TML:
    	outDoc = gen.getOutputPart( this.servicesPath  + File.separator + sname );
    	
    	final Element outputNavajo = this.domOut.createElement( "span" );
    	this.domOut.getDocumentElement().appendChild( outputNavajo );
    	
    	Document outSrc = (Document) outDoc.getMessageBuffer();
    	scriptTag = outSrc.createElement("script");
    	outSrc.getDocumentElement().appendChild(scriptTag);
    	scriptTag.setAttribute("name", NavaDoc.replaceString( sFileOrig.getName(), ".xml", "" ) );
    	scriptTag.setAttribute("type", "out");
    	
    	DOMSource domSrcOut = new DOMSource( ((Document) outDoc.getMessageBuffer()).getDocumentElement() );
    	DOMResult domResOut = new DOMResult( outputNavajo );
    	this.transformer.transform( domSrcOut, domResOut );
    	
    	
    } catch ( Exception e ) {
    	
    	this.errorText = "unable to transform source '" + sFileOrig + "': " + e;
    	//System.err.println(errorText);
    	e.printStackTrace( System.err );
    	
    	this.setErrorText( body );
    	
    	
    } finally {
    	if ( tempsFile != null ) {
    		tempsFile.delete();
    	}
    }


   

  } // public void transformWebService()

  // debugging
  public void dumpProperties() {
    Properties props = this.transformer.getOutputProperties();
    Enumeration enm = props.propertyNames();

    while ( enm.hasMoreElements() ) {
      String s = (String) enm.nextElement();

     
    }
  } // public void dumpProperties()

  // ----------------------------------------------------  private methods

  // sets the error text into the document
  private void setErrorText( Element body ) {

    Element p = this.dom.createElement( "p" );

    p.setAttribute( "class", "error" );
    Text t = this.dom.createTextNode( this.errorText );

    p.appendChild( t );
    this.body.appendChild( p );

  } // private void setErrorText()

  // over-ridden setHeaders method
  private void setHeaders() {
    String titl =
      ( ( ( this.projectName != null ) &&
          ( this.projectName.length() > 0 ) ? this.projectName : "Web" )
        + " Service: " ) + this.serviceName;

    this.setHeaders( titl );
  } // private void setHeaders()


  // sets all necessary output parameters
  private void setOutputProperties() {
//    this.transformer.setOutputProperty(
//      NavaDocConstants.OUTPUT_METHOD_PROP, NavaDocConstants.OUTPUT_METHOD_VALUE );
//
//    // set ident values
//    this.transformer.setOutputProperty( NavaDocConstants.INDENT,
//      ( this.indent.intValue() > 0 ? "true" : "false" ) );
//    this.transformer.setOutputProperty( NavaDocConstants.INDENT_AMOUNT,
//       this.indent.toString() );
    // this.dumpProperties();
  }

  public static void main ( String [] args ) {
	  
	  String ex = "<tsl><message name=\"aap\"><!-- <property name=\"aap\"/> Dit is commentaar --></message></tsl>";
	  System.err.println( rewriteComment(ex , ex.indexOf("<tsl"), ex.indexOf("/tsl>") ));
	  
  }
} // public class NavaDocTransformer

// EOF: $RCSfile$ //
