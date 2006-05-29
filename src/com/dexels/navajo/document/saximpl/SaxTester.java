/*
 * Created on May 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.document.saximpl;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.saximpl.qdxml.*;
import com.dexels.navajo.document.types.*;

public class SaxTester {

    public  static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
    public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
    public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
    public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

    private static final int ITERATIONS = 1;
    /**
     * @param args
     * @throws Exception 
     */
    
    
    public static void testDoc () throws Exception {
//        NavajoC
        System.setProperty(DOC_IMPL,QDSAX);
     }
    public static void main(String[] args) throws Exception {
        String name = null;
        System.err.println("Starting");
        long current = System.currentTimeMillis();
//        readWriteTest("c:/qdimplsave_text.xml",ITERATIONS,NANO);
//        System.err.println("time: "+(System.currentTimeMillis()-current));
//
//        NavajoFactory.resetImplementation();
//        
//         current = System.currentTimeMillis();
//        readWriteTest("c:/FormNumber2.xml", ITERATIONS,JAXP);
//        System.err.println("time: "+(System.currentTimeMillis()-current));
//
//        NavajoFactory.resetImplementation();
//
//        current = System.currentTimeMillis();
//        readWriteTest("c:/FormNumber2.xml",ITERATIONS,QDSAX);

        System.setProperty(DOC_IMPL,QDSAX);
        FileInputStream fis = new FileInputStream("c:/qdimplsave_text.xml");
        Navajo n = NavajoFactory.getInstance().createNavajo(fis);
        Binary b = (Binary)n.getProperty("DocumentData/Data").getTypedValue();
        FileOutputStream fos = new FileOutputStream("c:/dexxx.gif");
        b.write(fos);
        fos.flush();
        fos.close();
        n.write(System.err);
        fis.close();
        System.err.println("time: "+(System.currentTimeMillis()-current));
        System.err.flush();

    }

    public static void readWriteTest(String filename, int iterations, String navajoImplementation) throws NavajoException, IOException {
        System.setProperty(DOC_IMPL,navajoImplementation);
        
        NavajoFactory.resetImplementation();
        long current = System.currentTimeMillis();
        long traversaltotal = 0;
        for (int i = 0; i < iterations; i++) {
            FileInputStream fr = new FileInputStream(filename);
            Navajo n = NavajoFactory.getInstance().createNavajo(fr);
            System.err.println("Created navajo");
            long current2 = System.currentTimeMillis();
            traversalTest(n,20);
            long current3 = System.currentTimeMillis() - current2;
            traversaltotal+= current3;
            File temp = File.createTempFile("tmlTest", ".xml");
            FileWriter fw = new FileWriter(temp);
            System.err.println("About to serialize");
            n.write(fw);
            System.err.println("Finished.");
            fw.close();

                       System.err.println("File written: "+temp+" size: "+temp.length());
            fr.close();
        }
        long diff = System.currentTimeMillis() - current;
        System.err.println("Performance: "+(diff/iterations)+" per iteration ("+navajoImplementation+")");
        System.err.println("Traversal took: "+traversaltotal);
        
    }
    public static void traversalTest(Navajo n, int iterations) throws NavajoException {
        for (int i = 0; i < iterations; i++) {
            traversalTest(n);
            System.err.println("Finished traversal: "+i);
        }
    }    
    public static void traversalTest(Navajo n) throws NavajoException {
        ArrayList messages = n.getAllMessages();
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            Message element = (Message) iter.next();
            traverseMessage(element);
        }
        
    }

    private static void traverseMessage(Message element) {
        ArrayList messages = element.getAllMessages();
        for (Iterator iter = messages.iterator(); iter.hasNext();) {
            Message element2 = (Message) iter.next();
            traverseMessage(element2);
        }
        ArrayList properties = element.getAllProperties();
        for (Iterator iter = properties.iterator(); iter.hasNext();) {
            Property element3 = (Property) iter.next();
            String ss = element3.getValue();
        }
    }
}
