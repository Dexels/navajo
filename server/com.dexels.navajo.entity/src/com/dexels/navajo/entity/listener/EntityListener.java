package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AAAQuerier;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepository;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;

public class EntityListener extends HttpServlet {
    private static final long serialVersionUID = -6681359881499760460L;
    private final static Logger logger = LoggerFactory.getLogger(EntityListener.class);
    private final static String DEFAULT_OUTPUT_FORMAT = "json";
    private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json", "xml", "tml"));
   
    private EntityManager myManager;


    private int requestCounter = 0;
    private AAAQuerier authenticator;

    public void activate() {
        logger.info("Entity servlet component activated");
    }

    public void deactivate() {
        logger.info("Entity servlet component deactivated");
    }

    public void setEntityManager(EntityManager em) {
        myManager = em;
    }

    public void clearEntityManager(EntityManager em) {
        myManager = null;
    }
    
    public void addAuthenticator(AAAQuerier aa) {
        authenticator = aa;
    }

    public void removeAuthenticator(AAAQuerier aa) {
        authenticator = null;
    }
    

    /**
     * entity/Match?Match/MatchId=2312321
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Navajo result = null;
        Access access = null;
        String method = request.getMethod();
        String path = request.getPathInfo();
        long requestStart = System.currentTimeMillis();
       
        synchronized (this) {
            requestCounter++;
        }

        // Check for a .<format> in the URL - can be in RequestURI or QueryString
        String dotString = null;
        if (request.getRequestURI().contains(".")) {
            dotString = request.getRequestURI();
        } else if (request.getQueryString() != null && request.getQueryString().contains(".")) {
            dotString = request.getQueryString();
        }
        String urlOutput = null;
        if (dotString != null) {
            // The output format can be set by adding a trailing .<format> to
            // the URL. This overrules accept-encoding
            urlOutput = dotString.substring(dotString.lastIndexOf('.') + 1);
            if (!SUPPORTED_OUTPUT.contains(urlOutput)) {
                // unsupported format
                urlOutput = null;
            }
        }
        String outputFormat = getOutputFormat(request, urlOutput);
        
        // Only end-points are allowed to cache - no servers in between
        response.setHeader("Cache-Control", "private");
        response.setHeader("Content-Type", "application/" + outputFormat);

        HttpBasicAuthentication auth = new HttpBasicAuthentication(request);
        
        
        String entityName = path.substring(1);
        if (entityName.indexOf('.') > 0) {
            // Remove .<format> from entityName
            entityName = entityName.substring(0, entityName.indexOf('.'));
        }
        
        String tenant = determineInstanceFromRequest(request);
        if (tenant == null) {
            logger.warn("Entity request without tenant! This will result in some weird behavior when authenticating");
        }

        Navajo input = null;
        String etag = null;
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.equals("")) {
            ip = request.getRemoteAddr();
        }

        logger.info("Entity request {} ({}, {}, {})", entityName, method, auth.getUsername(),  ip);
        logger.debug("entity request count: {}", requestCounter);

        try {
            if (auth.getUsername() == null || auth.getPassword() == null) {
                throw new EntityException(EntityException.UNAUTHORIZED);
            }
            
            if (entityName.equals("")) {
                logger.error("No entity name found in request. Request URI: {}", request.getRequestURI());
                throw new EntityException(EntityException.BAD_REQUEST);
            }
            entityName = entityName.replace("/", ".");
            Entity e = myManager.getEntity(entityName);
            if (e == null) {
                // Requested entity not found
                logger.error("Requested entity not registred with entityManager!");
                throw new EntityException(EntityException.ENTITY_NOT_FOUND);
            }

            Message entityMessage = e.getMessage();

            // Get the input document
            if (method.equals("GET") || method.equals("DELETE")) {
                input = myManager.deriveNavajoFromParameterMap(e, request.getParameterMap());
            } else {
                JSONTML json = JSONTMLFactory.getInstance();
                json.setEntityTemplate(entityMessage.getRootDoc());
                try {
                    input = json.parse(request.getInputStream(), entityMessage.getName());
                } catch (Exception e1) {
                    logger.error("Error in parsing input JSON");
                    throw new EntityException(EntityException.BAD_REQUEST);
                }
            }
            
            // Create a header from the input
            Header header = NavajoFactory.getInstance().createHeader(input, "", auth.getUsername(), auth.getPassword(), -1);
            input.addHeader(header);
            
            // Create an access object for logging purposes
            Long startAuth =  System.currentTimeMillis();
            String scriptName = "entity/" + entityName.replace('.', '/');
            access = authenticateUser(input, tenant, scriptName, auth.getUsername(), auth.getPassword());
            access.created = new Date(requestStart);
            access.ipAddress = ip;
            access.authorisationTime = (int) (System.currentTimeMillis() - startAuth);
            
            header.setHeaderAttribute("parentaccessid", access.accessID);
            if (input.getMessage(entityMessage.getName()) == null) {
                logger.error("Entity name not found in input - format incorrect or bad request");
                throw new EntityException(EntityException.BAD_REQUEST);
            }
            
            // Merge input.
            input.getMessage(entityMessage.getName()).merge(entityMessage, true);

            etag = request.getHeader("If-Match");
            if (etag == null) {
                etag = request.getHeader("If-None-Match");
            }

            
            input.getMessage(entityMessage.getName()).setEtag(etag);

            Operation o = myManager.getOperation(entityName, method);
            o.setTenant(tenant);
            if (o.debugInput() || o.debugOutput()) {
                access.setDebugAll(true);
            }

            long startTime = System.currentTimeMillis();
            ServiceEntityOperation seo = new ServiceEntityOperation(myManager, DispatcherFactory.getInstance(), o);
            result = seo.perform(input);
            access.processingTime = (int) (System.currentTimeMillis() - startTime);
            
            if (access != null) {
                access.setExitCode(Access.EXIT_OK);
            }
        } catch (Exception ex) {
            result = handleException(ex, request, response);
            if (access != null) {
                access.setException(ex);
                access.setExitCode(Access.EXIT_EXCEPTION);
            }
        } 
        finally {
            writeOutput(result, response, outputFormat);
            if (access != null) {
                access.setFinished();
                access.setOutputDoc(result);
                NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(access));
            }
        }
        
    }



    private void writeOutput(Navajo result, HttpServletResponse response, String output) throws IOException, ServletException {
        if (result == null) {
            throw new ServletException("No output found");
        }
        if (result.getMessage("errors") != null) {
            String status = result.getMessage("errors").getProperty("Status").toString();
            if (status.equals("304")) {
                // No content
                logger.debug("Returning HTTP code 304 - not modified");
                return;
            }
        }
        if (output.equals("json")) {
            response.setHeader("content-type", "application/json");
            Writer w = new OutputStreamWriter(response.getOutputStream());
            JSONTML json = JSONTMLFactory.getInstance();
            json.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            try {
                json.format(result, w, true);
            } catch (Exception e) {
                logger.error("Error in writing entity output in JSON!");
                throw new ServletException("Error producing output");
            }
            w.close();
        } else if (output.equals("xml")) {
            response.setHeader("content-type", "text/xml");
            NavajoLaszloConverter.writeBirtXml(result, response.getWriter());
        } else {
            response.setHeader("content-type", "text/xml");
            result.write(response.getOutputStream());
        }
    }


    private String determineInstanceFromRequest(final HttpServletRequest req) {
        String requestInstance = req.getHeader("X-Navajo-Instance");
        if(requestInstance!=null) {
            return requestInstance;
        }
        return null;
    }

  
    // In case of an exception, we create a Navajo document with some messages
    // describing  the error. This allows us to output the exception in the 
    // format the user requested(eg.g JSON).
    private Navajo handleException(Exception ex, HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        Navajo result = null;
        logger.warn("Exception in handling entity request: {}. Going to try to handle it nicely.", ex);

        result = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(result, "errors");
        result.addMessage(m);
        m.addProperty(NavajoFactory.getInstance().createProperty(result, "Error", "boolean", "true", 1, null, null));
        if (ex instanceof EntityException) {
            response.setStatus(((EntityException) ex).getCode());
            int code = ((EntityException) ex).getCode();
            m.addProperty(
                    NavajoFactory.getInstance().createProperty(result, "Status", "string", String.valueOf(code), 1, null, null));
            m.addProperty(
                    NavajoFactory.getInstance().createProperty(result, "Message", "string", ex.getMessage(), 1, null, null));

        } else {
            response.setStatus(EntityException.SERVER_ERROR);
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string",
                    String.valueOf(EntityException.SERVER_ERROR), 1, null, null));
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string",
                    "Server error (" + ex.toString(), 1, null, null));
        }

        return result;
    }

    private String getOutputFormat(HttpServletRequest request, String urlOutput) {
        if (urlOutput != null) {
            // Explicit output in URL gets preference over Accept header
            return urlOutput;
        }

        String mimeResult = null;
        String header = request.getHeader("Accept");
        if (header != null) {
            try {
                String reqTypes[] = header.split(",");
                for (String reqType : reqTypes) {
                    String mime = reqType;
                    if (reqType.indexOf(';') > 0) {
                        mime = reqType.substring(0, reqType.indexOf(';'));
                    }

                    MimeType n = new MimeType(mime);
                    mimeResult = n.getSubType();
                    if (SUPPORTED_OUTPUT.contains(mimeResult)) {
                        // Found a supported type!
                        break;
                    }
                }

            } catch (MimeTypeParseException e) {
                logger.warn("MimeTypeParseException in getting mime-types from Accept header - using default output");
                mimeResult = DEFAULT_OUTPUT_FORMAT;
            }
        }

        if (!SUPPORTED_OUTPUT.contains(mimeResult)) {
            logger.info("No supported output format requested - using default output: {}", DEFAULT_OUTPUT_FORMAT);
            mimeResult = DEFAULT_OUTPUT_FORMAT;
        }
        return mimeResult;
    }
    
    private Access authenticateUser(Navajo inDoc, String tenant, String entity,
            String rpcUser, String rpcPassword) throws SystemException, AuthorizationException {
       Access access = new Access(1, 1, rpcUser, entity, "", "", "", null, false, null);
        access.setTenant(tenant);
        access.setInDoc(inDoc);
        appendGlobals(inDoc, tenant);
        
        if (tenant != null) {
            // logger.info("using multitenant: "+instance, new Exception());

            if (authenticator != null) {
                authenticator.performUserAuthorisation(tenant, rpcUser, rpcPassword, entity, inDoc, null, access);
            } else {
                logger.warn("No authenticator found for instance: {}", tenant);
            }           
        } else {
            logger.warn("No tenant defined - unable to authenticate!");
        }
        return access;
    }

    
    private void appendGlobals(Navajo inMessage, String tenant) {
        final GlobalManagerRepository globalManagerInstance = GlobalManagerRepositoryFactory.getGlobalManagerInstance();
        if (globalManagerInstance == null) {
            logger.info("No global manager found- not adding globals!");
            return;
        }
        GlobalManager gm = null;
        if (tenant == null) {
            gm = globalManagerInstance.getGlobalManager("default");
        } else {
            gm = globalManagerInstance.getGlobalManager(tenant);
        }
        
        if (gm != null) {
            gm.initGlobals(inMessage);
        }
    }
    
}
