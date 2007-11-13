package com.dexels.navajo.tipi.flickr;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.activity.ActivityInterface;
import com.aetrion.flickr.activity.Event;
import com.aetrion.flickr.activity.Item;
import com.aetrion.flickr.activity.ItemList;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.photos.*;
import com.aetrion.flickr.util.IOUtilities;

/**
 * Demonstration of howto use the ActivityInterface.
 *
 * @author mago
 * @version $Id$
 */
public class PhotoManager {
    String restHost = "www.flickr.com";
    static String apiKey;
    static String sharedSecret;
    Flickr f;
    REST rest;
    RequestContext requestContext;
    Properties properties = null;

    private static PhotoManager instance = null;
    
    public static PhotoManager getInstance() {
    	if(instance==null) {
    		try {
				instance = new PhotoManager();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return instance;
    }
    
    public PhotoManager() throws ParserConfigurationException, IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("setup.properties");
            properties = new Properties();
            properties.load(in);
        } finally {
            IOUtilities.close(in);
        }
        rest = new REST();
        rest.setHost(restHost);
        f = new Flickr(properties.getProperty("apiKey"),rest);
        Flickr.debugStream = false;
        // Set the shared secret which is used for any calls which require signing.
        requestContext = RequestContext.getRequestContext();
        requestContext.setSharedSecret(properties.getProperty("secret"));
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(properties.getProperty("token"));
        requestContext.setAuth(auth);
        Flickr.debugRequest = false;
	    Flickr.debugStream = true;
    }

    public PhotoList getTagList(String[] tags) throws FlickrException, IOException, SAXException {
//        ActivityInterface iface = f.getActivityInterface();
//        ItemList list = iface.userComments(10, 0);
//        for (int j = 0; j < list.size(); j++) {
//            Item item = (Item) list.get(j);
//            System.out.println("Item " + (j + 1) + "/" + list.size() + " type: " + item.getType());
//            System.out.println("Item-id:       " + item.getId() + "\n");
//            ArrayList events = (ArrayList) item.getEvents();
//            for (int i = 0; i < events.size(); i++) {
//                System.out.println("Event " + (i + 1) + "/" + events.size() + " of Item " + (j + 1));
//                System.out.println("Event-type: " + ((Event) events.get(i)).getType());
//                System.out.println("User:       " + ((Event) events.get(i)).getUser());
//                System.out.println("Username:   " + ((Event) events.get(i)).getUsername());
//                System.out.println("Value:      " + ((Event) events.get(i)).getValue() + "\n");
//            }
//        }
        SearchParameters sp = new SearchParameters();
        sp.setExtrasGeo(true);
        sp.setTags(tags); 
        
        PhotoList pl =  f.getPhotosInterface().search(sp, 10, 0);
        
        return pl;
 
    }
    public PhotoList getTagList(String[] tags, int index) throws FlickrException, IOException, SAXException {
      SearchParameters sp = new SearchParameters();
      sp.setTags(tags);
      PhotoList pl =  f.getPhotosInterface().search(sp, 1, index);
      return pl;

  }

	/**
	 * @param args
	 */
    public static void main(String[] args) {
        try {
            PhotoManager t = new PhotoManager();
            PhotoList pl= t.getTagList(new String[]{"noordbrabant"},3);
            ArrayList al =  t.getUrls(pl);
            for (Iterator iterator = al.iterator(); iterator.hasNext();) {
            	URL name = (URL) iterator.next();
				System.err.println(name);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public String getUrl(String[] tags, int index) throws IOException, FlickrException, SAXException {
    	PhotoList pl = getTagList(tags, index);
    	ArrayList al = getUrls(pl);
    	if(al==null || al.isEmpty()) {
    		return null;
    	}
    	Photo p = (Photo) al.get(0);

		InputStream is = p.getMediumAsStream();
		
		File f = File.createTempFile("flickR", ".jpg");
		f.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, is);
		

    	return f.toURI().toURL().toString();
    }
    
    private ArrayList  getUrls(PhotoList pl) throws IOException {
    	ArrayList al = new ArrayList();
    	for (Iterator iterator = pl.iterator(); iterator.hasNext();) {
			Photo p = (Photo) iterator.next();
//		    try {
//				GeoData gg =  f.getPhotosInterface().getGeoInterface().getLocation(p.getId());
//				if(gg!=null) {
//					System.err.println("GEO FOUND: "+gg.getLatitude()+" long:" +gg.getLongitude());
//				}
//		    } catch (SAXException e) {
//				e.printStackTrace();
//			} catch (FlickrException e) {
//				e.printStackTrace();
//			}

			al.add(p);
    	}
    	return al;
    }

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
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

}
