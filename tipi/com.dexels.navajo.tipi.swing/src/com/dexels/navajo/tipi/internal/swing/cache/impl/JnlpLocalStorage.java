package com.dexels.navajo.tipi.internal.swing.cache.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jnlp.BasicService;
import javax.jnlp.FileContents;
import javax.jnlp.PersistenceService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;

public class JnlpLocalStorage implements LocalStorage {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1); 

    private static final long DEFAULT_SIZE = 1000000;
    private final PersistenceService ps;
    private final BasicService bs;

    private final String cacheBase = "tipicache_";
    private final String relativePath;

    private final static Logger logger = LoggerFactory.getLogger(JnlpLocalStorage.class);

    private final String id;

    public JnlpLocalStorage(String relativePath, CookieManager cm, String id) throws UnavailableServiceException {
        this.id = id;
        ps = (PersistenceService) ServiceManager.lookup("javax.jnlp.PersistenceService");
        bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
        this.relativePath = relativePath.replaceAll("/", "_");

        String[] cacheMuffins;
        try {
            cacheMuffins = ps.getNames(getCacheBaseURL());
            logger.info("local muffins size: {}", cacheMuffins.length);
        } catch (Exception e) {
            logger.error("Error on filling muffins: ", e);
        }

    }

    private URL getCacheBaseURL() {
        return bs.getCodeBase();
    }

    private URL createMuffinUrl(String location) {
        String fixed = cacheBase + relativePath + location.replaceAll("/", "_");
        try {
            URL url = new URL(getCacheBaseURL(), fixed);
            return url;
        } catch (MalformedURLException e) {
            logger.error("Error detected", e);
            return null;
        }
    }

    @Override
    public void flush(String location) throws IOException {
        delete(location);
    }

    @Override
    public void flushAll() throws IOException {
        String[] cacheMuffins = ps.getNames(getCacheBaseURL());
        for (int i = 0; i < cacheMuffins.length; i++) {
            ps.delete(new URL(getCacheBaseURL(), cacheMuffins[i]));
        }
    }

    @Override
    public InputStream getLocalData(String location) {
        FileContents fc;
        try {
            URL muffinUrl = createMuffinUrl(location);
            
            fc = ps.get(muffinUrl);
            if (fc == null ||  fc.getLength() < 1) {
                logger.warn("Not found: {}", location);
                delete(location);
                return null;
            }
            return fc.getInputStream();
        } catch (MalformedURLException e) {
            logger.error("Error detected", e);
        } catch (FileNotFoundException e) {
            // regular cache miss
            logger.debug("not found: {} ", location);
            return null;
        } catch (IOException e) {
            logger.error("Error detected", e);

        }
        return null;
    }

    @Override
    public long getLocalModificationDate(String location) throws IOException {
        return 0;
    }

    @Override
    public URL getURL(String location, InputStream is) throws IOException {
        File f = File.createTempFile("tipiCache", "");
        OutputStream os = new FileOutputStream(f);
        copyResource(os, is);
        f.deleteOnExit();
        return f.toURI().toURL();
    }

    @Override
    public boolean hasLocal(String location) {
        FileContents fc = null;
        try {
            fc = ps.get(createMuffinUrl(location));
        } catch (MalformedURLException e) {
            // logger.debug("Malformed panic blues!");
            logger.error("Error detected",e);
        } catch (FileNotFoundException e) {
            // logger.debug("Local file: "+location+" not found!");
        } catch (IOException e) {
            logger.error("Error detected",e);
        }
        if (fc == null) {
            logger.debug("Has local for: {}: no",location);
            return false;
        } else {
            try {
                return fc.getLength() != 0;
            } catch (IOException e) {
                logger.error("Error detected",e);
            }
            logger.debug("Has local for {} failed",location);
            return false;
        }
    }

    @Override
    public void storeData(final String location, final InputStream data,  final Map<String, Object> metadata) throws IOException {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    FileContents fc = null;
                    URL muffinUrl = createMuffinUrl(location);
                    FileContents ff = null;
                  
                    long length = DEFAULT_SIZE;

                    try {
                        ff = ps.get(muffinUrl);
                    } catch (FileNotFoundException e) {
                        // logger.debug("Not found. fine.");
                    }
                    if (ff == null) {
                        ps.create(muffinUrl, length);
                    }

                    fc = ps.get(muffinUrl);
                    OutputStream os = fc.getOutputStream(true);
                    copyResource(os, data);
                } catch (IOException e) {
                    logger.error("Error on storing data for {}", location, e);
                }
               
                
            }
        });
       
    }

    private final void copyResource(OutputStream out, InputStream in) throws IOException {
        BufferedOutputStream bout = new BufferedOutputStream(out);
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) > -1) {
            bout.write(buffer, 0, read);
        }
        in.close();
        bout.flush();
        bout.close();
    }

    @Override
    public void delete(String location) {
        try {
            ps.delete(createMuffinUrl(location));
        } catch (IOException e) {
            logger.error("Error: ", e);
        }

    }

    public static void main(String[] args) {
        String muffinlocation = "tipicache_tipi_digest.properties";
        String[] splitted = muffinlocation.split("tipicache_");
        String muffinRelativePath = splitted[1].substring(0, splitted[1].indexOf("_") +1 );
        String path = splitted[1].substring(splitted[1].indexOf("_") + 1);
        String location = path.replaceAll("_", "/");
        System.out.println(muffinRelativePath);
    }
}
