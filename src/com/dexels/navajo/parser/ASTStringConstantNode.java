package com.dexels.navajo.parser;

import java.io.*;

import com.dexels.navajo.document.*;


public final class ASTStringConstantNode extends SimpleNode {

    String val;

    public ASTStringConstantNode(int id) {
        super(id);
    }

    public final Object interpret() {
        // Strip quotes.
        String s = val.substring(1, val.length() - 1);
        String t = s.replaceAll("\\\\'","'");
        return t;
//        return new String(val.substring(1, val.length() - 1));
    }
    
    public static void main(String[] args) throws Exception{
        System.err.println("alright.");
        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
      NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());

    Navajo n = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(n, "Aap");
        Property p = NavajoFactory.getInstance().createProperty(n, "Prop", Property.EXPRESSION_PROPERTY, "'\t'", 10, "", Property.DIR_IN, null);
        n.addMessage(m);
        m.addProperty(p);
        System.err.println("Value: >"+p.getValue()+"<");
        System.err.println("Value: >"+p.getTypedValue()+"<");
        StringWriter sw = new StringWriter();
        n.write(sw);
        ByteArrayInputStream sr = new ByteArrayInputStream(sw.toString().getBytes());
        Navajo nn = NavajoFactory.getInstance().createNavajo(sr);
        
        System.err.println("AGAIN:");
        nn.write(System.err);
        
        }
}
