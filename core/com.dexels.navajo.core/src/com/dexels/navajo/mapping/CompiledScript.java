package com.dexels.navajo.mapping;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.CompiledScriptInterface;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.NavajoClassSupplier;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionData;
import com.dexels.navajo.server.ConditionErrorException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class CompiledScript implements CompiledScriptMXBean, Mappable, CompiledScriptInterface {
    private final static String UNSET_CONFIG_MODE = "unset";
    protected NavajoClassSupplier classLoader;
    private final HashMap functions = new HashMap();
    private String configDebugMode = UNSET_CONFIG_MODE;
    /**
     * Fields accessable by webservice via Mappable interface.
     */
    protected String stackTrace;
    protected String threadName;
    protected String accessId;
    protected long runnningTime;
    protected boolean waiting;
    protected String lockName;
    protected String lockOwner;
    protected String lockClass;

    protected MappableTreeNode currentMap = null;
    protected final Stack treeNodeStack = new Stack();
    // protected Navajo outDoc = null;
    protected Navajo inDoc = null;
    protected Message currentOutMsg = null;
    protected Access myAccess = null;
    protected final Stack<Message> outMsgStack = new Stack();
    protected final Stack paramMsgStack = new Stack();
    protected Message currentParamMsg = null;
    protected Message currentInMsg = null;

    protected Selection currentSelection = null;
    protected final Stack inMsgStack = new Stack();
    protected Object sValue = null;
    protected Operand op = null;
    protected String type = "";
    protected String subtype = "";
    protected Property p = null;
    protected LazyArray la = null;
    protected String fullMsgName = "";
    protected boolean matchingConditions = false;
    protected HashMap evaluatedAttributes = null;
    protected boolean inSelectionRef = false;
    protected final Stack inSelectionRefStack = new Stack();
    protected int count = 1;

    protected String[] conditionArray = null;
    protected String[] ruleArray = null;
    protected String[] codeArray = null;
    protected String[] descriptionArray = null;

    private CompiledScriptFactory factory;

    private HashSet<Lock> acquiredLocks = new HashSet<Lock>();

    private final static Logger logger = LoggerFactory.getLogger(CompiledScript.class);

    /**
     * This HashMap is used for user defined expressions in <definitions>
     * section of a script.
     */
    protected HashMap userDefinedRules = new HashMap();

    protected boolean kill = false;

    @SuppressWarnings("unused")
    private ThreadInfo myThread = null;

    @Override
    public String getScriptName() {
        return getClass().getName();
    }

    @Override
    public String getUser() {
        return myAccess.rpcUser;
    }

    @Override
    public String getAccessId() {
        return myAccess.accessID;
    }

    @Override
    public Navajo getInDoc() {
        return inDoc;
    }

    @Override
    public void setInDoc(Navajo inDoc) {
        this.inDoc = inDoc;
    }

    @Override
    public String getThreadName() {
        return DispatcherFactory.getInstance().getThreadName(myAccess);
    }

    @Override
    public boolean isWaiting() {
        return getWaiting();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#getWaiting()
     */
    @Override
    public boolean getWaiting() {
        return false;
    }

    @Override
    public String getLockName() {
        return "";
    }

    @Override
    public String getLockOwner() {
        return "";
    }

    @Override
    public String getLockClass() {
        return "";
    }

    @Override
    public String getStackTrace() {

        StringBuffer stackTrace = new StringBuffer();
        StackTraceElement[] elt = myAccess.getThread().getStackTrace();
        for (int i = 0; i < elt.length; i++) {
            stackTrace.append(elt[i].getClassName() + "." + elt[i].getMethodName() + " (" + elt[i].getFileName() + ":"
                    + elt[i].getLineNumber() + ")\n");
        }
        return stackTrace.toString();
    }

    @Override
    public long getRunningTime() {
        return System.currentTimeMillis() - myAccess.created.getTime();
    }

    @Override
    public void kill() {
        logger.warn("Calling kill from JMX");
        myAccess.getCompiledScript().setKill(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#setKill(boolean)
     */
    @Override
    public void setKill(boolean b) {
        kill = b;
        myAccess.getThread().interrupt();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#getKill()
     */
    @Override
    public boolean getKill() {
        return kill;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#setClassLoader(com
     * .dexels.navajo.script.api.NavajoClassSupplier)
     */
    @Override
    public void setClassLoader(NavajoClassSupplier loader) {
        this.classLoader = loader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getClassLoader()
     */
    @Override
    public NavajoClassSupplier getClassLoader() {
        return this.classLoader;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#finalBlock(com.dexels
     * .navajo.api.Access)
     */
    @Override
    public abstract void finalBlock(Access access) throws Exception;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#setValidations()
     */
    @Override
    public abstract void setValidations();

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#dumpRequest()
     */
    @Override
    public void dumpRequest() {
        String scriptName = getScriptName();
        if (debugRequest() &&  System.getenv("DEBUG_SCRIPTS") != null &&  System.getenv("DEBUG_SCRIPTS").equals("true") ) {
            System.err.println(" --------- BEGIN NAVAJO REQUEST --------- "  + scriptName);
            myAccess.getInDoc().write(System.err);
            System.err.println("--------- END NAVAJO REQUEST --------- " + scriptName);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#dumpRequest()
     */
    @Override
    public void dumpResponse() {
        String scriptName = getScriptName();
        if (System.getenv("DEBUG_SCRIPTS") != null &&  System.getenv("DEBUG_SCRIPTS").equals("true") ) {
            if (debugResponse()) {
                System.err.println(" --------- BEGIN NAVAJO RESPONSE --------- "  + scriptName);
                myAccess.getOutputDoc().write(System.err);
                System.err.println("--------- END NAVAJO RESPONSE --------- " + scriptName);
            }
           

            String consoleOutput = myAccess.getConsoleOutput();
            if (consoleOutput != null && !consoleOutput.trim().equals("")) {
                System.err.println(" --------- NAVAJO CONSOLE OUTPUT: " + getScriptName());
                System.err.println(consoleOutput);
                System.err.println("--------- NAVAJO CONSOLE OUTPUT");
                
            }
        }
    }
    
    @Override
    public boolean isDebugAll() {
        if (!configDebugMode.equals(UNSET_CONFIG_MODE)) {
            return debugAll(configDebugMode);
        }
        return debugAll(getScriptDebugMode());
    }
    @Override
    public boolean debugRequest() {
        if (!configDebugMode.equals(UNSET_CONFIG_MODE)) {
            return debugRequest(configDebugMode);
        }
        return  debugRequest(getScriptDebugMode());
    }
    
    @Override
    public boolean debugResponse() {
        if (!configDebugMode.equals(UNSET_CONFIG_MODE)) {
            return debugResponse(configDebugMode);
        } 
        return debugResponse(getScriptDebugMode());
    }
    
    
    public boolean debugRequest(String type) {
        return (type.indexOf("request") != -1) ||  debugAll(type);
    }
    
 
    public boolean debugResponse(String type) {
        return (type.indexOf("response") != -1) || debugAll(type);
    }
    
    public boolean debugAll(String type) {
        return type.indexOf("true") != -1;
    }
    
    @Override
    public void setConfigDebugMode(String mode) {
        if (mode != null && !mode.equals("")) {
            this.configDebugMode = mode;
        }
    }
    
    @Override
    public String getScriptDebugMode() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#setDependencies()
     */
    @Override
    public void setDependencies() {
        // Example:
        // dependentObjects.add( new
        // IncludeDependency(IncludeDependency.getScriptTimeStamp("MyScript1"),
        // "MyScript1"));
        // dependentObjects.add( new
        // IncludeDependency(IncludeDependency.getScriptTimeStamp("MyScript2"),
        // "MyScript2"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getDependentObjects
     * ()
     */
    @Override
    public ArrayList<Dependency> getDependentObjects() {
        return new ArrayList<Dependency>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getDependencies()
     */
    @Override
    public Dependency[] getDependencies() {

        Dependency[] all = new Dependency[getDependentObjects().size()];
        all = getDependentObjects().toArray(all);
        // for ( int i = 0; i < all.length; i++ ) {
        // // Normalize id's
        // if ( all[i] instanceof AdapterFieldDependency ) {
        // all[i].setId( ((AdapterFieldDependency) all[i]).getEvaluatedId() );
        // }
        // }
        return all;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#run(com.dexels.navajo
     * .api.Access)
     */
    @Override
    public final void run(Access access) throws Exception {

        myAccess = access;
        long start = System.currentTimeMillis();

        try {
            dumpRequest();
            setValidations();

            currentParamMsg = access.getInDoc().getMessage("__parms__");

            ConditionData[] conditions = getValidationRules(access);
            boolean conditionsFailed = false;
            if (conditions != null && conditions.length > 0) {
                Navajo outMessage = access.getOutputDoc();
                Message[] failed = checkValidationRules(conditions, access.getInDoc(), outMessage, access);
                if (failed != null) {
                    conditionsFailed = true;
                    access.setExitCode(Access.EXIT_VALIDATION_ERR);
                    Message msg = NavajoFactory.getInstance().createMessage(outMessage, "ConditionErrors");
                    outMessage.addMessage(msg);
                    msg.setType(Message.MSG_TYPE_ARRAY);
                    for (int i = 0; i < failed.length; i++) {
                        msg.addMessage(failed[i]);
                    }
                }
            }
            if (!conditionsFailed) {
                try {
                    execute(access);
                    access.setExitCode(Access.EXIT_OK);
                } catch (com.dexels.navajo.mapping.BreakEvent be) {
                    access.setExitCode(Access.EXIT_BREAK);
                    throw be;
                } catch (UserException e) {
                    access.setExitCode(Access.EXIT_USEREXCEPTION);
                    throw e;
                } catch (ConditionErrorException e) {
                    access.setExitCode(Access.EXIT_VALIDATION_ERR);
                    throw e;
                } catch (Exception e) {
                    access.setExitCode(Access.EXIT_EXCEPTION);
                    throw e;
                } finally {
                    // TODO Will fail epically with continuations. Just because
                    // this thread will die doesn't mean this scirpt is done.
                    finalBlock(access);
                }
            }
        } finally {
            dumpResponse();
            // Release acquired locks.
            for (Lock l : acquiredLocks) {
                try {
                    l.unlock();
                } catch (Throwable t) {

                }
            }
            acquiredLocks.clear();
            access.processingTime = (int) (System.currentTimeMillis() - start);
        }
    }

    protected Access getAccess() {
        return myAccess;
    }

    /**
     * Deprecated method to check validation errors. Use <validations> block
     * inside webservice script instead.
     *
     * @param conditions
     * @param inMessage
     * @param outMessage
     * @return
     * @throws NavajoException
     * @throws SystemException
     * @throws UserException
     */
    private final Message[] checkValidationRules(ConditionData[] conditions, Navajo inMessage, Navajo outMessage,
            Access a) throws NavajoException, SystemException, UserException {

        if (conditions == null) {
            return null;
        }

        ArrayList<Message> messages = new ArrayList<Message>();
        int index = 0;

        for (int i = 0; i < conditions.length; i++) {
            ConditionData condition = conditions[i];
            boolean valid = false;

            try {
                valid = com.dexels.navajo.parser.Condition.evaluate(condition.condition, inMessage, a);
            } catch (com.dexels.navajo.parser.TMLExpressionException ee) {
                throw new UserException(-1, "Invalid condition: " + ee.getMessage(), ee);
            }

            if (!valid) {

                String eval = com.dexels.navajo.parser.Expression.replacePropertyValues(condition.condition, inMessage);
                Message msg = NavajoFactory.getInstance().createMessage(outMessage, "failed" + (index++));
                Property prop0 = NavajoFactory.getInstance().createProperty(outMessage, "Id", Property.STRING_PROPERTY,
                        condition.id + "", 0, "", Property.DIR_OUT);

                // Evaluate comment as expression.
                String description = "";
                try {
                    Operand o = Expression.evaluate(condition.comment, inMessage);
                    description = o.value + "";
                } catch (Exception e) {
                    description = condition.comment;
                }
                Property prop1 = NavajoFactory.getInstance().createProperty(outMessage, "Description",
                        Property.STRING_PROPERTY, description, 0, "", Property.DIR_OUT);
                
                msg.addProperty(prop0);
                msg.addProperty(prop1);
                
                if (System.getenv("DEBUG_SCRIPTS") != null && System.getenv("DEBUG_SCRIPTS").equals("true")) {
                    Property prop2 = NavajoFactory.getInstance().createProperty(outMessage, "FailedExpression",
                            Property.STRING_PROPERTY, condition.condition, 0, "", Property.DIR_OUT);
                    Property prop3 = NavajoFactory.getInstance().createProperty(outMessage, "EvaluatedExpression",
                            Property.STRING_PROPERTY, eval, 0, "", Property.DIR_OUT);
                    msg.addProperty(prop2);
                    msg.addProperty(prop3);
                }

                messages.add(msg);
            }
        }

        if (messages.size() > 0) {
            Message[] msgArray = new Message[messages.size()];

            messages.toArray(msgArray);
            return msgArray;
        } else {
            return null;
        }
    }

    private final ConditionData[] getValidationRules(Access access) throws Exception {
        Navajo inMessage = access.getInDoc();
        if (conditionArray != null) {
            List<ConditionData> conditions = new ArrayList<ConditionData>();
            for (int i = 0; i < conditionArray.length; i++) {
                boolean check = (conditionArray[i].equals("") ? true : Condition.evaluate(conditionArray[i], inMessage,
                        access));
                if (check) {
                    ConditionData cd = new ConditionData();
                    cd.id = codeArray[i];
                    cd.comment = (descriptionArray != null ? descriptionArray[i] : "empty");
                    cd.condition = ruleArray[i];
                    conditions.add(cd);
                }
            }
            ConditionData[] conditionArray = new ConditionData[conditions.size()];
            conditionArray = conditions.toArray(conditionArray);
            return conditionArray;
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#execute(com.dexels
     * .navajo.api.Access)
     */
    @Override
    public abstract void execute(Access access) throws Exception, NavajoDoneException;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getFunction(java
     * .lang.String)
     */
    @Override
    public final Object getFunction(String name) throws Exception {
        Object f = functions.get(name);
        if (f != null) {
            return f;
        }
        // Ignore
        f = Class.forName(name, false, this.getClass().getClassLoader()).newInstance();
        functions.put(name, f);
        return f;
    }

 
    protected Map<String, Object> getEvaluationParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Expression.ACCESS, myAccess);
        return params;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#findMapByPath(java
     * .lang.String)
     */
    @Override
    public final Object findMapByPath(String path) {

        StringTokenizer st = new StringTokenizer(path, "/");
        int count = 0;
        while (st.hasMoreTokens()) {
            String element = st.nextToken();
            if (!"..".equals(element)) {
                logger.warn("Huh? : " + element);
            }
            count++;
        }

        Object m = ((MappableTreeNode) treeNodeStack.get(treeNodeStack.size() - count)).getMyMap();

        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#releaseCompiledScript
     * ()
     */
    @Override
    public void releaseCompiledScript() {
        myAccess = null;
        classLoader = null;
    }

    @Override
    public void load(Access access) throws MappableException, UserException {
    }

    @Override
    public void store() throws MappableException, UserException {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#hasDirtyDependencies
     * (com.dexels.navajo.api.Access)
     */
    @Override
    public boolean hasDirtyDependencies(Access a) {

        if (getDependentObjects() == null) {
            return false;
        }
        for (int i = 0; i < getDependentObjects().size(); i++) {
            Dependency dep = getDependentObjects().get(i);
            if (dep != null && dep.needsRecompile()) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getDescription()
     */
    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public Stack<MappableTreeNode> getTreeNodeStack() {
        return treeNodeStack;
    }

    @Override
    public Stack<Message> getOutMsgStack() {
        return outMsgStack;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#getAuthor()
     */
    @Override
    public String getAuthor() {
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#getScriptType()
     */
    @Override
    public String getScriptType() {
        return "tsl";
    }



    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.script.api.CompiledScriptInterface#getCurrentMap()
     */
    @Override
    public MappableTreeNode getCurrentMap() {
        return currentMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getCurrentOutMsg()
     */
    @Override
    public Message getCurrentOutMsg() {
        return currentOutMsg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getCurrentParamMsg()
     */
    @Override
    public Message getCurrentParamMsg() {
        return currentParamMsg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getCurrentInMsg()
     */
    @Override
    public Message getCurrentInMsg() {
        return currentInMsg;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#getCurrentSelection
     * ()
     */
    @Override
    public Selection getCurrentSelection() {
        return currentSelection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.dexels.navajo.script.api.CompiledScriptInterface#setFactory(com.dexels
     * .navajo.script.api.CompiledScriptFactory)
     */
    @Override
    public void setFactory(CompiledScriptFactory factory) {
        this.factory = factory;
    }

    protected Object getResource(String name) {
        if (this.factory != null) {
            return factory.getResource(name);
        }
        return null;
    }
    
    protected void writeToLog(String msg) {
        myAccess.addScriptLogging(msg);
        logger.info(myAccess.getRpcName() + " (" + myAccess.getAccessID() + "): " +  msg);
    }

    /**
     * Get a lock for the synchronized block.
     * 
     */
    public Lock getLock(String user, String service, String key) throws Exception {
        if (user == null && service == null && key == null) {
            throw new Exception("Either user or service or both should be specified.");
        }
        String lockName = user + "-" + service + "-" + key;
        logger.debug("lockname: " + lockName);
        Lock l = TribeManagerFactory.getInstance().getLock(lockName);
 
        return l;
    }
    
    public void acquiredLock(Lock l ) {
        acquiredLocks.add(l);
    }

    public void releaseLock(Lock l) {
    	try {
    		l.unlock();
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    	} finally {
    		acquiredLocks.remove(l);
    	}
    }
    
}
