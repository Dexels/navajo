package com.dexels.navajo.tipi.flickr;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import com.aetrion.flickr.*;
import com.aetrion.flickr.auth.*;
import com.aetrion.flickr.photos.*;
import com.aetrion.flickr.util.*;

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
    
	private final static Logger logger = LoggerFactory
			.getLogger(PhotoManager.class);
	

    private static PhotoManager instance = null;
    
    public static PhotoManager getInstance() {
    	if(instance==null) {
    		try {
				instance = new PhotoManager();
			} catch (ParserConfigurationException e) {
				logger.error("Error: ",e);
			} catch (IOException e) {
				logger.error("Error: ",e);
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
        // Set the shared secret which is used for any calls which require signing.
        requestContext = RequestContext.getRequestContext();
        requestContext.setSharedSecret(properties.getProperty("secret"));
        Auth auth = new Auth();
        auth.setPermission(Permission.READ);
        auth.setToken(properties.getProperty("token"));
        requestContext.setAuth(auth);
        Flickr.debugRequest = false;
	    Flickr.debugStream = false;
    }

    public PhotoList getTagList(String[] tags) throws FlickrException, IOException, SAXException {
//        ActivityInterface iface = f.getActivityInterface();
//        ItemList list = iface.userComments(10, 0);
//        for (int j = 0; j < list.size(); j++) {
//            Item item = (Item) list.get(j);
//            logger.info("Item " + (j + 1) + "/" + list.size() + " type: " + item.getType());
//            logger.info("Item-id:       " + item.getId() + "\n");
//            ArrayList events = (ArrayList) item.getEvents();
//            for (int i = 0; i < events.size(); i++) {
//                logger.info("Event " + (i + 1) + "/" + events.size() + " of Item " + (j + 1));
//                logger.info("Event-type: " + ((Event) events.get(i)).getType());
//                logger.info("User:       " + ((Event) events.get(i)).getUser());
//                logger.info("Username:   " + ((Event) events.get(i)).getUsername());
//                logger.info("Value:      " + ((Event) events.get(i)).getValue() + "\n");
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

//	/**
//	 * @param args
//	 */
//    public static void main(String[] args) {
//        try {
//            PhotoManager t = new PhotoManager();
//            PhotoList pl= t.getTagList(new String[]{"noordbrabant"},3);
//            ArrayList al =  t.getUrls(pl);
//            for (Iterator iterator = al.iterator(); iterator.hasNext();) {
//            	URL name = (URL) iterator.next();
//				logger.info(name);
//			}
//        } catch (Exception e) {
//            logger.error("Error: ",e);
//        }
//        System.exit(0);
//    }

	public List<Photo> getPhotos(String[] tags, int max, int index) throws FlickrException, IOException, SAXException {
    	
        SearchParameters sp = new SearchParameters();
        sp.setSort(SearchParameters.INTERESTINGNESS_DESC);
//        sp.setTagMode("all");
        sp.setTags(tags);
        
        PhotoList pl =  f.getPhotosInterface().search(sp, max, index);
        List<Photo> al = new ArrayList<Photo>();
        for (Iterator<Photo> iterator = pl.iterator(); iterator.hasNext();) {
			Photo photo = iterator.next();
			al.add(photo);
		}
        return al;

    }
    
    public Photo getPhoto(String id) throws FlickrException, IOException, SAXException {
        return  f.getPhotosInterface().getPhoto(id);

    }
    
    
    public String getUrl(String[] tags, int index) throws IOException, FlickrException, SAXException {
    	PhotoList pl = getTagList(tags, index);
    	List<Photo> al = getUrls(pl);
    	if(al==null || al.isEmpty()) {
    		return null;
    	}
    	Photo p = al.get(0);
    	String s = p.getLargeUrl();
    	logger.info("My url:" +s);
		InputStream is = p.getMediumAsStream();
		
		File f = File.createTempFile("flickR", ".jpg");
		f.deleteOnExit();
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, is);
		

    	return f.toURI().toURL().toString();
    }
    
	public List<Photo>  getUrls(PhotoList pl) {
    	List<Photo> al = new ArrayList<Photo>();
    	for (Iterator<Photo> iterator = pl.iterator(); iterator.hasNext();) {
			Photo p = iterator.next();
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
