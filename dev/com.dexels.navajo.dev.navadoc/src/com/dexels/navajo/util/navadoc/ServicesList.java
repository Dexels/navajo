package com.dexels.navajo.util.navadoc;



import java.io.File;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dexels.navajo.util.navadoc.NavaDoc;
import com.dexels.navajo.util.navadoc.NavaDocConstants;
import com.dexels.navajo.util.navadoc.config.ConfigurationException;

public class ServicesList extends TreeSet {

  private File path = null;
  private int rootPathNameLength;
  
  public ServicesList( File p  )
    throws ConfigurationException {
	String root = p.getAbsolutePath();
	rootPathNameLength = root.length() + 1;
	addServices(p);  
	
  } // public ServicesList()

  public void addServices( File p ) throws ConfigurationException {
	  this.path = p;

	    File[] fList = this.path.listFiles();

	    if ( fList != null ) {
	      try {
	     
	        for ( int i = 0; i < fList.length; i++ ) {
	          File f = fList[i];

	          if ( f.isFile() ) {
	            String n = f.getName();
	         
	            // this gets the base name of the web service
	            if ( n.indexOf(".xml") > -1 ) {
	            	  
	          
	              String base = f.getAbsolutePath().substring( rootPathNameLength );
	              base = base.substring(0, base.lastIndexOf(".xml"));
	              //System.err.println("Adding file: " + base );
	              this.add( base );
	            }
	          } else if ( f.isDirectory() ) {
	        	  //System.err.println(" In directory: " + f.getName());
	        	  addServices(f);
	          }
	        }
	      } catch ( Exception ree ) {
	        ConfigurationException e =
	          new ConfigurationException( ree.toString() );

	        throw ( e );
	      }
	    }

  }
  
  public static void main(String [] args) {
		  
  }
} // public class ServicesList

// EOF: $RCSfile$ //
