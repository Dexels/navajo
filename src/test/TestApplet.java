package test;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;



public class TestApplet extends JApplet {

	public String [] urlnames = new String [] {
			"Sportlink website (GET)",
			"Sportlink club atlas 80 (GET)",
			"Sportlink club atlas 443 (GET)",
			"Sportlink club hera 80 (GET)",
			"Sportlink club hera 443 (GET)",
			"Sportlink club atlas 80 (POST)",
			"Sportlink club atlas 443 (POST)",
			"Sportlink club hera 80 (POST)",
			"Sportlink club hera 443 (POST)"
	};
	
	public String [] urls = new String[] {
			"http://www.sportlinkservices.nl/",
			"http://atlas.dexels.com/sportlink/knvb",
			"http://atlas.dexels.com:443/sportlink/knvb",
			"http://hera1.dexels.com/sportlink/knvb",
			"http://hera1.dexels.com:443/sportlink/knvb",
			"http://atlas.dexels.com/sportlink/knvb/servlet/Postman",
			"http://atlas.dexels.com:443/sportlink/knvb/servlet/Postman",
			"http://hera1.dexels.com/sportlink/knvb/servlet/Postman",
			"http://hera1.dexels.com:443/sportlink/knvb/servlet/Postman"
	};
	
	private boolean testConnection(URL url) {
		
		String xml = "<tml><header><transaction rpc_name=\"navajo_logon\" rpc_usr= \"ROOT\" rpc_pwd=\"\"/></header></tml>";
		HttpURLConnection con = null;
		
		try {
			con = (HttpURLConnection) url.openConnection();
			
			try {
				java.lang.reflect.Method timeout = con.getClass().getMethod("setConnectTimeout", new Class[]{int.class});
				timeout.invoke( con, new Object[]{new Integer(3000)});
			} catch (Throwable e) {
				System.err.println("setConnectTimeout does not exist, upgrade to java 1.5+");
			}
			
			if ( url.toString().endsWith("Postman")) {
				
				System.err.println("Using POST");
				con.setRequestMethod("POST");
				con.setDoOutput(true);
				con.setDoInput(true);
				con.setUseCaches(false);

				//con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
				OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
				System.err.println("Wrote POST");
				out.write(xml);
				out.close();
				System.err.println("POST done");
			} else {
				System.err.println("Using GET");
			}
			
			BufferedReader isr = new BufferedReader( new InputStreamReader( con.getInputStream()) );
			String line = null;
			if ( (line = isr.readLine() ) != null ) {
				System.err.println(line);
			}
			isr.close();
			
		} catch (Throwable e) {
			e.printStackTrace(System.err);
			return false;
		}
		return true;
	}

	public void init() {
		setLayout(new GridBagLayout());
		final ArrayList hm = new ArrayList();
		for (int i = 0; i < urls.length; i++ ) {
			JLabel label = new JLabel( urlnames[i]);
			add(label, new GridBagConstraints(0,i,1,1,1.0,0.0,18,0,new Insets(3,3,3,3),0,0));
			JCheckBox jcb = new JCheckBox("",false);
			jcb.setEnabled(false);
			hm.add(jcb);
			add(jcb, new GridBagConstraints(1,i,1,1,1.0,0.0,18,0,new Insets(3,3,3,3),0,0));
		}
		final JProgressBar jpb = new JProgressBar(0, urls.length-1);
		add(jpb, new GridBagConstraints(0,urls.length+1,2,1,1.0,1.0,18,2,new Insets(3,3,3,3),0,0));
		
		final JButton jb = new JButton("Start");
		add(jb, new GridBagConstraints(0,urls.length+2,1,1,0.0,0.0,18,0,new Insets(3,3,3,3),0,0));
		jb.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						// Reset first.
						jb.setEnabled(false);
						jpb.setValue(0);
						for (int i = 0; i < urls.length; i++ ) {
							((JCheckBox) hm.get(i)).setSelected(false);
						}
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						for (int i = 0; i < urls.length; i++ ) {
							final int j = i;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							SwingUtilities.invokeLater(new Runnable() { public void run() { try { JCheckBox jcb = (JCheckBox) hm.get(j);jpb.setValue(j);jcb.setSelected(testConnection(new URL(urls[j])));} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} };});

						}
						jb.setEnabled(true);
					}
				}.start();
			} } );
		
	}
	
	public static void main(String [] args) throws Exception {
		TestApplet ap = new TestApplet();
		for (int i = 0; i < ap.urls.length; i++ ) {
			System.err.println(ap.urls[i] + ":" + ap.testConnection(new URL(ap.urls[i])));
		}
	}
}
