package java.net;
//
// TimeoutSocketFactory
//
// by Mark Nelson - 2001, 2002
//
//
// This code was created with only slight modifications based on
// a post from Denise Haskin. See the following URL for the original:
// http://forum.java.sun.com/thread.jsp?forum=4&thread=187644
//
// Note that you will need to add the -Xbootclasspath/a:. to the
// VM commands when using this code, which must be in java.net
//
import java.net.*;

public class MySocketImplFactory implements SocketImplFactory
{
    public MySocketImplFactory( )
    {
      System.err.println("IN MySocketImplFactory");
    }
    public SocketImpl createSocketImpl()
    {
      PlainSocketImpl s = new MyPlainSocketImpl( m_timeout );
      return s;
    }
     int m_timeout;
}

class MyPlainSocketImpl extends PlainSocketImpl
{
  MyPlainSocketImpl( int timeout )
  {
    m_timeout = timeout;
  }
  protected void connect( InetAddress add, int port )
    throws java.io.IOException
  {
    super.connect( add, port);
    try
    {
      setOption( SO_TIMEOUT, new Integer( m_timeout ));
    }
    catch (Exception ex)
    {
     System.out.println("Caught exception in connect(InetAddress,int): "
                        + ex );
    }
  }
  protected void connect( String host, int port )
    throws java.io.IOException
  {
    super.connect( host, port);
    try
    {
      setOption( SO_TIMEOUT, new Integer( m_timeout ));
    }
    catch (Exception ex)
    {
      System.out.println( "Caught exception in connect(String,int): "
                          + ex );
    }
  }
  final int m_timeout;
}
