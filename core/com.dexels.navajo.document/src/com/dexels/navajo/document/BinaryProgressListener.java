/*
 * Created on May 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.document;


public interface BinaryProgressListener {
    /**
     * Fires an binary progress event event to all listeners
     * @param b boolean
     * @param service String
     * @param progress long
     * @param total long
     */
    public void fireBinaryProgress(String service, long progress, long total);

    public void fireBinaryFinished(String message, long expectedLength);
}
