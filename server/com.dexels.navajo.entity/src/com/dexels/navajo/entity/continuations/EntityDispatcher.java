package com.dexels.navajo.entity.continuations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AuthenticationMethod;
import com.dexels.navajo.authentication.api.AuthenticationMethodBuilder;
import com.dexels.navajo.authentication.api.LoginStatisticsProvider;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.entity.Entity;
import com.dexels.navajo.entity.EntityException;
import com.dexels.navajo.entity.EntityManager;
import com.dexels.navajo.entity.EntityMapper;
import com.dexels.navajo.entity.impl.ServiceEntityOperation;
import com.dexels.navajo.entity.util.EntityHelper;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;

public class EntityDispatcher {
    private static final String HTTP_METHOD_DELETE = "DELETE";
    private static final String HTTP_METHOD_GET = "GET";
    private static final Object HTTP_METHOD_OPTIONS = "OPTIONS";

    private final static Logger logger = LoggerFactory.getLogger(EntityDispatcher.class);
    private final static Logger statLogger = LoggerFactory.getLogger("stats");

    private final static String DEFAULT_OUTPUT_FORMAT = "json";
    private static final Set<String> SUPPORTED_OUTPUT = new HashSet<String>(Arrays.asList("json", "xml", "birt", "tml"));

    private EntityManager myManager;
    private AuthenticationMethodBuilder authMethodBuilder;
    private EntityMapper myMapper;


