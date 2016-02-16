/**
 * Copyright 2013 Jitendra Kotamraju.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dexels.navajo.listeners.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.InflaterInputStream;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jitu.rx.servlet.ObservableServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.api.NAVADOC;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.io.ObservableStreams;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;


public class NonBlockingListener extends HttpServlet {
	private static final long serialVersionUID = -4381216748627396838L;
	private LocalClient localClient;
	
	
	private final static Logger logger = LoggerFactory.getLogger(NonBlockingListener.class);

	

	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient localClient) {
		this.localClient = localClient;
	}

	public void clearLocalClient(LocalClient localClient) {
		this.localClient = null;
	}
	
    @Override
    protected void doGet(HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

    }
    
	@Override
    protected void doPost(HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        AsyncContext ac = req.startAsync();
		
		Map<String,Object> attributes = new HashMap<>();
		Enumeration<String> en = req.getHeaderNames();
		while (en.hasMoreElements()) {
			String headerName = en.nextElement();
			String headerValue = req.getHeader(headerName);
			System.err.println("Detected header: "+headerName+" name: "+headerValue);
			attributes.put(headerName,headerValue);
		}
        ServletInputStream in = req.getInputStream();

//		ObservableStreams.streamInputStreamWithBufferSize(in, 10);
       Observable<ByteBuffer> input = ObservableStreams.streamInputStreamWithBufferSize(in, 20);
//        ObservableServlet.create(in)      
       input = decompress(input,attributes);
        
        Observable<NavajoStreamEvent> inStream = input
//        		.subscribeOn(Schedulers.io())
        		.doOnNext((bytearray)->System.err.println("Data: "+new String(bytearray.array())))
        		.doOnCompleted(()->System.err.println("done!"))
        		.lift(XML.parse())
        		.doOnCompleted(()->System.err.println("XML complete"))
        		.lift(NAVADOC.parse(attributes))
    			.doOnNext(event->System.err.println("Event: "+event));


        // detect if this is a streaming script, if so, pass the inStream
        // ..... TODO
        
        in.isReady();
        
        // if not, gather the stream to a single Navajo and schedule to the regular channels.
        
        Navajo inputNavajo = inStream.lift(NAVADOC.collect(attributes)).toBlocking().first();
        
        System.err.println("Gathered:");
        inputNavajo.write(System.err);

        String tenant = determineTenantFromRequest(req);
        
		Navajo outDoc = execute(tenant, inputNavajo); // getLocalClient().handleInternal(getNavajoInstance(), in, getRequest().getCert(), clientInfo);
		
        System.err.println("Out:");
        outDoc.write(System.err);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
			Observable.just(outDoc)
			.lift(NAVADOC.stream())
			.lift(NAVADOC.serialize())
			.subscribe(a->{try {baos.write(a);} catch (Exception e2) {e2.printStackTrace();};
			
			},e->{},()->System.err.println("Restreamed: \n"+new String(baos.toByteArray()))
		);
		
        
        
		String encoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		if(encoding!=null) {
			resp.addHeader("Content-Encoding", encoding);
		}
//		System.err.println("Starting output");
		Observable.just(outDoc)
			.lift(NAVADOC.stream())
			.lift(NAVADOC.serialize())
//			.lift(NavajoStreamOperators.compress(encoding))
			.subscribe(
					bytes->{
//						System.err.println("Writing output: "+bytes.length);
						writeResponse(resp, bytes);
					},
					err->{
						try {
							resp.sendError(501);
						} catch (Exception e1) {
							logger.error("Error: ", e1);
						}
					},
					()->{
						ac.complete(); 
						try {
							resp.getOutputStream().close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					);
    }

	private Observable<ByteBuffer> decompress(Observable<ByteBuffer> input, Map<String, Object> attributes) {
		String encoding = (String) attributes.get("Content-Encoding");
		if(encoding==null) {
			return input;
		}
		if(encoding.equals("jzlib")) {
			return input.lift(NavajoStreamOperators.inflate());
		}
		// TODO gzip
		return input;
	}

	private void writeResponse(final HttpServletResponse resp, byte[] bytes) {
		try {
			resp.getOutputStream().write(bytes);
		} catch (IOException e) {
			logger.error("Error: ", e);
			try {
				resp.sendError(502);
			} catch (IOException e1) {
				logger.error("Error: ", e1);
			}
		}
	}
    

	public String decideEncoding(String accept) {
		if(accept==null) {
			return null;
		}
		String[] encodings = accept.split(",");
		Set<String> acceptedEncodings = new HashSet<>();
		for (String encoding : encodings) {
			acceptedEncodings.add(encoding);
		}
		if(acceptedEncodings.contains("deflate")) {
			return "deflate";
		}
		if(acceptedEncodings.contains("jzlib")) {
			return "jzlib";
		}
		// TODO gzip
		return null;
	}
    
	private final Navajo execute(String tenant, Navajo in) throws IOException, ServletException {

		// BufferedReader r = null;
		if(tenant!=null) {
			MDC.put("instance",tenant);
//			myRequest.getInputDocument().getHeader().setHeaderAttribute("instance", tenant);
		}
	
		try {
			in.getHeader().setHeaderAttribute("useComet", "true");
			if (in.getHeader().getHeaderAttribute("callback") != null) {

				String callback = in.getHeader().getHeaderAttribute("callback");

				Navajo callbackNavajo = getLocalClient().handleCallback(tenant, in, callback);
				return callbackNavajo;

			} else {
//					String queueId = myQueue.getId();
//					int queueLength = myQueue.getQueueSize();

//					ClientInfo clientInfo = getRequest().createClientInfo(
//							scheduledAt, startedAt, queueLength, queueId);
				Navajo outDoc = getLocalClient().handleInternal(tenant, in,null, null);
				return outDoc;
			}
		} catch (Throwable e) {
			if (e instanceof FatalException) {
				FatalException fe = (FatalException) e;
				if (fe.getMessage().equals("500.13")) {
					// Server too busy.
					throw new ServletException("500.13");
				}
			}
			throw new ServletException(e);
		} finally {
			MDC.remove("instance");
		}
	}

    
    // warn: Duplicated code
	private String determineTenantFromRequest(final HttpServletRequest req) {
		String requestInstance = req.getHeader("X-Navajo-Instance");
		if(requestInstance!=null) {
			return requestInstance;
		}
		String pathinfo = req.getPathInfo();
		if(pathinfo.length() > 0 && pathinfo.charAt(0) == '/') {
			pathinfo = pathinfo.substring(1);
		}
		String instance = null;
		if(pathinfo.indexOf('/')!=-1) {
			instance = pathinfo.substring(0, pathinfo.indexOf('/'));
		} else {
			instance = pathinfo;
		}
		return instance;
	}

	
    private Subscription sendNavajo(Navajo n,OutputStream out) {
    	NavajoStreamSerializer nss = new NavajoStreamSerializer();
    	return Observable.<Navajo>just(n)
    			.flatMap(NavajoDomStreamer::feed)
    			.flatMap(nss::feed)
    			.subscribe(b->write(out,b));
    }

    private void write(OutputStream out, byte[] b) {
    	try {
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    static class ReadObserver implements Observer<ByteBuffer> {
        private final HttpServletResponse resp;
        private final AsyncContext ac;

        ReadObserver(HttpServletResponse resp, AsyncContext ac) {
            this.resp = resp;
            this.ac = ac;
        }

        @Override
        public void onCompleted() {
            System.out.println("Read onCompleted=" + Thread.currentThread());
            resp.setStatus(HttpServletResponse.SC_OK);

            Observable<ByteBuffer> data = data();
            ServletOutputStream out = null;
            try {
                out = resp.getOutputStream();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            Observable<Void> writeStatus = ObservableServlet.write(data, out);
            writeStatus.subscribe(new WriteObserver(ac));
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("read onError=" + Thread.currentThread());
            e.printStackTrace();
        }

        @Override
        public void onNext(ByteBuffer buf) {
            //Thread.dumpStack();
            //System.out.println("read onNext=" + Thread.currentThread());
        }

        Observable<ByteBuffer> data() {
            ByteBuffer[] data = new ByteBuffer[1000000];
            for(int i=0; i < data.length; i++) {
                data[i] = ByteBuffer.wrap((i+"0000000000000\n").getBytes());
            }
            return Observable.from(data);
        }
    }

    static class WriteObserver implements Observer<Void> {
        private final AsyncContext ac;

        public WriteObserver(AsyncContext ac) {
            this.ac = ac;
        }

        @Override
        public void onCompleted() {
            System.out.println("Composite Write onCompleted");
            ac.complete();
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("write onError=" + Thread.currentThread());
            e.printStackTrace();
        }

        @Override
        public void onNext(Void args) {
            //System.out.println("Composite Write onNext");
            // no-op
        }
    }

}
