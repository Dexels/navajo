package com.dexels.navajo.entity.listener;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.LoginStatisticsProvider;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityAuthenticator;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.global.GlobalManager;
import com.dexels.navajo.server.global.GlobalManagerRepository;
import com.dexels.navajo.server.global.GlobalManagerRepositoryFactory;

public class EntityListener extends HttpServlet {
    private static final long serialVersionUID = -6681359881499760460L;
    private final static Logger logger = LoggerFactory.getLogger(EntityListener.class);
    private final static Logger statLogger = LoggerFactory.getLogger("stats");

    private final static String DEFAULT_OUTPUT_FORMAT = "json";
    private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json", "xml", "tml"));

    private EntityManager myManager;
    private Map<String, EntityAuthenticator> authenticators = new HashMap<>();
    private int requestCounter = 0;


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

  
    
    public void addEntityAuthenticator(EntityAuthenticator a) {
        authenticators.put(a.getIdentifier(),  a);
    }
    
    public void removeEntityAuthenticator(EntityAuthenticator a) {
        authenticators.remove(a.getIdentifier());
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
        String inputEtag = null;
        String outputEtag = null;

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.equals("")) {
            ip = request.getRemoteAddr();
        }
        
        try {
            if (entityName.equals("")) {
                logger.error("No entity name found in request. Request URI: {}", request.getRequestURI());
                throw new EntityException(EntityException.BAD_REQUEST);
            }
            
            EntityAuthenticator auth = getAuthenticator(request);
            if (auth.getUsername() == null || auth.getPassword() == null) {
                throw new EntityException(EntityException.UNAUTHORIZED);
            }

            synchronized (this) {
                requestCounter++;
            }
            logger.info("Entity request {} ({}, {}, {})", entityName, method, auth.getUsername(), ip);
            logger.debug("entity request count: {}", requestCounter);

            
            entityName = entityName.replace("/", ".");
            Entity e = myManager.getEntity(entityName);
            if (e == null) {
                // Requested entity not found
                logger.warn("Requested entity not registred with entityManager! {}", entityName);
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
            
            // Check if input contains the entityMessage
            if (input.getMessage(entityMessage.getName()) == null) {
                logger.error("Entity name not found in input - format incorrect or bad request");
                throw new EntityException(EntityException.BAD_REQUEST);
            }

            // Create a header from the input
            Header header = NavajoFactory.getInstance().createHeader(input, "", auth.getUsername(), auth.getPassword(), -1);
            input.addHeader(header);

            // Create an access object for logging purposes
            Long startAuth = System.currentTimeMillis();
            String scriptName = "entity/" + entityName.replace('.', '/');
            access = authenticateUser(auth, input, tenant, e, scriptName,  ip);
            access.created = new Date(requestStart);
            access.authorisationTime = (int) (System.currentTimeMillis() - startAuth);
            header.setHeaderAttribute("parentaccessid", access.accessID);
          
          
            inputEtag = request.getHeader("If-Match");
            if (inputEtag == null) {
                inputEtag = request.getHeader("If-None-Match");
            }

            input.getMessage(entityMessage.getName()).setEtag(inputEtag);

            Operation o = myManager.getOperation(entityName, method);
            o.setTenant(tenant);
            if (o.debugInput() || o.debugOutput()) {
                access.setDebugAll(true);
            }
            auth.checkAuthenticationFor(o);

            long opStartTime = System.currentTimeMillis();
            ServiceEntityOperation seo = new ServiceEntityOperation(myManager, DispatcherFactory.getInstance(), o);
            result = seo.perform(input);
            
            if (result.getMessage(entityMessage.getName()) == null) {
                throw new EntityException(EntityException.ENTITY_NOT_FOUND);
            }
            
            if (method.equals("GET") && result.getMessage(entityMessage.getName()) != null) {
                outputEtag = result.getMessage(entityMessage.getName()).generateEtag();
            }
            access.processingTime = (int) (System.currentTimeMillis() - opStartTime);

            if (access != null) {
                access.setExitCode(Access.EXIT_OK);
            }
        } catch (Exception ex) {
            result = handleException(ex, request, response);
            if (access != null) {
                access.setException(ex);
                access.setExitCode(Access.EXIT_EXCEPTION);
            }
        } finally {
            writeOutput(result, response, outputFormat, outputEtag);
            if (access != null) {
                access.setFinished();
                access.setOutputDoc(result);
                NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(access));
                statLogger.info("Finished {} ({}) in {}ms", access.accessID, access.getRpcName(), (System.currentTimeMillis() - requestStart));
            }

        }

    }

    private EntityAuthenticator getAuthenticator(HttpServletRequest request) throws EntityException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.equals("")) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(authHeader);
        if (st.hasMoreTokens()) {
             String id = st.nextToken();
             EntityAuthenticator a =  authenticators.get(id);
             if (a != null) {
                 return a.getInstance(request);
             } else {
                 logger.warn("Unsupported authorization scheme: {}", id);
                 throw new EntityException(EntityException.UNAUTHORIZED);
             }
        }
        return null;
    }

    private void writeOutput(Navajo result, HttpServletResponse response, String output, String etag) throws IOException, ServletException {
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
        if (etag != null) {
            response.setHeader("etag", etag);
        }
        if (output.equals("json")) {
            response.setHeader("content-type", "application/json");
            Writer w = new OutputStreamWriter(response.getOutputStream());
            JSONTML json = JSONTMLFactory.getInstance();
            json.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            try {
                json.format(result, w, true);
            } catch (Exception e) {
                logger.error("Error in writing entity output in JSON!", e);
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
        if (requestInstance != null) {
            return requestInstance;
        }
        return null;
    }

    // In case of an exception, we create a Navajo document with some messages
    // describing the error. This allows us to output the exception in the
    // format the user requested(eg.g JSON).
    private Navajo handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Navajo result = null;
        if (ex instanceof EntityException) {
            logger.warn("EntityException in handling entity request: {}. Going to try to handle it nicely.", ex.getMessage());
        } else {
            logger.error("Exception in handling entity request. Going to try to handle it nicely.", ex);

        }

        result = NavajoFactory.getInstance().createNavajo();
        Message m = NavajoFactory.getInstance().createMessage(result, "errors");
        result.addMessage(m);
        m.addProperty(NavajoFactory.getInstance().createProperty(result, "Error", "boolean", "true", 1, null, null));
        if (ex instanceof EntityException) {
            response.setStatus(((EntityException) ex).getCode());
            int code = ((EntityException) ex).getCode();
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string", String.valueOf(code), 1, null, null));
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string", ex.getMessage(), 1, null, null));

        } else {
            response.setStatus(EntityException.SERVER_ERROR);
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string", String.valueOf(EntityException.SERVER_ERROR), 1, null, null));
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string", "Server error (" + ex.toString(), 1, null, null));
        }
        response.addHeader("Connection", "close");
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

    private Access authenticateUser(EntityAuthenticator auth, Navajo inDoc, String tenant, Entity entity, String scriptname, String ip)
            throws AuthorizationException {
        Access access = new Access(1, 1, auth.getUsername(), scriptname, "", "", "", null, false, null);
        access.setTenant(tenant);
        access.ipAddress = ip;
        access.setInDoc(inDoc);
        access.rpcPwd = auth.getPassword();
        appendGlobals(inDoc, tenant);
        
        if (LoginStatisticsProvider.reachedAbortThreshold(auth.getUsername(), access.getIpAddress())) {
            logger.info("Refusing request from {} for {}  due to too many failed auth attempts", access.getIpAddress(), auth.getUsername());
            throw new AuthorizationException(true, false, auth.getUsername(), "Not authorized");
        }
        
        if (LoginStatisticsProvider.reachedRateLimitThreshold(auth.getUsername(), access.getIpAddress())) {
            logger.info("Delaying request from {} for {} due to too many failed auth attempts", access.getIpAddress(), auth.getUsername());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return null;
            }
        }
        
        if (!auth.isAuthenticated(access)) {
            throw new AuthorizationException(true, false, auth.getUsername(), "Not authenticated");
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
