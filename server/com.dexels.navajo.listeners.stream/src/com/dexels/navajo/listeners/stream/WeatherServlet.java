package com.dexels.navajo.listeners.stream;

import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.io.NavajoStreamOperators;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class WeatherServlet extends HttpServlet {

	private static final long serialVersionUID = 2123658874212879814L;

	
	private final static Logger logger = LoggerFactory.getLogger(WeatherServlet.class);

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AsyncContext ac = req.startAsync();
//		Map<String, Object> attributes = extractHeaders(req);
//		String responseEncoding = decideEncoding((String) attributes.get("Accept-Encoding"));
		Observable<XMLEvent> eventStream = createReadListener(ac.getRequest().getInputStream())
				.lift(NavajoStreamOperators.decompress(req.getHeader(("Content-Encoding"))))
				.lift(XML.parse());
				
	}

	public static String decideEncoding(String accept) {
		if (accept == null) {
			return null;
		}
		String[] encodings = accept.split(",");
		Set<String> acceptedEncodings = new HashSet<>();
		for (String encoding : encodings) {
			acceptedEncodings.add(encoding.trim());
		}
		if (acceptedEncodings.contains("deflate")) {
			return "deflate";
		}
		if (acceptedEncodings.contains("jzlib")) {
			return "jzlib";
		}
		if (acceptedEncodings.contains("gzip")) {
			return "gzip";
		}
		return null;
	}

	public static Observable<byte[]> createReadListener(final ServletInputStream in) {
		return Observable.create(new OnSubscribe<byte[]>() {
			@Override
			public void call(Subscriber<? super byte[]> subscriber) {
				final ServletReadListener listener = new ServletReadListener(in, subscriber);
				in.setReadListener(listener);
			}
		});
	}

	private static Subscriber<byte[]> createOutput(AsyncContext context, String contentType) throws IOException {
		context.getResponse().setContentType(contentType);
		final ServletOutputStream out = context.getResponse().getOutputStream();
		final AtomicBoolean done = new AtomicBoolean(false);
		final AtomicBoolean completed = new AtomicBoolean(false);
		final Queue<byte[]> outputQueue = new ConcurrentLinkedQueue<byte[]>();
		out.setWriteListener(new WriteListener() {
			
			@Override
			public void onWritePossible() throws IOException {
				if(done.get() && !completed.get()) {
					logger.debug("Done switch trapped and write complete, closing context.");
					completed.compareAndSet(false, true);
					context.complete();
				}
				boolean ready = false;
				while(!outputQueue.isEmpty()) {
					byte[] element = outputQueue.poll();
					if(element!=null) {
						out.write(element);
						ready = out.isReady();
					}
					if(!ready) {
						// not ready, will break loop and await new onWritePossible call
						return;
					}
				}
			}
			
			@Override
			public void onError(Throwable e) {
				logger.error("Error: ", e);
				// TODO Does not work with Async. Duh.
				context.complete();
			}
		});
		
		return new Subscriber<byte[]>(){

			@Override
			public void onCompleted() {
				try {
					logger.debug("Input completed. Setting done: "+done.get());
					done.compareAndSet(false,true);
					logger.debug("Write Done: "+done.get());
					if(out.isReady()) {
						logger.debug("onCompleted done, and still ready, so closing context:");
						completed.compareAndSet(false, true);
						context.complete();
					} else {
						logger.debug("onCompleted done, but not ready, so deferring complete to onWritePossible");
					}
				
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onError(Throwable ex) {
					logger.error("Error!",ex);
					context.complete();
			}

			@Override
			public void onNext(byte[] b) {
				try {
					outputQueue.offer(b);
					boolean ready = true;
					while(ready && !outputQueue.isEmpty()) {
						ready = out.isReady();
						if(!ready) {
							break;
						}
						byte[] element = outputQueue.poll();
						out.write(element);
					}

				} catch (Exception e) {
					logger.error("Error: ", e);
					onError(e);
					unsubscribe();
				}
				
			}
		};
	}

}
