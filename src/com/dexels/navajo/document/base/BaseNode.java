package com.dexels.navajo.document.base;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

//import nanoxml.*;
import java.io.*;
import java.util.*;
import java.util.Map.*;

import com.dexels.navajo.document.*;

public abstract class BaseNode implements java.io.Serializable{
  protected Navajo myDocRoot;
  
  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";
  //public final static String XML_ESCAPE_DELIMITERS = "";

  public abstract Map<String,String> getAttributes();
  public abstract List<? extends BaseNode> getChildren();
  public abstract String getTagName();
  private static final int INDENT = 3;
   public BaseNode(){
    myDocRoot = null;
  }

  public BaseNode(Navajo n) {
    myDocRoot = n;
  }

  public Navajo getRootDoc() {
    return myDocRoot;
  }


  public void setRootDoc(Navajo n) {
    myDocRoot = n;
  }

 public final void writeElement(final Writer sw, String value ) throws IOException {
    sw.write(value);
  }
 

 public final void printElement(final Writer sw, int indent) throws IOException {
	 String tagName = getTagName();

	 for (int a = 0; a < indent; a++) {
		 sw.write(" ");
	 }
	 writeElement( sw, "<");
	 writeElement( sw, tagName);
	 Map<String,String> map = getAttributes();

	 if (map != null) {
		 for (Iterator<Entry<String, String>> iter = map.entrySet().iterator(); iter.hasNext();) {
			 Entry<String, String> e = iter.next();
			 String element = e.getKey();
			 String value = e.getValue();
			 /*
			  * TODO: stream!
			  */
			  if (value!=null) {
				  // optimization: Only escape properties:
				  String sss = element;
				  if (getTagName().equals("property")) {
					  if (element.equals("value")) {
						  sss = XMLEscape(value);
					  }
				  }
				  if (getTagName().equals("option")) {
					  sss = XMLEscape(value);
				  }

				  sss = XMLEscape(value);
				  writeElement( sw," ");
				  writeElement( sw, element);
				  writeElement( sw, "=\"");
				  writeElement( sw, sss);
				  writeElement( sw, "\"");
//				  System.err.println("||"+value+"||");
			  }
		 }
	 }
	 List<? extends BaseNode> list = getChildren();
	 boolean hasText = hasTextNode();
	 boolean hasChildren = (list!=null) && list.size()>0;
	 if (hasChildren && hasText) {
		 throw new IllegalStateException("Can not have both children AND text");
	 }
	 if (!hasChildren && !hasText) {
		 writeElement( sw, "/>\n");
		 return;
	 }
	 writeElement( sw, ">\n");
	 // list should not be null, but to appease the warnings
	 if(list!=null) {
		 for (int i = 0; i < list.size(); i++) {
			 BaseNode child = list.get(i);
			 child.printElement(sw,indent+INDENT);
		 }
	 }
	 if (hasText) {
		 writeText(sw);
	 }
	 if (hasText || hasChildren) {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 writeElement( sw, "</");
		 writeElement( sw,tagName);
		 writeElement( sw,">\n");
	 }


 }  
  
  public void write(final Writer w) throws NavajoException {
      try {
        printElement(w,0);
    } catch (IOException e) {
        throw new NavajoExceptionImpl(e);
    }
  }
 

  public void write(final OutputStream stream) throws NavajoException {
	  try {
		  OutputStreamWriter osw = new OutputStreamWriter(stream,"UTF-8");
		  printElement(osw,0);
		  osw.flush();
	  } catch (IOException e) {
		  throw new NavajoExceptionImpl(e);
	  }
   }  
   
  public boolean hasTextNode() {
      return false;
  }
  @SuppressWarnings("unused")
public void writeText(Writer w) throws IOException {
      // default impl. Only used for properties. 
       
  }

