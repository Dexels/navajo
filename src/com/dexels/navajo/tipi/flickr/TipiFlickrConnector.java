package com.dexels.navajo.tipi.flickr;

import java.io.*;
import java.util.*;

import com.aetrion.flickr.people.*;
import com.aetrion.flickr.photos.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.connectors.*;

public class TipiFlickrConnector extends TipiBaseConnector {


	public TipiFlickrConnector() {
		System.err.println("FLICKR INSTANTIATED!");
	}
	public Set<String> getEntryPoints() {
		Set<String> s = new HashSet<String>();
		s.add("InitFlickr");
		s.add("InitQueryFlickr");
		s.add("InitListFlickr");
		return s;
	}

	public String getDefaultEntryPoint() {
		return "InitFlickr";
	}

	
//	private String loadUrl(String tag, int index) {
//		try {
//			String url =  PhotoManager.getInstance().getUrl(new String[]{tag}, index);
//			return url;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (FlickrException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		}
//		return null;
//		
//	}

	public static void main(String[] args) throws NavajoException, TipiException {
		TipiFlickrConnector tipiFlickrConnector = new TipiFlickrConnector();
		Navajo n = tipiFlickrConnector.createInitListFlickr();
		n.write(System.err);
		n.getProperty("Flickr/Tag").setValue("Omniworld fc voetbal");
		Navajo mm = tipiFlickrConnector.doListPictures(n);
		mm.write(System.err);
//		2298318976		
		Navajo o = tipiFlickrConnector.createInitQueryFlickr();
		o.getProperty("Flickr/Id").setValue("2298318976");
		Navajo xx = tipiFlickrConnector.doQueryPicture(o);
		xx.write(System.err);
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		if(service.equals("InitListFlickr")) {
			try {
				Navajo result = createInitListFlickr();
				injectNavajo(service, result);
			} catch (NavajoException e) {
				throw new TipiException("Error calling service: "+service,e);
			}
		}
		if(service.equals("InitFlickr")) {
			try {
				Navajo result = createInitFlickr();
				injectNavajo(service, result);
			} catch (NavajoException e) {
				throw new TipiException("Error calling service: "+service,e);
			}
		}
		
		
		if(service.equals("InitQueryFlickr")) {
			try {
				Navajo result = createInitQueryFlickr();
				injectNavajo(service, result);
			} catch (NavajoException e) {
				throw new TipiException("Error calling service: "+service,e);
			}
		}
		if(service.equals("ListFlickr")) {
			try {
				Navajo result = doListPictures(n);
				injectNavajo(service, result);
			} catch (NavajoException e) {
				throw new TipiException("Error calling service: "+service,e);
			}
		}

		if(service.equals("QueryFlickr")) {
			try {
				Navajo result = doQueryPicture(n);
				injectNavajo(service, result);
			} catch (NavajoException e) {
				throw new TipiException("Error calling service: "+service,e);
			}
		}
	}

