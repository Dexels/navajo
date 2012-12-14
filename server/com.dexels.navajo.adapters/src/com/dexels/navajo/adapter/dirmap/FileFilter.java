/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dexels.navajo.adapter.dirmap;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 *
 * @author boer001
 * 
 * The mask is a file mask 
 * Replace is to be used in regex.
 * '.' --> '[.]*'
 * '*' --> '.*'
 * '?' --> '.'
 */
public class FileFilter implements FilenameFilter {

    private String masker = "";

    public FileFilter(String _masker) {
        _masker = _masker.replaceAll("[.]", "[.]");
        _masker = _masker.replaceAll("[*]", ".*");
        masker  = _masker.replaceAll("[?]", ".");
    }

    /**
     * If mask is set the mask is case insensitive when parameter is true 
     * 
     * @param insensitive 
     */
    public void setInsensitive(Boolean insensitive) {
        masker  = (insensitive.booleanValue() ? "(?i)" : "") + this.masker;
    }
    
    public boolean accept(File dir, String name) {
        return Pattern.matches(masker, name);
    }
}