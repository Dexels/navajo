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
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public abstract class BaseNode implements java.io.Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = -8900993539784890274L;

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
 

 public void printElement(final Writer sw, int indent) throws IOException {
	 final boolean isOpen = printStartTag(sw, indent,false);
	 if(!isOpen) {
		 return;
	 }
	 printBody(sw, indent);
	 printCloseTag(sw, indent);
 }
 
public void printCloseTag(final Writer sw, int indent) throws IOException {
	String tagName = getTagName();
	 for (int a = 0; a < indent; a++) {
		 sw.write(" ");
	 }
	 writeElement( sw, "</");
	 writeElement( sw,tagName);
	 writeElement( sw,">\n");
}
public void printBody(final Writer sw, int indent) throws IOException {
	 List<? extends BaseNode> list = getChildren();
	 boolean hasText = hasTextNode();
	 boolean hasChildren = (list!=null) && list.size()>0;
	 if (hasChildren && hasText) {
		 throw new IllegalStateException("Can not have both children AND text");
	 }
//	 if (!hasChildren && !hasText) {
//		 writeElement( sw, "/>\n");
//		 return;
//	 }
//	 writeElement( sw, ">\n");
//	 // list should not be null, but to appease the warnings
	 if(list!=null) {
		 for (int i = 0; i < list.size(); i++) {
			 BaseNode child = list.get(i);
			 child.printElement(sw,indent+INDENT);
		 }
	 }
	 if (hasText) {
		 writeText(sw);
	 }
}
public boolean printStartTag(final Writer sw, int indent,boolean forceDualTags) throws IOException {
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
//				  logger.info("||"+value+"||");
			  }
		 }
	 }
	 List<? extends BaseNode> list = getChildren();
	 boolean hasText = hasTextNode();
	 boolean hasChildren = (list!=null) && list.size()>0;
	 if (hasChildren && hasText) {
		 throw new IllegalStateException("Can not have both children AND text");
	 }
	 if (!hasChildren && !hasText && !forceDualTags) {
		 writeElement( sw, "/>\n");
		 return false;
	 }
	 writeElement( sw, ">\n");
	return true;
}
 
 // {tml : {documentImplementation :  SAXP, methods : {}}
 
 public final void printElementJSON(final Writer sw, boolean arrayElement) throws IOException {
		String tagName = getTagName();
		if ("tml".equals(tagName)) {
			writeElement(sw, "{");
		}
		if (!arrayElement) {
			writeElement(sw, "\"" + tagName + "\"");
			writeElement(sw, " : {");
		} else {
			writeElement(sw, "{");
		}
		Map<String, String> map = getAttributes();
		if (map != null) {
			for (Iterator<Entry<String, String>> iter = map.entrySet().iterator(); iter.hasNext();) {
				Entry<String, String> e = iter.next();
				String element = e.getKey();
				String value = e.getValue();
				/*
				 * TODO: stream!
				 */
				if (value != null) {
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
					writeElement(sw, " ");
					writeElement(sw, "\"" + element + "\"");
					writeElement(sw, " : ");
					writeElement(sw, "\"" + sss + "\"");
					if (iter.hasNext()) {
						writeElement(sw, ", ");
					}
					// logger.info("||"+value+"||");
				}
			}
		}
		List<? extends BaseNode> list = getChildren();
		boolean hasText = hasTextNode();
		boolean hasChildren = (list != null) && list.size() > 0;
		if(list==null) {
			list = Collections.emptyList();
		}
		if (hasChildren && hasText) {
			throw new IllegalStateException("Can not have both children AND text");
		}
		if (!hasChildren && !hasText) {
			writeElement(sw, "}");
			return;
		}
		// group the childred to see if JSONArrays must be created
		Map<String, List<BaseNode>> groupedChildren = new HashMap<String,List<BaseNode>>();
		for (int j = 0; j < list.size(); j++) {
			BaseNode child = list.get(j);
			String key = child.getTagName();
			if (!groupedChildren.containsKey(key)) {
				groupedChildren.put(key, new ArrayList<BaseNode>());
			}
			List<BaseNode> l = groupedChildren.get(key);
			l.add(child);
		}
		// Now all children are grouped.
		Iterator<String> it = groupedChildren.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			list = groupedChildren.get(key);
			// list should not be null, but to appease the warnings
			if (list != null) {
				if (list.size() > 1) {
					// it is an array
					if (map != null) {
						writeElement(sw, ", ");
					}
					writeElement(sw, "\"" + key + "\" : [");
					for (int i = 0; i < list.size(); i++) {
						BaseNode child = list.get(i);
						if (i > 0) {
							writeElement(sw, ", ");
						}
						child.printElementJSON(sw, true);
					}
					writeElement(sw, "]");
				} else {
					BaseNode child = list.get(0);
					if (map != null) {
						writeElement(sw, ", ");
					}
					child.printElementJSON(sw, false);
				}
			}
		}
		if (hasText) {
			writeText(sw);
		}
		if (!arrayElement && (hasText || hasChildren)) {
			writeElement(sw, "}");
		}
		writeElement(sw, "}");
	}

	/**
	 * @param sw The writer to write to 
	 */
	public void printElementJSONTypeless(final Writer sw) throws IOException  {
		getAttributes();
		getChildren();
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
            OutputStreamWriter osw = new OutputStreamWriter(stream, "UTF-8");
            printElement(osw, 0);
            osw.flush();
        } catch (IOException e) {
            throw new NavajoExceptionImpl(e);
        }
    } 
   
  public boolean hasTextNode() {
      return false;
  }
  
  /**
 * @param w the writer to write to 
 * @throws IOException 
 */
public void writeText(Writer w) throws IOException  {
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
