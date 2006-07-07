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


import com.dexels.navajo.document.*;

public abstract class BaseNode implements java.io.Serializable{
  protected Navajo myDocRoot;
  
  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";
  //public final static String XML_ESCAPE_DELIMITERS = "";

  public abstract Map getAttributes();
  public abstract List getChildren();
  public abstract String getTagName();
  private static final int INDENT = 3;
  
  private static HashMap objectCountMap = new HashMap();
  
  private final void countObjects() {
	  
	  synchronized ( objectCountMap ) {
		  Integer objectCount = (Integer) objectCountMap.get(this.getClass().getName());
		
		  if ( objectCount == null ) {
			  objectCount = new Integer(1);
		  } else {
			  objectCount = new Integer(objectCount.intValue() + 1);
		  }
		  //System.err.println( this.getClass().getName() + " count is: " + objectCount.intValue() );
		  objectCountMap.put(this.getClass().getName(), objectCount);
	  }
  }
  
  public final static Map getObjectCountMap() {
	  return (HashMap) objectCountMap.clone();
  }
  
  public BaseNode(){
    myDocRoot = null;
    countObjects();
  }

  public BaseNode(Navajo n) {
    myDocRoot = n;
    countObjects();
  }

  public Navajo getRootDoc() {
    return myDocRoot;
  }


  public void setRootDoc(Navajo n) {
    myDocRoot = n;
  }

 public final void writeElement( Writer sw, String value ) throws IOException {
    sw.write(value);
  }
 

  public void printElement(Writer sw, int indent) throws IOException {
          String tagName = getTagName();

          for (int a = 0; a < indent; a++) {
            sw.write(" ");
        }
          writeElement( sw, "<" + tagName);
          Map map = getAttributes();

          if (map != null) {
              for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                String value = (String)map.get(element);
                /*
                 * Todo: stream!
                 */
                if (value!=null) {
//                    System.err.println("||"+value+"||");
                    String sss = XMLEscape(value);
                    writeElement( sw," "+ element + "=\"" + sss+ "\"");
                }
            }
          }
          List list = getChildren();
          boolean hasText = hasTextNode();
          boolean hasChildren = (list!=null) && list.size()>0;
          if (hasChildren && hasText) {
            throw new IllegalStateException("Can not have both children AND text");
        }
          if (!hasChildren && !hasText) {
              writeElement( sw, "/>\n");
              return;
          }
//          if (list!=null && list.size() > 0) {
            writeElement( sw, ">\n");
//          }
//          else {
//              throw new RuntimeException("WHOOOOOPS thought this did not happen");
//            writeElement( sw, "/>\n");
//          }

          for (int i = 0; i < list.size(); i++) {
              BaseNode child = (BaseNode)list.get(i);
//              if (child!=null) {
//                System.err.println("CHILD::: "+child.getClass()+ " in class: "+getClass());
//            } else {
//                System.err.println("Null child at index: "+i+ " in class: "+getClass());
//              
//            }
              child.printElement(sw,indent+INDENT);
          }
          if (hasText) {
//              System.err.println("*******************                                    Text PRESENT");
            writeText(sw);
//            System.err.println("Text written...");
          }
          if (hasText || hasChildren) {
              for (int a = 0; a < indent; a++) {
                  sw.write(" ");
              }
            writeElement( sw, "</" + tagName + ">\n");
          }

      
  }  
  
  public void write(Writer w) throws NavajoException {
      try {
        printElement(w,0);
    } catch (IOException e) {
        throw new NavajoExceptionImpl(e);
    }
  }
 

  public void write(OutputStream stream) throws NavajoException {
      try {
//          System.err.println("Writing to stream impl: "+stream.getClass());
           OutputStreamWriter osw = new OutputStreamWriter(stream,"UTF-8");
          printElement(osw,0);
          osw.flush();
//          System.err.println("Writing to stream finished");
      } catch (IOException e) {
          throw new NavajoExceptionImpl(e);
      }
   }  
   
  public boolean hasTextNode() {
      return false;
  }
  public void writeText(Writer w) throws IOException {
      // default impl. Only used for properties. 
      
  }

  /**
   * Replace all occurrences of the characters &, ', ", < and > by the escaped
   * characters &amp;, &quot;, &apos;, &lt; and &gt;
   */
  private static String XMLEscape(String s) {
    
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
  public static String XMLUnescape(String s) {
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
  
  public void finalize() {
	  synchronized ( objectCountMap ) {
		  Integer objectCount = (Integer) objectCountMap.get(this.getClass().getName());
		  if ( objectCount == null ) {
			  objectCount = new Integer(0);
		  } else {
			  int newCount =  objectCount.intValue() - 1;
			  if ( newCount < 0 ) {
				  newCount = 0;
			  }
			  objectCount = new Integer(newCount);
		  }
		  objectCountMap.put(this.getClass().getName(), objectCount);
	  }
  }
}
