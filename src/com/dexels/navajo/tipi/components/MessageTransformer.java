package com.dexels.navajo.tipi.components;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MessageTransformer extends com.dexels.navajo.tipi.TipiComponent {
  public MessageTransformer() {
  }

//  public void addToContainer(Component c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
//  }
//  public void removeFromContainer(Component c) {
//    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
//  }
//  public void registerEvents() {
//    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent abstract method*/
//  }
  public Container createContainer() {
    return null;
  }

  public Object getComponentValue(String name) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
    return super.getComponentValue(name);
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("transform")) {
      //System.err.println("INVOCATION: "+invocation.toString());
      TipiValue path = compMeth.getParameter("transformpath");
      TipiValue xsltFile = compMeth.getParameter("xsltFile");
      Message m = myContext.getMessageByPath(path.getValue());
      transformMessage(m,xsltFile.getValue());
    }
  }

  private void transformMessage(Message msg, String xsltFile){
    try{
      Transformer transformer;
      if(xsltFile.length() > 0){
        transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(xsltFile));
      }else{
        transformer = TransformerFactory.newInstance().newTransformer();
      }
      //System.err.println("m.getRef(): " + msg.getRef().getClass());
      com.dexels.navajo.document.nanoimpl.XMLElement elmnt = (com.dexels.navajo.document.nanoimpl.XMLElement) msg.getRef();
//      javax.swing.FileDialog fd = new FileDialog((Frame)myContext.getTopLevel(), "Opslaan", FileDialog.SAVE);
      JFileChooser fd = new JFileChooser("Opslaan");
      fd.showSaveDialog(this.getTipiParent().getContainer());
      File out = fd.getSelectedFile();
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      transformer.transform(new StreamSource(new StringReader(elmnt.toString())), new StreamResult(out));
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }


}