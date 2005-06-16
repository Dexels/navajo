/*
 * Created on Jun 2, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.mapping.compiler;

import com.dexels.navajo.document.nanoimpl.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public class TslCompileException extends Exception {

    public static final int TSL_UNKNOWN_MAP = -1;
    public static final int TSL_PARSE_EXCEPTION = -2;
    public static final int TSL_UNKNOWN_FIELD = -3;
    public static final int VALIDATION_NO_VALUE = -8;
    public static final int VALIDATION_NO_CODE = -9;
    public static final int VALIDATION_NO_SCRIPT_INCLUDE = -10;
    public static final int TSL_MISSING_FIELD_NAME = -12;
    public static final int SUB_MAP_ERROR = -13;
    public static final int TSL_MISSING_VALUE = -14;
    public static final int TSL_INAPPROPRIATE_NODE = -15;
  
    
    private int startOffset;
    private int endOffset;
    private XMLElement mySource;
    private final int code;
     public TslCompileException(int code, String message, XMLElement x) {
        super(message+" ("+x.getStartOffset()+"-"+x.getOffset()+")");
        this.startOffset = x.getStartOffset();
        this.endOffset = x.getOffset();
        this.code = code;
        mySource = x;
    }
   public TslCompileException(int code, String message, int startOffset, int endOffset) {
        super(message+" ("+startOffset+"-"+endOffset+")");
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.code = code;
    }

   public int getCode() {
       return code;
   }
   
    /**
     * @return
     */
    public int getEndOffset() {
        return endOffset;
    }

    /**
     * @return
     */
    public int getStartOffset() {
        return startOffset;
    }
    /**
     * @return
     */
    public XMLElement getSource() {
        // TODO Auto-generated method stub
        return mySource;
    }
 }