	private Navajo createInitFlickr() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Method list = NavajoFactory.getInstance().createMethod(n, "InitListFlickr", null);
		Method query = NavajoFactory.getInstance().createMethod(n, "InitQueryFlickr", null);
		n.addMethod(list);
		n.addMethod(query);
		return n;
	}
	private Navajo createInitListFlickr() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Flickr");
		n.addMessage(m);
		addProperty(m, "Tag", null, Property.STRING_PROPERTY);
		addProperty(m, "Index", null, Property.INTEGER_PROPERTY);
		addProperty(m, "Max", null, Property.INTEGER_PROPERTY);
		addProperty(m, "IncludeThumbnail", false, Property.BOOLEAN_PROPERTY);
		addProperty(m, "IncludePicture", false, Property.BOOLEAN_PROPERTY);
		addProperty(m, "IncludeMetaData", false, Property.BOOLEAN_PROPERTY);
		
		Method go = NavajoFactory.getInstance().createMethod(n, "ListFlickr", null);
		n.addMethod(go);
		return n;
	}
	private Navajo createInitQueryFlickr() throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Flickr");
		n.addMessage(m);
		addProperty(m, "Id", null, Property.STRING_PROPERTY);
		Method go = NavajoFactory.getInstance().createMethod(n, "QueryFlickr", null);
		n.addMethod(go);
		return n;
	}
	private Navajo doListPictures(Navajo input) throws TipiException, NavajoException {
		Property tag = input.getProperty("Flickr/Tag");
		Property max = input.getProperty("Flickr/Max");
		Property thumbProp = input.getProperty("Flickr/IncludeThumbnail");
		Property mediumProp = input.getProperty("Flickr/IncludePicture");
		Boolean includeThumbnails = (Boolean) thumbProp.getTypedValue();
		Boolean includePicture = (Boolean) mediumProp.getTypedValue();

		Property metaDataProp = input.getProperty("Flickr/IncludeMetaData");
		Boolean metadata = (Boolean) metaDataProp.getTypedValue();

		Integer maxInt = (Integer) max.getTypedValue();
		Property index = input.getProperty("Flickr/Index");
		Integer indexInt = (Integer) index.getTypedValue();
		if(maxInt==null) {
			maxInt = 10;
		}
		if(indexInt==null) {
			indexInt = 1;
		}
		String tagString = (String) tag.getTypedValue();
		List<Photo> tt;
		StringTokenizer tagger = new StringTokenizer(tagString);
		String[] tagg = new String[tagger.countTokens()];
		int i=0;
		while (tagger.hasMoreTokens()) {
			tagg[i++]= tagger.nextToken();
			System.err.println("adding tag: "+tagg[i-1]);
		}
		try {
			tt = PhotoManager.getInstance().getPhotos(tagg,maxInt, indexInt);
		} catch (Exception e) {
			throw new TipiException("Error connecting to Flickr",e);
		}
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "List",Message.MSG_TYPE_ARRAY);
		n.addMessage(m);
		for (Photo photo : tt) {
			Message element = NavajoFactory.getInstance().createMessage(n, "Flickr",Message.MSG_TYPE_ARRAY_ELEMENT);
			m.addMessage(element);
			if(metadata) {
				appendPhoto(element,photo);
			}
			
			if(includeThumbnails) {
				appendThumbnailBinary(element, photo);
			}
			if(includePicture) {
				appendMediumPictureBinary(element, photo);
			}
			
			
		}
		return n;
	}

	private Navajo doQueryPicture(Navajo input) throws TipiException, NavajoException {

		Property id = input.getProperty("Flickr/Id");
		String idString = (String) id.getTypedValue();
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "Photo");
		n.addMessage(m);

		try {
			Photo pp = PhotoManager.getInstance().getPhoto(idString);
			appendPhoto(m,pp);
			appendPhotoBinaries(m, pp);
		} catch (Exception e) {
			throw new TipiException("Error connecting to Flickr",e);
		}
		return n;
	}
	
	
	private void appendPhotoBinaries(Message element, Photo photo) throws NavajoException {
		try {
			addProperty(element, "Small", new Binary(photo.getSmallAsInputStream(),false), Property.STRING_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			addProperty(element, "Medium", new Binary(photo.getMediumAsStream(),false), Property.STRING_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			addProperty(element, "Large", new Binary(photo.getLargeAsStream(),false), Property.STRING_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void appendThumbnailBinary(Message element, Photo photo) throws NavajoException {
		try {
			addProperty(element, "Small", new Binary(photo.getSmallAsInputStream(),false), Property.STRING_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	private void appendMediumPictureBinary(Message element, Photo photo) throws NavajoException {
		try {
			addProperty(element, "Medium", new Binary(photo.getMediumAsStream(),false), Property.STRING_PROPERTY);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	private void appendPhoto(Message element, Photo photo) throws NavajoException {
		addProperty(element, "Id", photo.getId(), Property.STRING_PROPERTY);
		addProperty(element, "Description", photo.getDescription(), Property.STRING_PROPERTY);
		addProperty(element, "DateTaken", photo.getDateTaken(), Property.DATE_PROPERTY);
		addProperty(element, "DateAdded", photo.getDateAdded(), Property.DATE_PROPERTY);
		addProperty(element, "DatePosted", photo.getDatePosted(), Property.DATE_PROPERTY);
		User owner = photo.getOwner();
		if(owner!=null) {
			addProperty(element, "OwnerName", owner.getRealName(), Property.STRING_PROPERTY);
			addProperty(element, "Owner", owner.getUsername(), Property.STRING_PROPERTY);
		}
		if(photo.hasGeoData()) {
			GeoData geoData = photo.getGeoData();
			addProperty(element, "Latitude", geoData.getLatitude(), Property.STRING_PROPERTY);
			addProperty(element, "Longitude", geoData.getLongitude(), Property.STRING_PROPERTY);
		}
		addProperty(element, "SmallUrl", photo.getSmallUrl(), Property.STRING_PROPERTY);
		addProperty(element, "MediumUrl", photo.getMediumUrl(), Property.STRING_PROPERTY);
		addProperty(element, "LargeUrl", photo.getLargeUrl(), Property.STRING_PROPERTY);
		addProperty(element, "Url", photo.getUrl(), Property.STRING_PROPERTY);
	
	}

	public String getConnectorId() {
		return "flickr";
	}


	private void addProperty(Message m, String name, Object value, String type) throws NavajoException {
		Navajo n = m.getRootDoc();
		Property p = NavajoFactory.getInstance().createProperty(n, name, type, null, 0, null, Property.DIR_IN);
		p.setAnyValue(value);
		m.addProperty(p);
	}

}
