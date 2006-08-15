package com.dexels.navajo.document.nanoimpl;

import java.util.*;
import java.io.*;
import java.net.*;

import com.dexels.navajo.document.*;


import org.dexels.utils.Base64;

import com.dexels.navajo.document.base.*;
import com.dexels.navajo.document.types.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>c
 * @author Frank Lyaruu
 * @version 1.0
 */

public final class PropertyImpl
    extends BasePropertyImpl
    implements Property, Comparable, NanoElement {
 

  
  public PropertyImpl(Navajo n, String name, String type, String value, int i,
                      String desc, String direction) {
    super(n,name,type,value,i,desc,direction);
  }

  public PropertyImpl(Navajo n, String name, String type, String value, int i,
                      String desc, String direction, String subType) {
    super(n,name,type,value,i,desc,direction,subType);
  }


  public PropertyImpl(Navajo n, String name, String cardinality, String desc,
                      String direction) {
    super(n,name,cardinality,desc,direction);
  }

 
  public PropertyImpl(Navajo n, String name) {
    super(n,name);
  }

//  public final void setValue(InputStream is) {
//
//  }
//
//  private final void copyResource(OutputStream out, InputStream in) throws IOException{
//      BufferedInputStream bin = new BufferedInputStream(in);
//      BufferedOutputStream bout = new BufferedOutputStream(out);
//      byte[] buffer = new byte[1024];
//      int read;
//      while ((read = bin.read(buffer)) > -1) {
//        bout.write(buffer,0,read);
//      }
//      bin.close();
//      bout.flush();
//      bout.close();
//  }


  public final XMLElement toXml(XMLElement parent) {
  	return toXml(parent, false, null);
  }

  public final XMLElement toXml(XMLElement parent, boolean condense, String method) {
    XMLElement x = new CaseSensitiveXMLElement();
    x.setName("property");
    x.setAttribute("name", myName);

    // Check if cloned (if parent = null assume clone), if so copy length, description and cardinality.
    if (parent == null) {
    	x.setAttribute("length", length+"");
    	if (description != null) {
    		x.setAttribute("description", description);
    	}
    	if (cardinality != null) {
    		x.setAttribute("cardinality", cardinality);
    	}
    }

    if (myValue != null) {
//      if (Date.class.isInstance(myValue)) {
//        x.setAttribute("value",dateFormat1.format((Date)myValue));
//      } else {
//      }
    }
    if (subType != null) {
      x.setAttribute(PROPERTY_SUBTYPE, subType);
    }

    /** @todo Refine this a bit. Should be checked for every attribute, with precedence to this one */
//     subType = serializeSubtypes();
//     if (subType != null) {
//       System.err.println("Subtype found!");
//       x.setAttribute("subtype", subType);
//     }

    try {
// Always set type:
        x.setAttribute("type", type);
       
        if (myBinary != null) {
        	x.setContent( myBinary.getBase64() );
        }
        if (myValue != null) {
            x.setAttribute("value", (String) myValue);
          }

    if (definitionProperty == null || definitionProperty.getAllSelections().size() == 0 ) {
 //      System.err.println("Serializing property. No definition");

      if (direction != null) {
        x.setAttribute("direction", direction);
      }
      else {
        x.setAttribute("direction", "in");
      }

      if (description != null && !condense) {
        x.setAttribute("description", description);
      }

      if (length != -1 && !condense) {
        x.setAttribute("length", length + "");

      }

      if (cardinality != null) {
        x.setAttribute("cardinality", cardinality);
      }

      for (int i = 0; i < selectionList.size(); i++) {
        SelectionImpl s = (SelectionImpl) selectionList.get(i);
        if (!condense || s.isSelected()) {
        	x.addChild(s.toXml(x));
        }
      }

    }
    else {

      /** @todo Watch out here. Just because there is a definition message,
       * it seems to assume that a definitionProperty is present. This is
       * very likely, but it results in a null pointer exception when it is
       * not. I am not sure if a silent ignore is wise, maybe just throw a
       * clearer runtime exception. */

//      System.err.println("Serializing property. With definition");
      if (myValue != null) {
        if (definitionProperty.getValue() != null &&
            !definitionProperty.getValue().equals(getValue())) {
          x.setAttribute("value", (String) myValue);

        }
        if (definitionProperty.getValue() == null) {
          x.setAttribute("value", (String) myValue);
        }

      }

        ArrayList al = getAllSelectedSelections();
        //System.err.println("# of selected selections: " + al.size());
        for (int i = 0; i < al.size(); i++) {

          SelectionImpl s = (SelectionImpl) al.get(i);
          //System.err.println("SELECTION: "+s.toXml(x).toString());
          s.setSelected(true);

          /** @todo Beware: Isn't this very strange?  */
          x.addChild(s.toXml(x));

        }
      }
    }
    catch (NavajoException ex) {
       ex.printStackTrace();
     }

    //System.err.println("Result: " + x.toString());
    return x;
  }

  public final void fromXml(XMLElement e) {
    fromXml(e, null);
  }

  public final void fromXml(XMLElement e, MessageImpl parentArrayMessage) {
    String sLength = null;
    myName = (String) e.getAttribute(Property.PROPERTY_NAME);
    myValue = (String) e.getAttribute(Property.PROPERTY_VALUE);
    subType = (String) e.getAttribute(PROPERTY_SUBTYPE);
    description = (String) e.getAttribute(Property.PROPERTY_DESCRIPTION);
    direction = (String) e.getAttribute(Property.PROPERTY_DIRECTION);
    type = (String) e.getAttribute(Property.PROPERTY_TYPE);
    sLength = (String) e.getAttribute(Property.PROPERTY_LENGTH);
    Integer plength = null;
    try {
      if (sLength != null) {
        length = Integer.parseInt(sLength);
        plength = new Integer(length);
      }
    }
    catch (Exception e1) {
      System.err.println("ILLEGAL LENGTH IN PROPERTY " + myName + ": " +
                         sLength);
    }

    definitionProperty = null;

    if (parentArrayMessage != null) {

      definitionProperty = parentArrayMessage.getPropertyDefinition(myName);

      if (definitionProperty != null) {
        if (description == null || "".equals(description)) {
          description = definitionProperty.getDescription();
        }
        if (direction == null || "".equals(direction)) {
          direction = definitionProperty.getDirection();
        }
        if (type == null || "".equals(type)) {
          type = definitionProperty.getType();
        }
        if (plength == null) {
          length = definitionProperty.getLength();
        }
        if (subType == null) {
          if (definitionProperty.getSubType() != null) {
            setSubType(definitionProperty.getSubType());
          }
          else {
            subType = null;
          }
        }
        else {
          if (definitionProperty.getSubType() != null) {
            /**
                 * Concatenated subtypes. The if the same key of a subtype is present
             * in both the property and the definition property.
             */
            setSubType(definitionProperty.getSubType() + "," + subType);
          }
        }

        if (myValue == null || "".equals(myValue)) {
          myValue = definitionProperty.getValue();
        }
      }
    }

    if (subType == null &&
        NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
      setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
    }
    else {
      setSubType(subType);
    }

    if (type == null && parentArrayMessage != null) {
      System.err.println("Found undefined property: " + getName());
    }

    isListType = (type != null && type.equals(Property.SELECTION_PROPERTY));
    if (isListType) {
      cardinality = (String) e.getAttribute(Property.PROPERTY_CARDINALITY);
      if (cardinality == null) {
        cardinality = definitionProperty.getCardinality();
      }
      type = Property.SELECTION_PROPERTY;
      try {
        if (definitionProperty == null || definitionProperty.getAllSelections().size() == 0) {
          cardinality = (String) e.getAttribute("cardinality");
          for (int i = 0; i < e.countChildren(); i++) {
            XMLElement child = (XMLElement) e.getChildren().elementAt(i);
            SelectionImpl s = (SelectionImpl) NavajoFactory.getInstance().createSelection(myDocRoot, "", "", false);
            s.fromXml(child);
            s.setParent(this);
            this.addSelection(s);
          }
        }
        else { // There is a definition property with defined selections(!)
          ArrayList l = definitionProperty.getAllSelections();
          for (int i = 0; i < l.size(); i++) {
            SelectionImpl s = (SelectionImpl) l.get(i);
            SelectionImpl s2 = (SelectionImpl) s.copy(getRootDoc());
            addSelection(s2);
          }
          for (int j = 0; j < e.countChildren(); j++) {
            XMLElement child = (XMLElement) e.getChildren().elementAt(j);
            String val = (String) child.getAttribute("value");
            //System.err.println(">>>>>>>>>>>>>>>>>>>>>> Attempting to select value: " + val);
            if (val != null) {
              setSelectedByValue(val);
            }
          }

        }

      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }

    }
    if (type == null) {
      type = Property.STRING_PROPERTY;
    }
    
    if (BINARY_PROPERTY.equals(type) ) {
    	if (myValue==null) {
    		String content = e.getContent();
			if (content!=null ) {
            	try {
            		System.err.println("Nano. Parsing content. size: "+content.length());
        			myBinary = new Binary(new StringReader(content));
        			
            	} catch (IOException e1) {
        			e1.printStackTrace();
        		}
			} else {
				System.err.println("NULL binary!");
			}
		} else {  
	    	try {
        		System.err.println("Nano. Parsing binary value attribute. size: "+myValue.length());
				myBinary = new Binary(new StringReader(myValue));
				myValue = null;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    } else {
        try {
            setValue(PropertyTypeChecker.getInstance().verify(this, myValue));
        } catch (PropertyTypeException e1) {
              e1.printStackTrace();
        } 
    }

  }

  public final Object getRef() {
    return toXml(null);
  }

  public Object clone() {
    PropertyImpl pi = new PropertyImpl(myDocRoot, getName());
    pi.fromXml(toXml(null));
    return pi;
  }

  public Object clone(final String newName) {
    throw new java.lang.UnsupportedOperationException(
        "Method clone( String ) not yet implemented.");
  }

  public void addExpression(ExpressionTag e) {
    throw new java.lang.UnsupportedOperationException(
        "Method addExpression() not yet implemented.");
  }

public XMLElement toXml() {
    return toXml(null);
}
public final void writeComponent(Writer w) throws IOException {
    toXml().write(w);
}
}
