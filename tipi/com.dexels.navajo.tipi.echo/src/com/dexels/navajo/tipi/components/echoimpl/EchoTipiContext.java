package com.dexels.navajo.tipi.components.echoimpl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.TaskQueueHandle;
import nextapp.echo2.app.Window;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webrender.Connection;
import nextapp.echo2.webrender.WebRenderServlet;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.echoimpl.impl.BrowserCookieManager;
import com.dexels.navajo.tipi.internal.BaseTipiErrorHandler;
import com.dexels.navajo.tipi.internal.FileResourceLoader;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;

import echopointng.command.JavaScriptEval;


public class EchoTipiContext extends TipiContext {
	private static final long serialVersionUID = -958549676651346217L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EchoTipiContext.class);
    
    private int zIndexCounter = 0;

    
    private boolean useAsyncThread = false;
    
    private final TipiEchoInstance myInstance;
    public EchoTipiContext(TipiEchoInstance t, EchoTipiContext parentContext) {
     	super(t, parentContext);
    	myInstance = t;
		ContainerContext context =(ContainerContext) t.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
    	Map parameterMap =  context.getInitialRequestParameterMap();
		logger.info("request: "+parameterMap);
		Set s = parameterMap.keySet();
		Iterator it = s.iterator();
		while (it.hasNext()) {
			Object type = it.next();
			String[] object = (String[]) parameterMap.get(type);
			logger.info("Key: "+type);
			if(object.length==1) {
				setGlobalValue((String) type, object[0]);
			}
		}
		
		setGlobalValue("sessionId",context.getSession().getId());
		
    	if(useAsyncThread) {
    		TaskQueueHandle handle =  t.createTaskQueue();
    		context.setTaskQueueCallbackInterval(handle, 3000);
    	}
		eHandler = new BaseTipiErrorHandler();
		eHandler.setContext(this);
		eHandler.initResource();

    	
    	setCookieManager(new BrowserCookieManager());
    }

    public void setSystemProperty(String name, String value) {
   	 super.setGlobalValue(name, value);
   	 
    }
    
 	public  String getSystemProperty(String name) {
 		return (String) getGlobalValue(name);
 	}
   
    public ApplicationInstance getInstance() {
    	return myInstance;
    }

   
    public void finalize() {
    	 Window w = (Window) getTopLevel();
    	 w.dispose();
    }
    
    public void setSplashInfo(String s) {
        /**
         * @todo Implement this com.dexels.navajo.tipi.TipiContext abstract
         *       method
         */
    }


	public void execute(Runnable r) throws TipiException {
		if (useAsyncThread) {
			ContainerContext context =(ContainerContext) getInstance().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
			TaskQueueHandle handle =  getInstance().createTaskQueue();

			if (null != context) {
				context.setTaskQueueCallbackInterval(handle, 3000);
			}
			getInstance().enqueueTask(handle, r);
		} else {
			r.run();
		}
	}
	
	public void enqueueExecutable(final TipiExecutable te) throws TipiException {
		Runnable r = new Runnable(){

			public void run() {
				try {
					te.performAction(null, null, 0);
				} catch (TipiException e) {
					logger.info("tipi exception caught");
					logger.error("Error: ",e);
					showInternalError(e.getMessage(),e);
				} catch (TipiBreakException e) {
					logger.error("Error: ",e);
				}
				
			}
		};
		// TODO Remove, document or do something with this strange name dependency
		if(Thread.currentThread().getName().indexOf("http")!=-1) {
			r.run();
		} else {
			
			
			
			ContainerContext context =(ContainerContext) getInstance().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
			
						TaskQueueHandle handle =  getInstance().createTaskQueue();
			
						if (null != context) {
							context.setTaskQueueCallbackInterval(handle, 3000);
						}
					getInstance().enqueueTask(handle, r);
			 
//					myThreadPool.enqueueExecutable(te);
		}
		
	}

    
    public void setSplashVisible(boolean b) {
      }

    public void setSplash(Object s) {

    }

    public void clearTopScreen() {

    }

    public int getPoolSize() {
        return 0;
    }

