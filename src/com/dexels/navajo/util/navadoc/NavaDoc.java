package com.dexels.navajo.util.navadoc;

/**
 * <p>Title: NavaDoc</p>
 * <p>Description: Automated documentation utility for Navajo Web Services</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Matthew Eichler
 * @version $Revision$
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//import com.dexels.navajo.server.listener.soap.wsdl.Generate;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;
import com.dexels.navajo.util.navadoc.config.DocumentSet;
import com.dexels.navajo.util.navadoc.config.NavaDocConfigurator;

public class NavaDoc {

  public static final String vcIdent =
      "$Id$";

  private NavaDocConfigurator config = new NavaDocConfigurator();

  private ServicesList list = null;
  private NavaDocTransformer transformer = null;
  private NavaDocIndexDOM index = null;
  private File tempStyleSheet = null;
  private HashMap<String, NavaDocIndexDOM> indices = new HashMap<String, NavaDocIndexDOM>();

  /**
   * Outside mediator object which controls all the
   * inner documentation objects, looping through the
   * list of services and generating HTML content.
   * NavaDoc also keeps a nice index page of all the services
   * found
   *
   * @throws ConfigurationException when required configuration
   * objects are wrong or don't exist
   */

  public NavaDoc() throws ConfigurationException, FileNotFoundException, IOException, SAXException {

    config.configure();

    final Map setMap = config.getDocumentSetMap();
    final Set keys = setMap.keySet();
    final Iterator iter = keys.iterator();
    while (iter.hasNext()) {
      final String name = (String) iter.next();
      final DocumentSet dset = (DocumentSet) setMap.get(name);
    
      // set-up an index DOM
      try {
        this.index = new NavaDocIndexDOM(dset);
      }
      catch (ParserConfigurationException ex) {
        throw new ConfigurationException(ex.toString(),
                                         this.config.getConfigUri());
      }

      // document this directory
      this.setTransformer(dset);
      this.document(dset);

      // output index pages
     System.err.println("Ouput: " + dset.getPathConfiguration().getPath(NavaDocConstants.TARGET_PATH_ELEMENT) );
     NavaDocOutputter idxOut =
          new NavaDocOutputter(this.index,   
        		               dset.getPathConfiguration().getPath(NavaDocConstants.TARGET_PATH_ELEMENT));
     
     Iterator<String> it = indices.keySet().iterator();
     while(it.hasNext()){
    	 String key = it.next(); 
    	 NavaDocIndexDOM current = indices.get(key);
    	 current.createBreadCrumb(key);
    	 NavaDocOutputter idOut = new NavaDocOutputter(current, new File(dset.getPathConfiguration().getPath(NavaDocConstants.TARGET_PATH_ELEMENT).getAbsolutePath() + "/" + key));
     }
    }

  } // public NavaDoc()

  /**
   * Provides a count of all the web services found based
   * on the configuration.  This was useful for testing
   * purposes
   *
   * @return int count of web services
   */

  public int count() {
    return (this.list.size());
  } // public int count()

  public static void main(String[] args) throws Exception {
	  
	  NavaDoc documenter = null;
	  System.setProperty("configUri", "/home/aphilip/workspace/NavaDoc/test/config/navadoc.xml");
	  System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl"); 
	  try {
		  documenter = new NavaDoc(); 
	  } finally {
		  if ( documenter != null && documenter.tempStyleSheet != null ) {
			  documenter.tempStyleSheet.delete();
		  }
	  }
	  
  }

  public static String replaceString(String input, String oldValue, String newValue){
	     int index = input.indexOf(oldValue);
	     
	     if(index > -1){
	       String head = input.substring(0, index);
	       String tail = input.substring(index + oldValue.length());
	       return replaceString( head + newValue + tail, oldValue, newValue );
	     }else{
	       return input;
	     }
	   }
  // ----------------------------------------------------------- private methods

  private void setTransformer(final DocumentSet dset) throws
      ConfigurationException, FileNotFoundException {

	  try {
    final File sPath = dset.getPathConfiguration().getPath(NavaDocConstants.SVC_PATH_ELEMENT);
    final File styleSheet = dset.getPathConfiguration().getPath(  NavaDocConstants.STYLE_PATH_ELEMENT);
    final File tPath = dset.getPathConfiguration().getPath(NavaDocConstants.TARGET_PATH_ELEMENT);
    BufferedReader fr = new BufferedReader ( new FileReader( styleSheet ) );
    
    StringBuffer fileContent = new StringBuffer( (int) styleSheet.length() );
    String line = null;
    while ( ( line = fr.readLine() ) != null ) {
    	fileContent.append(line);
    }
    fr.close();
    String replacedContent = replaceString( fileContent.toString(), "[DOCUMENTROOT]", tPath.getAbsolutePath() );
    tempStyleSheet = File.createTempFile("stylesheet", ".xsl", styleSheet.getParentFile() );
    BufferedWriter bw = new BufferedWriter ( new FileWriter ( tempStyleSheet ) );
    bw.write( replacedContent + "\n");
    //System.err.println( replacedContent );
    bw.close();
    
    final String indent = (dset.getProperty(NavaDocConstants.INDENT) != null) ?
        dset.getProperty(NavaDocConstants.INDENT) :
        NavaDocConstants.DEFAULT_INDENT_AMOUNT;
    final String cssUri = dset.getProperty("css-uri");

   
      //System.err.println("in setTransformer(), sPath = " + sPath);
      this.transformer = new NavaDocTransformer( tempStyleSheet, sPath, indent );
      this.list = new ServicesList(sPath);

      // set optional parameters, nulls OK
      this.transformer.setProjectName(dset.getName());
      this.transformer.setCssUri(tPath.getAbsolutePath() + "/" + cssUri);

    }
    catch (Exception e) {
      e.printStackTrace( System.err );
      throw new ConfigurationException(e.getMessage(), this.config.getConfigUri());

    }

  }
  
  public File getTempStyleSheet(){
  	return this.tempStyleSheet;
  }
  
  private NavaDocIndexDOM getPrevious(String key, DocumentSet dset){
  	NavaDocIndexDOM previous = null;
  	try{
  		previous = new NavaDocIndexDOM(dset);  	
  		indices.put(key, previous);
  		
  		if(key.indexOf(File.separator) > 0){
  			String parentKey = key.substring(0, key.lastIndexOf(File.separator));
  			NavaDocIndexDOM parent = indices.get(parentKey);
  			if(parent == null){
  				parent = getPrevious(parentKey, dset);
  			}
  			parent.addSubFolderRow(key.substring(key.lastIndexOf(File.separator)+1), "",  parent.baseUri + key.substring(key.lastIndexOf(File.separator)+1) + "/index.html");
  		}
  		
  	}catch(Exception e){
  		e.printStackTrace();
  	}
		
		return previous;
  }

  /**
   * does all the work of going through the list of web
   * services and using the transformer to generate the
   * documentation.  There will be a single document combining
   * BPFL and BPCL documentation for a logical web service.
   * A link in the index page is created after each service
   * has an HTML page generated.
   */

  private void document(final DocumentSet dset) throws IOException {

    final File tPath = dset.getPathConfiguration().getPath(NavaDocConstants.
        TARGET_PATH_ELEMENT);
    final File sPath = dset.getPathConfiguration().getPath(NavaDocConstants.
        SVC_PATH_ELEMENT);
    final Iterator iter = this.list.iterator();
    
    while (iter.hasNext()) {
    	NavaDocIndexDOM currentindex = index;
      final String sname = (String) iter.next();
      
           
      /*
       * Check for subfolders, for subfolders we make new index files.
       */
      String key = sname;
      if(key.indexOf(File.separator) > 0){      	
      	key = sname.substring(0, sname.lastIndexOf(File.separator));      	
      	
      	currentindex = indices.get(key);
      	if(currentindex == null){
      		try{
      			      			
      			currentindex = new NavaDocIndexDOM(dset);
      			indices.put(key, currentindex);   
      			NavaDocIndexDOM previous = null;
      			if(key.indexOf(File.separator) > 0){
      				String previousKey = key.substring(0, key.lastIndexOf(File.separator));
      				previous = indices.get(previousKey);      				
      				
      				if(previous == null){
      					previous = getPrevious(previousKey, dset);
      				}
      			}else {
      				previous = index;
      			}
      			
      			if(key.indexOf(File.separator) > 0){
      				previous.addSubFolderRow(key.substring(key.lastIndexOf(File.separator)+1), "", currentindex.baseUri + key.substring(key.lastIndexOf(File.separator)+1) + "/index.html");
      			}else{
      				previous.addSubFolderRow(key, "", currentindex.baseUri + key + "/index.html");
      			}
      		}catch(Exception e){
      			e.printStackTrace();
      		}
      	}
      }
      
      if ( !transformer.up2dateCheck( sname, tPath.getAbsolutePath(), currentindex ) ) {
    	  this.transformer.transformWebService(sname);
    	  NavaDocOutputter outputter = new NavaDocOutputter(this.transformer, tPath);
//    	
      }
 
    }

  } // private void document()

} // public class NavaDoc
// EOF: $RCSfile$ //