    public void run(EntityContinuationRunner runner) {
        Navajo result = null;
        Access access = null;
        String method = runner.getHttpRequest().getMethod();
        String path = runner.getHttpRequest().getPathInfo();
        if (path.startsWith("/entity")) {
            path = path.substring(7);
        }
        Navajo input = null;
        String inputEtag = null;
        boolean entityFound = false;
        try {

            // Check for a .<format> in the URL - can be in RequestURI
            String dotString = null;
            if (runner.getHttpRequest().getRequestURI().contains(".")) {
                dotString = runner.getHttpRequest().getRequestURI();
            }
            String urlOutput = null;
            if (dotString != null) {
                // The output format can be set by adding a trailing .<format>
                // to the URL. This overrules accept-encoding
                urlOutput = dotString.substring(dotString.lastIndexOf('.') + 1);
                if (!SUPPORTED_OUTPUT.contains(urlOutput)) {
                    // unsupported format
                    urlOutput = null;
                }
            }
            runner.setOutputFormat(getOutputFormat(runner.getHttpRequest(), urlOutput));

            String entityName = path.substring(1);
            if (entityName.indexOf('.') > 0) {
                // Remove .<format> from entityName
                entityName = entityName.substring(0, entityName.indexOf('.'));
            }
            
            // Auth is only required for methods other than OPTIONS
            String authHeader = null;
            String tenant = null;
            if (!method.equals(HTTP_METHOD_OPTIONS)) {
                authHeader = runner.getHttpRequest().getHeader("Authorization");
                if (authHeader == null) {
                    logger.warn("Missing authentication header!");
                    throw new EntityException(EntityException.UNAUTHORIZED);

                }
                
                tenant = determineInstanceFromRequest(runner.getHttpRequest());
                if (tenant == null && !authHeader.startsWith(AuthenticationMethod.OAUTH_IDENTIFIER)) {
                    // No tenant only supported for Oauth login
                    logger.warn("Entity request without tenant! This will result in some weird behavior when authenticating");
                    throw new EntityException(EntityException.UNAUTHORIZED);
                    
                }
            }

            String ip = runner.getHttpRequest().getHeader("X-Forwarded-For");
            if (ip == null || ip.equals("")) {
                ip = runner.getHttpRequest().getRemoteAddr();
            }

            if (entityName.equals("")) {
                logger.error("No entity name found in request. Request URI: {}", runner.getHttpRequest().getRequestURI());
                throw new EntityException(EntityException.BAD_REQUEST);
            }

            logger.debug("Entity request {} ({}, {})", entityName, method, ip);
            String queryString = runner.getHttpRequest().getQueryString();
            boolean debug = queryString != null && queryString.contains("developer=true");

            // Check entity mapper for this folder. If we find an entity mapped
            // to this folder named like our request, this is our entity
            entityName = entityName.replace("/", ".");
            String mappedEntity = null;
            if (debug) {
                mappedEntity = entityName;
            } else {
                String entitySubName = entityName.substring(entityName.lastIndexOf('.') + 1);
                String folder;
                if (entityName.equals(entitySubName)) {
                    folder = ""; // Root folder
                } else {
                    folder = path.substring(1, path.lastIndexOf("/"));
                }
                Set<String> entities = myMapper.getEntities(folder);
               
                for (String s : entities) {
                    String anEntity = s.substring(s.lastIndexOf('.') + 1);
                    if (anEntity.equals(entitySubName)) {
                        mappedEntity = s;
                        break;
                    }
                }
            }
            
            Entity e = myManager.getEntity(mappedEntity);

            if (e == null) {
                // Requested entity not found
                logger.warn("Requested entity not registred! {}", entityName);
                throw new EntityException(EntityException.ENTITY_NOT_FOUND);
            }
            entityFound = true;
            Message entityMessage = e.getMessage(Entity.DEFAULT_VERSION);

            String version = runner.getHttpRequest().getHeader("X-Navajo-Version");
            if (version == null) {
                logger.debug("Request on default entity");
                version = "0";
            } else if (!e.getMyVersionKeys().contains(version)) {
                logger.error("Request on unknown entity {} version {}", e.getName(), version);
                throw new EntityException(EntityException.UNKNOWN_VERSION);
            } else {
                logger.debug("Requesting entity {} version {}", e.getName(), version);
            }

            // Get the input document
            if (method.equals(HTTP_METHOD_OPTIONS) || method.equals(HTTP_METHOD_GET) || method.equals(HTTP_METHOD_DELETE)) {
                input = EntityHelper.deriveNavajoFromParameterMap(e, runner.getHttpRequest().getParameterMap(), version);
            } else {
                JSONTML json = JSONTMLFactory.getInstance();
                json.setEntityTemplate(entityMessage.getRootDoc());
                try {
                    input = json.parse(runner.getRequestInputStream(), entityMessage.getName());
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

            if (method.equals(HTTP_METHOD_OPTIONS)) {
                processGetOptions(e, runner.getHttpResponse());
                result = NavajoFactory.getInstance().createNavajo();
                return;
            }
            Operation entityOperation = myManager.getOperation(e.getName(), method);
            
            // Create an access object for logging purposes
            Long startAuth = System.currentTimeMillis();
            String scriptName = "entity/" + entityName.replace('.', '/');

            access = new Access(1, 1, "placeholder", scriptName, "", "", "", null, false, null);
            access.setOperation(entityOperation);
            access.ipAddress = ip;
            if (queryString != null) {
                access.addScriptLogging("Entity Query = " + queryString);
            }
            

            try {
                access = authenticateUser(input, tenant, access, authHeader);
            } catch (AuthorizationException auth) {
                logger.warn("Auth exception: ", auth);
                throw new EntityException(EntityException.UNAUTHORIZED);
            }
            entityOperation.setTenant(access.getTenant());
            
            // Create a header from the input
            Header header = NavajoFactory.getInstance().createHeader(input, "", access.rpcUser, access.rpcPwd, -1);

            String locale = runner.getHttpRequest().getHeader("X-Navajo-Locale");
            if (locale != null) {
                header.setHeaderAttribute("locale", locale);
            }

            String userAgent = runner.getHttpRequest().getHeader("User-Agent");
            if (userAgent != null) {
                header.setHeaderAttribute("user_agent", userAgent);
            }

            input.addHeader(header);

            access.created = new Date(runner.getStartedAt());
            access.authorisationTime = (int) (System.currentTimeMillis() - startAuth);
            access.setClientDescription("entity");
            header.setHeaderAttribute("parentaccessid", access.accessID);

            runner.getDispatcher().getAccessSet().add(access);
            
            
            // Update RPCName to reflect method
            access.rpcName = access.rpcName + "-" + method;
            inputEtag = runner.getHttpRequest().getHeader("If-Match");
            if (inputEtag == null) {
                inputEtag = runner.getHttpRequest().getHeader("If-None-Match");
            }

            input.getMessage(entityMessage.getName()).setEtag(inputEtag);

            if (entityOperation.debugInput() || entityOperation.debugOutput()) {
                access.setDebugAll(true);
            }

            long opStartTime = System.currentTimeMillis();
            ServiceEntityOperation seo = new ServiceEntityOperation(myManager, runner.getDispatcher(), entityOperation, version);
            result = seo.perform(input);

            if (result.getMessage(entityMessage.getName()) == null) {
                throw new EntityException(EntityException.ENTITY_NOT_FOUND);
            }

            if (method.equals(HTTP_METHOD_GET) && result.getMessage(entityMessage.getName()) != null) {
                runner.setOutputEtag(result.getMessage(entityMessage.getName()).generateEtag());
            }
            access.processingTime = (int) (System.currentTimeMillis() - opStartTime);
            if (access != null) {
                access.setExitCode(Access.EXIT_OK);
            }

        } catch (Throwable ex) {
            result = handleException(ex, runner.getHttpResponse());

            if (access != null) {
                boolean skipLogging = false;
                if (ex instanceof EntityException) {
                    EntityException e = (EntityException) ex;
                    if (e.getCode() == EntityException.NOT_MODIFIED) {
                        skipLogging = true;
                    } else if (e.getCode() == EntityException.ENTITY_NOT_FOUND && entityFound) {
                        skipLogging = true;
                        access.setExitCode(Access.EXIT_SCRIPT_NOT_FOUND);
                    } else if (e.getCode() == EntityException.UNAUTHORIZED) {
                        skipLogging = true;
                        access.setExitCode(Access.EXIT_AUTH_EXECPTION);
                    } else if (e.getCode() == EntityException.VALIDATION_ERROR) {
                        skipLogging = true;
                        access.setExitCode(Access.EXIT_VALIDATION_ERR);
	                } else if (e.getCode() == EntityException.CONFLICT) {
	                    skipLogging = true;
	                    access.setExitCode(Access.EXIT_ENTITY_CONFLICT);
	                } else if (e.getCode() == EntityException.BAD_REQUEST) {
                        skipLogging = true;
                        access.setExitCode(Access.EXIT_VALIDATION_ERR);
                    }
                }

                if (!skipLogging) {
                    access.setExitCode(Access.EXIT_EXCEPTION);
                    access.setException(ex);
                }
            }

        } finally {
            runner.setResponseNavajo(result);
            if (access != null) {
                runner.getDispatcher().getAccessSet().remove(access);
                access.setFinished();
                access.setOutputDoc(result);
                NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(access));
                statLogger.info("Finished {} ({}) in {}ms", access.accessID, access.getRpcName(), (System.currentTimeMillis() - runner.getStartedAt()));
            }

        }
    }

    private void processGetOptions(Entity e, HttpServletResponse response) {
        Map<String, Operation> operations = myManager.getOperations(e.getName());
        String allowed = "";
        for (Operation op : operations.values()) {
            allowed += op.getMethod() + ", ";
        }
        if (!allowed.equals("")) allowed = allowed.substring(0,  allowed.length() -2); // remove , at the end
        response.setHeader("Access-Control-Allow-Methods", allowed);
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,authorization");
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
    private Navajo handleException(Throwable ex, HttpServletResponse response) {
        Navajo result = null;
        if (ex instanceof EntityException) {
        	EntityException entityEx = (EntityException) ex;
        	if (entityEx.getCode() != EntityException.NOT_MODIFIED) {
        		logger.warn("EntityException in handling entity request: {}. Going to try to handle it nicely.", ex.getMessage());
        	}
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

            // Specifically check for condition errors
            Navajo n = ((EntityException) ex).getNavajo();
            if (n != null && n.getMessage("ConditionErrors") != null) {
                ArrayList<String> viols = new ArrayList<String>();
                for (Message message : n.getMessage("ConditionErrors").getAllMessages()) {
                    viols.add(message.getProperty("Id").getValue());
                }
                m.addProperty(
                        NavajoFactory.getInstance().createProperty(result, "ViolationCodes", "list", viols.toString(), 1, null, null));
            } else {
                m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string", ex.getMessage(), 1, null, null));
            }
            
        } else {
            response.setStatus(EntityException.SERVER_ERROR);
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Status", "string", String.valueOf(EntityException.SERVER_ERROR), 1, null, null));
            m.addProperty(NavajoFactory.getInstance().createProperty(result, "Message", "string", "Server error " + ex.toString(), 1, null,
                    null));
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
            logger.debug("No supported output format requested - using default output: {}", DEFAULT_OUTPUT_FORMAT);
            mimeResult = DEFAULT_OUTPUT_FORMAT;
        }
        return mimeResult;
    }

    private Access authenticateUser(Navajo inDoc, String tenant, Access access, String authHeader) throws AuthorizationException {
        access.setTenant(tenant);
        access.setInDoc(inDoc);

        if (LoginStatisticsProvider.reachedAbortThreshold(access.getRpcUser())) {
            logger.info("Refusing request from {} for {}  due to too many failed auth attempts", access.getIpAddress(), access.getRpcUser());
            throw new AuthorizationException(true, false, access.getRpcUser(), "Not authorized");
        }

       
        AuthenticationMethod authenticator = authMethodBuilder.getInstanceForRequest(authHeader);
        if (authenticator == null) {
            throw new AuthorizationException(false, false, null, "Missing authenticator");
        }
        
        authenticator.process(access);
        return access;
    }


    public void setEntityManager(EntityManager em) {
        myManager = em;
    }

    public void clearEntityManager(EntityManager em) {
        myManager = null;
    }

    public void setAuthenticationMethodBuilder(AuthenticationMethodBuilder amb) {
        this.authMethodBuilder = amb;
    }

    public void clearAuthenticationMethodBuilder(AuthenticationMethodBuilder eventAdmin) {
        this.authMethodBuilder = null;
    }

    public void setEntityMapper(EntityMapper mapp) {
        myMapper = mapp;
    }

    public void clearEntityMapper(EntityMapper mapp) {
        myMapper = null;
    }

}
