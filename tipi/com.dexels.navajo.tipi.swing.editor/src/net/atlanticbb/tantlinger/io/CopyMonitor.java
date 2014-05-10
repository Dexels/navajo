/*
 * Created on Feb 2, 2006
 *
 */
package net.atlanticbb.tantlinger.io;

public interface CopyMonitor
{
    public void bytesCopied(int numBytes);   
    
    public boolean isCopyAborted();    
}
