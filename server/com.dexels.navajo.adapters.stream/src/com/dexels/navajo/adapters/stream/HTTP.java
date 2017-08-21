package com.dexels.navajo.adapters.stream;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.reactivex.Flowable;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import rx.schedulers.Schedulers;

public class HTTP {

	// TODO test, support extra headers
	public static Flowable<byte[]> post(String postURL, Flowable<byte[]> body, Map<String,List<Object>> headers) throws MalformedURLException {
		URL u = new URL(postURL);
		boolean secure = u.getProtocol().equals("https");
		String host = u.getHost();
		int port = u.getPort()==-1?(secure?443:80):u.getPort();
		String path = u.getPath();
		
		HttpClient<ByteBuf,ByteBuf> client = secure ? HttpClient.newClient(host,port).secure(defaultSSLEngineForClient(host, port)) : HttpClient.newClient(host,port);

		return RxJavaInterop.toV2Flowable(
				client.createPost(path)
				    .addHeader("Host", host)
				    .addHeaders(headers)
//				    .addHeader("X-Navajo-Instance", "KNVB")
//				    .addHeader("Content-Type", "text/xml")
				    .writeContentAndFlushOnEach(RxJavaInterop.toV1Observable(body).map(HTTP::toByteBuf))
					.observeOn(Schedulers.io())
			    )
			    .map(HTTP::processResponse)
			    .concatMapEager(e->e);
		
	}
	// TODO Rewrite using non blocking HTTP Client
	public static Flowable<byte[]> get(String getUrl) throws MalformedURLException {
		URL u = new URL(getUrl);
		boolean secure = u.getProtocol().equals("https");
		String host = u.getHost();
		int port = u.getPort()==-1?(secure?443:80):u.getPort();
		String path = u.getPath();
		HttpClient<ByteBuf,ByteBuf> client = secure ? HttpClient.newClient(host,port).secure(defaultSSLEngineForClient(host, port)) : HttpClient.newClient(host,port);

		
		return RxJavaInterop.toV2Flowable(client
		    .createGet(path+"?"+u.getQuery())
		    .doOnNext(re->System.err.println(":: "+re.toString()))
		    .concatMap(resp->resp.getContent().asObservable().map(b->{byte[] bb = new byte[b.readableBytes()]; b.readBytes(bb); return bb;})));

//		return Observable.create(subscriber->{
//			try {
//				System.err.println("GET THREAD: "+Thread.currentThread().getName());
//				URL url = new URL(getUrl);
//				URLConnection conn = url.openConnection();
//				InputStream is = conn.getInputStream();
//				int nRead;
//				byte[] data = new byte[BUFFERSIZE];
//
//				while ((nRead = is.read(data, 0, data.length)) != -1) {
//					subscriber.onNext(Arrays.copyOfRange(data,0,nRead));
//				}
//				subscriber.onCompleted();
//			} catch (Throwable e) {
//				subscriber.onError(e);
//			}
//		});
	}
	
	
//	public static Observable<ByteBuffer> getToByteBuffer(String getUrl) {
//		return get(getUrl).map(b->ByteBuffer.wrap(b));
//	}

	private static Flowable<byte[]> processResponse(HttpClientResponse<ByteBuf> response) {
		return RxJavaInterop.toV2Flowable(response.getContent().asObservable().doOnSubscribe(()->System.err.println("Subscribe happened")).doOnNext(e->System.err.println("Something read")). map(HTTP::toBytes));
	}
	
	private static  byte[] toBytes(ByteBuf bytebuf) {
		byte[] bytes = bytebuf.toString(Charset.defaultCharset()).getBytes();
		bytebuf.release();
		return bytes;
	}
	
	private static ByteBuf toByteBuf(byte[] b) {
		return Unpooled.copiedBuffer(b);
	}
    private static SSLEngine defaultSSLEngineForClient(String host, int port) {
        try {
			SSLContext sslCtx = SSLContext.getDefault();
			SSLEngine sslEngine = sslCtx.createSSLEngine(host, port);
			sslEngine.setUseClientMode(true);
			
			System.err.println("SSL engine created");
			return sslEngine;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
