package com.dexels.navajo.adapter.descriptionprovider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.tidy.Tidy;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.sun.net.ssl.HttpsURLConnection;

public class TestBabel {

	private static File debugDir = new File("babel");

	/**
	 * @param args
	 * @throws IOException
	 * @throws NavajoException
	 */
	public static void main(String[] args) throws IOException, NavajoException {
		// String s = getTranslation("De woordvoerder van het Witte Huis begon
		// zijn persbriefing op een ongebruikelijke manier. Tony Fratto
		// excuseerde zich voor het onbeantwoord blijven van
		// e-mails....","nl_en");
		// String s = getTranslation("aardbei","nl_en");
		String s = getTranslation("test","", "nl_en","");

		System.err.println(s);
	}

	public static String getTranslation(String entity, String word, String lang_pair, String context) throws MalformedURLException, IOException, ProtocolException {

		word = word.replaceAll(" ", "%20");

		InputStream ii = getFileStream(entity, word, lang_pair,context);
		if (ii == null) {
			ii = getInputStream(entity, word, lang_pair,context);
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copyResource(baos, ii);
		String result = new String(baos.toByteArray());

//		debugWord(entity, word, lang_pair, result, context);
		// String result = convertToXHTML(ii);
		String val2 = result.replaceAll("&nbsp;", " ");
		val2 = val2.replaceAll("\n\r", "\n");
		val2 = val2.replaceAll("\n", " ");

		// System.err.println("RESULT: "+val2);

		String search = "<td bgcolor=white class=s><div style=padding:10px;>";
		int i = val2.indexOf(search);
		String ss = val2.substring(i + search.length(), val2.length()-1);
		String ss2 = ss.substring(ss.indexOf(";\">") + 1, ss.length() - 1);
		ss2 = ss2.trim();
		int divindex = ss2.indexOf("</div>");

		if (divindex == -1) {
			System.err.println("No closing div found: " + ss2);
			System.err.println("Error parsing result of word: " + word);
		} else {

			String ss3 = ss2.substring(0, divindex);
			ss3 = ss3.replaceAll("\n", " ");
			return ss3;
		}
		return "error:"+word;

	}

	private static InputStream getInputStream(String entity, String word, String lang_pair,String context) throws MalformedURLException, IOException, ProtocolException {
		System.err.println("Word: " + word + " ttrans: " + lang_pair);

		URL url = new URL("http://babelfish.altavista.com/tr?lp=" + lang_pair + "&trtext=" + word + "&btnTrUrl=Translate&doit=done&intl=1");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);

		InputStream ii = con.getInputStream();
		return ii;
	}

	private static void debugWord(String entity, String word, String lang_pair, String result, String context) throws IOException {
		File contextdir = new File(debugDir, context);
		File langdir = new File(contextdir, lang_pair);
		if (!langdir.exists()) {
			langdir.mkdirs();
		}
		File f = new File(langdir, entity+".html");
		FileWriter fw = new FileWriter(f);
		fw.write(result);
		fw.flush();
		fw.close();
	}

	private static InputStream getFileStream(String entity, String word, String lang_pair, String context) throws IOException {
		File f = new File(debugDir, lang_pair);
		File fa = new File(f, context);
		File f2 = new File(fa, entity+".html");
		if (f2.exists()) {
			return new FileInputStream(f2);
		}
		return null;
	}

	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}

	public static String convertToXHTML(InputStream bais) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Tidy t = new Tidy();
		t.setIndentContent(true);
		t.setXmlOut(true);
		// t.setXHTML(true);
		t.parse(bais, baos);
		String result = new String(baos.toByteArray());
		// System.err.println("Tidy result: \n"+result);
		t.setXHTML(true);
		// xmlns="http://www.w3.org/1999/xhtml
		int ii = result.indexOf("<html>");
		if (ii < 0) {
			return result;
		} else {
			return result.substring(ii);
		}
	}

}
