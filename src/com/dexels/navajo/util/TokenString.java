

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

/**
 * This object can be used to parse a String into elements seperated by Tokens (which can be Strings!)
 * This function has been written to overcome the single char limitation of the standard StringTokenizer
 * object.
 *
 * Example:
 *
 * String str = "6 <=9 >= 8 < 3";
 * String tokens = {"<=", ">=", "<"};
 *
 * TokenString tok = new TokenString(str, tokens);
 * while (tok.hasMoreElements()) {
 *   String el = tok.nextElement();
 *   String token = tok.getToken();
 * }
 *
 * IT IS IMPORTANT TO NOTE THAT THE token list SHOULD BE ORDERED BY TOKEN LENGTH.
 * STARTING WITH THE LONGEST TOKEN, IN ORDER TO DISCRIMINATE BETWEEN TOKENS THAT
 * START WITH THE SAME CHARACTER(S).
 */
package com.dexels.navajo.util;


public class TokenString {

    private int ElPtr = 0;
    @SuppressWarnings("unused")
	private int TkPtr = 0;
    private String input = null;
    private String[] tokens = null;
    private String curToken = null;

    public TokenString(String str, String[] tokens) {
        this.input = str;
        this.tokens = tokens;
    }

    private String getNextToken(String s) {
        boolean found = false;
        int i = 0;
        String tok = "";

        while (!found) {
            if (i < tokens.length) {
                tok = tokens[i];
                i++;
                if (s.startsWith(tok))
                    found = true;
            } else {
                found = true;
                tok = "";
            }
        }

        return tok;
    }

    public int countTokens() {
        int i = 0;

        String scratch = input;
        String tok = "";

        // while (!(tok = getNextToken(scratch)).equals("")) {

        boolean ready = false;

        while (!ready) {

            tok = getNextToken(scratch);
            if (!tok.equals("")) {
                int b = scratch.indexOf(tok) + tok.length();

                scratch = scratch.substring(b, scratch.length());
                i++;
            }

            scratch = scratch.substring(1, scratch.length());
            if (scratch.length() == 0)
                ready = true;
        }

        return i;
    }

    public int countElements() {

        int i = 0;
        // Save ElPtr and curToken.
        int ElPtrSaved = ElPtr;
        String curTokenSaved = curToken;

        while (this.hasMoreElements()) {
            this.nextElement();
            i++;
        }

        ElPtr = ElPtrSaved;
        curToken = curTokenSaved;

        return i;
    }

    private String getElement() {

        String scratch = input.substring(ElPtr, input.length());
        String el = scratch;
        int i, newEnd = 0;

        if (ElPtr == input.length())
            return null;

        int length = input.length();

        String beginToken = null;

        for (i = ElPtr; i < length; i++) {
            beginToken = getNextToken(scratch);
            if (!beginToken.equals("")) {
                String temp = input.substring(ElPtr, input.length());

                newEnd = temp.indexOf(beginToken);
                el = temp.substring(0, newEnd);
                i = ElPtr + newEnd + beginToken.length();
                break;
            } else
                beginToken = null;
            scratch = scratch.substring(1, scratch.length());
        }
        ElPtr = i;

        curToken = beginToken;

        return el;
    }

    public boolean hasMoreElements() {
        return (ElPtr < input.length());
    }

    public String nextElement() {
        String el = getElement();

        return el;
    }

    public String getToken() {
        return curToken;
    }

}
