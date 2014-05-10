package net.atlanticbb.tantlinger.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;



public class MonitoredInputStream extends FilterInputStream
{
    CopyMonitor monitor;

    public MonitoredInputStream(InputStream in, CopyMonitor monitor)
    {
        super(in);
        this.monitor = monitor;
    }
    
    public boolean markSupported() 
    {
        return false;
    }
    
    public int read() throws IOException
    {
        checkAborted();        
        return super.read();        
    }    
    
    public int read(byte b[], int off, int len) throws IOException 
    {
        checkAborted();
        int numRead = super.read(b, off, len);
        if(numRead != -1) 
        {
            monitor.bytesCopied(numRead);
        }
        return numRead;
        
    }
    
    public int read(byte b[]) throws IOException 
    {
        checkAborted();        
        return super.read(b);
    }
    
    private void checkAborted() throws IOException
    {
        if(monitor.isCopyAborted())
        {
            System.err.println("copy aborted");
            throw new IOException("Copy aborted");
        }
    }
}
