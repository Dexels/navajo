package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EchoServlet extends HttpServlet
{
	private static final long serialVersionUID = -4256893542061225301L;
	private static final int BUFFER_SIZE = 16 * 1024;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        AsyncContext asyncContext = request.startAsync(request, response);
        asyncContext.setTimeout(0);
        Echoer echoer = new Echoer(asyncContext);
        request.getInputStream().setReadListener(echoer);
        response.getOutputStream().setWriteListener(echoer);
    }

    private class Echoer implements ReadListener, WriteListener
    {
        private final byte[] buffer = new byte[BUFFER_SIZE];
        private final AsyncContext asyncContext;
        private final ServletInputStream input;
        private final ServletOutputStream output;
        private final AtomicBoolean complete=new AtomicBoolean(false);
        

        private Echoer(AsyncContext asyncContext) throws IOException
        {
            this.asyncContext = asyncContext;
            this.input = asyncContext.getRequest().getInputStream();
            this.output = asyncContext.getResponse().getOutputStream();
        }
        
        @Override
        public void onDataAvailable() throws IOException
        {
            // input is available, but only read if we can write
            if (output.isReady()) {
                onWritePossible();
            }
        }

        @Override
        public void onAllDataRead() throws IOException
        {
            // All data is consume, so all writes are complete then complete. 
            if (output.isReady() && complete.compareAndSet(false,true))
                asyncContext.complete();
        }

        @Override
        public void onWritePossible() throws IOException
        {
            // This method is called:
            //   1) after first registering a WriteListener (ready for first write)
            //   2) after first registering a ReadListener iff write is ready
            //   3) when a previous write completes after an output.isReady() returns false
            //   4) from an input callback if out.isReady()
            
            // If there is no more input to consume
            if (input.isFinished()) 
            {
                if (complete.compareAndSet(false,true))
                    asyncContext.complete();
                return;
            }
            
            // We know we can write, so loop only if we can read as well
            while (input.isReady()) {
                int read = input.read(buffer);
                output.write(buffer, 0, read);
                
                // only continue if we can still write
                if (!output.isReady()) {
                	System.err.println("Not ready!");
                    break;
                } else {
                	System.err.println("Ready!");
                }
            }
            
        }

        @Override
        public void onError(Throwable failure)
        {
            failure.printStackTrace();
        }
    }
}