  /**
   * Replace all occurrences of the characters &, ', ", < and > by the escaped
   * characters &amp;, &quot;, &apos;, &lt; and &gt;
   */
  private static final String XMLEscape(String s) {
    
    boolean contains = false;
    for ( int i = 0; i < XML_ESCAPE_DELIMITERS.length(); i++ ) {
        if ( s.indexOf( XML_ESCAPE_DELIMITERS.charAt(i) ) != -1 ) {
            contains = true;
        }
    }
    
    if ( ! contains ) {
        return s;
    }
    
      if ((s == null) || (s.length() == 0)) {
          return s;
      }

      StringTokenizer tokenizer = new StringTokenizer(s, XML_ESCAPE_DELIMITERS, true);
      StringBuffer    result    = new StringBuffer();

      while (tokenizer.hasMoreElements()) {
          String substring = tokenizer.nextToken();

          if (substring.length() == 1) {
              switch (substring.charAt(0)) {

              case '&' :
                  result.append("&amp;");
                  break;

              //case '\'' :
              //    result.append("&apos;");
              //    break;

              case ';' :
                  result.append("\\;");
                  break;

              case '<' :
                  result.append("&lt;");
                  break;

              case '>' :
                  result.append("&gt;");
                  break;

              case '\"' :
                  result.append("&quot;");
                  break;

//              case '\n' :
//                  result.append("\\n");
//                  break;

              default :
                  result.append(substring);
              }
          }
          else {
              result.append(substring);
          }
      }

      return result.toString();
  }

  /**
   * Replace all occurrences of the escaped characters &amp;, &quot;, &apos;,
   * &lt; and &gt; by the unescaped characters &, ', ", < and >.
   */
  public static final String XMLUnescape(String s) {
      if ((s == null) || (s.length() == 0)) {
          return s;
      }

      int    offset;
      int    next;
      String result;

      // filter out all escaped ampersands
      offset = 0;
      result = "";

      while ((next = s.indexOf("&amp;", offset)) >= 0) {
          result += s.substring(offset, next) + "&";
          offset = next + "&amp;".length();
      }

      result += s.substring(offset, s.length());    // characters after last &
      s      = result;

      // filter out all escaped double quotes
      offset = 0;
      result = "";

      while ((next = s.indexOf("&quot;", offset)) >= 0) {
          result += s.substring(offset, next) + "\"";
          offset = next + "&quot;".length();
      }

      result += s.substring(offset, s.length());    // characters after last "
      s      = result;

      // filter out all escaped single quotes
      offset = 0;
      result = "";

      while ((next = s.indexOf("&apos;", offset)) >= 0) {
          result += s.substring(offset, next) + "\'";
          offset = next + "&apos;".length();
      }

      result += s.substring(offset, s.length());    // characters after last "
      s      = result;

      // filter out all escaped less than characters
      offset = 0;
      result = "";

      while ((next = s.indexOf("&lt;", offset)) >= 0) {
          result += s.substring(offset, next) + "<";
          offset = next + "&lt;".length();
      }

      result += s.substring(offset, s.length());    // characters after last <
      s      = result;

      // filter out all escaped greater than characters
      offset = 0;
      result = "";

      while ((next = s.indexOf("&gt;", offset)) >= 0) {
          result += s.substring(offset, next) + ">";
          offset = next + "&gt;".length();
      }

      result += s.substring(offset, s.length());    // characters after last >
      s      = result;

      // filter out all escaped newlines
      offset = 0;
      result = "";

      while ((next = s.indexOf("\\n", offset)) >= 0) {
          result += s.substring(offset, next) + "\n";
          offset = next + "\\n".length();
      }

      result += s.substring(offset, s.length());    // characters after last newline

       // filter out all escaped ;'s
      offset = 0;
      result = "";

      while ((next = s.indexOf("\\;", offset)) >= 0) {
          result += s.substring(offset, next) + ";";
          offset = next + "\\;".length();
      }

      result += s.substring(offset, s.length());    // characters after last newline

      return result;
  }
  

}
