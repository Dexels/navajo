package com.dexels.navajo.server.listener.soap;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.Vector;

/**
 * Simple test driver for our message service.
 */
public class TestMessageService {

/**
    public String doit(String[] args) throws Exception {
        Options opts = new Options(args);
        opts.setDefaultURL("http://localhost/sport-tester/servlet/SOAPMan");

        Service  service = new Service();
        Call     call    = (Call) service.createCall();

        call.setTargetEndpointAddress( new URL(opts.getURL()) );
        SOAPBodyElement[] input = new SOAPBodyElement[3];

        input[0] = new SOAPBodyElement(XMLUtils.StringToElement("urn:foo",
                                                                "e1", "Hello"));
        input[1] = new SOAPBodyElement(XMLUtils.StringToElement("urn:foo",
                                                                "e1", "World"));

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc            = builder.newDocument();
        Element cdataElem       = doc.createElementNS("urn:foo", "e3");
        CDATASection cdata      = doc.createCDATASection("Text with\n\tImportant  <b>  whitespace </b> and tags! ");
        cdataElem.appendChild(cdata);

        input[2] = new SOAPBodyElement(cdataElem);

        Vector          elems = (Vector) call.invoke( input );
        SOAPBodyElement elem  = null ;
        Element         e     = null ;

        elem = (SOAPBodyElement) elems.get(0);
        e    = elem.getAsDOM();

        String str = "Res elem[0]=" + XMLUtils.ElementToString(e);

        elem = (SOAPBodyElement) elems.get(1);
        e    = elem.getAsDOM();
        str = str + "Res elem[1]=" + XMLUtils.ElementToString(e);

        elem = (SOAPBodyElement) elems.get(2);
        e    = elem.getAsDOM();
        str = str + "Res elem[2]=" + XMLUtils.ElementToString(e);

        return( str );
    }

    public static void main(String[] args) throws Exception {
        String res = (new TestMessageService()).doit(args);
        System.out.println(res);
    }
    */
}