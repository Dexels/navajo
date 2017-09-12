import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

public class TestLargePayloads {
  private static AtomicLong clientWrote = new AtomicLong(0);
  private static AtomicLong serverRead = new AtomicLong(0);
  private static AtomicLong serverWrote = new AtomicLong(0);
  private static AtomicLong clientRead = new AtomicLong(0);

//  private static AtomicLong iteration = new AtomicLong(0);

  public static void main(String[] args) throws Exception {

    HttpServer<ByteBuf, ByteBuf> server = HttpServer.newServer()
      .enableWireLogging("FOO", LogLevel.WARN)
      .start(
        (request, response) ->
          response.writeStringAndFlushOnEach( // force flush server output
            request.getContent().autoRelease() // release bytebufs
              .subscribeOn(Schedulers.computation())
              .map((byteBuf) -> byteBuf.toString(Charset.defaultCharset()))
              .doOnNext(s -> update(serverRead, s.length()))
              .compose(TestLargePayloads::incrementChars)
              .doOnNext(s -> update(serverWrote, s.length()))
              .doOnNext(s -> sleep()) // force thread switching
          )
      );

    TestSubscriber<String> subscriber = TestSubscriber.create();

    printStatus();

    HttpClient.newClient("localhost", server.getServerPort())
      .enableWireLogging("TMP", LogLevel.INFO)
      .createPost("/")
      .writeStringContent(
        Observable.range(0, 10)
          .map(i -> String.format("%010d", i))
          .doOnNext(s -> update(clientWrote, s.length()))
          .doOnNext(s -> sleep()), // force thread switching
        s -> true // force flush client output
      )
      .flatMap(
        response -> {
          System.out.println("Got response");
          return response.getContent().autoRelease()
            .map((byteBuf) -> byteBuf.toString(Charset.defaultCharset()));
        }
      )
      .doOnNext(s -> update(clientRead, s.length()))
		.subscribeOn(Schedulers.io())
		.observeOn(Schedulers.computation())
      .subscribe(subscriber);

    subscriber.awaitTerminalEvent();

//    printStatus();

    System.err.println();

    subscriber.assertNoErrors();

    server.shutdown();
  }

  private static void sleep() {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  static Observable<String> incrementChars(Observable<String> ss) {
    // busy work
    return ss.map(
      s ->
        s.chars()
          .map(i -> i + 1)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString()
    );
  }

  static void update(AtomicLong v, int by) {
    v.addAndGet(by);

//    if (iteration.getAndIncrement() % 1000 == 0) {
      printStatus();
//    }
  }

  private static void printStatus() {
    System.out.println(
      String.format(
        "Client wrote %s, Server read %s, Server wrote %s, Client read %s [%s]",
        clientWrote,
        serverRead,
        serverWrote,
        clientRead,
        Thread.currentThread().getName()
      )
    );
  }
}