//    public void setServerContext(ApplicationInstance sc) {
//        myServerContext = sc;
//    }
//    public ApplicationInstance getServerContext() {
//        return myServerContext;
//    }

    public void doExit() {
    	shutdown();
    	ApplicationInstance ai = ApplicationInstance.getActive();
         if (ai instanceof TipiEchoInstance) {
             try {
				((TipiEchoInstance)ai).exitToUrl();
			} catch (MalformedURLException e) {
				logger.error("Error: ",e);
			}
 		} 
         }
    


    public URL getDynamicResourceBaseUrl(String path) throws MalformedURLException {
        Connection con = WebRenderServlet.getActiveConnection();
        HttpServletRequest req = con.getRequest();
        String url = req.getRequestURL().toString();
         URL u = new URL(url);
//        getServletContext().
        

        String contextname = con.getRequest().getContextPath();
        // deprecated the init. The context path should work
//        String base = (String) con.getServlet().getInitParameter("baseURL");
//        if(base==null) {
//        	base=contextname;
//        }
//      logger.info("Base ATTR: "+base);

        URL rootURL =  new URL(u.getProtocol(),getHostname(),u.getPort(),contextname+"/dynamic/"+path);
         return rootURL;
        
    }
	private String getHostname() throws MalformedURLException {
		Connection con = WebRenderServlet.getActiveConnection();
		HttpServletRequest req = con.getRequest();
		String url = req.getRequestURL().toString();

		URL u = new URL(url);
		String contextname = con.getRequest().getContextPath();
		// deprecated the init. The context path should work
		logger.info("CONTEXTNAME: "+contextname);
		String host = con.getServlet().getInitParameter("host");
//		if (base == null) {
//			base = contextname;
//		}
		if(host==null) {
			host = u.getHost();
		}

		return host;

	}
    
    public URL getContextURL() throws MalformedURLException {
        Connection con = WebRenderServlet.getActiveConnection();
        HttpServletRequest req = con.getRequest();
        String url = req.getRequestURL().toString();
        URL u = new URL(url);
//        getServletContext().
 
        String contextname = con.getRequest().getContextPath();
        // deprecated the init. The context path should work
        String base = con.getServlet().getInitParameter("baseURL");
        if(base==null) {
        	base=contextname;
        }
        URL rootURL =  new URL(u.getProtocol(),u.getHost(),u.getPort(),base);
        return rootURL;
    }

    public File getDynamicResourceBaseDir() {
        String pathString = "/dynamic";
        String realPath = getServletContext().getRealPath(pathString);
		File ff = new File(realPath);
        if (!ff.exists()) {
            ff.mkdirs();
        }
        return ff;
    }

    public ServletContext getServletContext() {
 
        ApplicationInstance app = ApplicationInstance.getActive();
        ContainerContext containerContext  
           = (ContainerContext) app.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
        ServletContext sc = containerContext.getSession().getServletContext();
        return sc;
    }
    
//    private static Map staticComponentMap = new HashMap();
    
   
    public int acquireHighestZIndex() {
    	return zIndexCounter++;
    }
    


	public void showInfo(String text, String title) {
		logger.info("ALERTING: "+text);
		text = text.replaceAll("\n", "\\\\n");
		logger.info("ALERTING2: "+text);
		ApplicationInstance.getActive().enqueueCommand(new JavaScriptEval("alert('" + text +"')"));
	//logger.info("Show info found: ");
//	Thread.dumpStack();
	}

	// TODO What's different?
	public void setTipiResourceLoader(String tipiCodeBase) throws MalformedURLException {
		if (tipiCodeBase != null) {
			if (tipiCodeBase.indexOf("http:/") != -1) {
				setTipiResourceLoader(new HttpResourceLoader(tipiCodeBase));
			} else {
				setTipiResourceLoader(new FileResourceLoader(new File(tipiCodeBase)));
			}
		} else {
			// nothing supplied. Use a file loader with fallback to classloader.
			setTipiResourceLoader(new FileResourceLoader(new File("tipi")));
		}
	}

	public void runAsyncInEventThread(Runnable r) {
		try {
			execute(r);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}


	public void runSyncInEventThread(Runnable r) {
		r.run();
	}

	
	public void showInternalError(String errorString, Throwable t) {
		showInfo("Internal error: "+errorString,"Problem:");
		super.showInternalError(errorString, t);
	}
	

	@Override
	public void showQuestion(String text, String title, String[] options) throws TipiBreakException {
		showInfo("showQuestion not supported", "Warning");
	}

//	public void showWarning(String text, String title) {
//		String parsed = text.replaceAll("\n", "[newline]\n");
//		super.showWarning(parsed, title);
//	}
	public String createExpressionUrl(String expression) {
		// TODO Auto-generated method stub
		Connection con = WebRenderServlet.getActiveConnection();
		HttpServletRequest req = con.getRequest();
		String url = req.getRequestURL().toString();
		
		return url + "?evaluate="+expression;
	}
}
