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
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.ConditionData;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

@SuppressWarnings("unchecked")
public abstract class CompiledScript implements CompiledScriptMXBean, Mappable  {

  protected NavajoClassSupplier classLoader;
  private final HashMap functions = new HashMap();

  /**
   * Fields accessable by webservice via Mappable interface.
   */
  public String stackTrace;
  public String threadName;
  public String accessId;
  public long runnningTime;
  public boolean waiting;
  public String lockName;
  public String lockOwner;
  public String lockClass;
  public boolean debugAll = false;
  
  public MappableTreeNode currentMap = null;
  public final Stack treeNodeStack = new Stack();
//  public Navajo outDoc = null;
  public Navajo inDoc = null;
  public Message currentOutMsg = null;
  public Access myAccess = null;
  public final Stack outMsgStack = new Stack();
  public final Stack paramMsgStack = new Stack();
  public Message currentParamMsg = null;
  public Message currentInMsg = null;
  

  public Selection currentSelection = null;
  public final Stack inMsgStack = new Stack();
  public Object sValue = null;
  public Operand op = null;
  public String type = "";
  public String subtype = "";
  public Property p = null;
  public LazyArray la = null;
  public String fullMsgName = "";
  public boolean matchingConditions = false;
  public HashMap evaluatedAttributes = null;
  public boolean inSelectionRef = false;
  public final Stack inSelectionRefStack = new Stack();
  public int count = 1;

  public String[] conditionArray = null;
  public String[] ruleArray = null;
  public String[] codeArray = null;
  public String[] descriptionArray = null;

  
private final static Logger logger = LoggerFactory
		.getLogger(CompiledScript.class);

  /**
   * This HashMap is used for user defined expressions in <definitions> section of a script.
   */
  public HashMap userDefinedRules = new HashMap();
    
  protected boolean kill = false;

  @SuppressWarnings("unused")
  private ThreadInfo myThread = null;
  @SuppressWarnings("unused")

  public String getScriptName() {
	  return getClass().getName();
  }
  
  public String getUser() {
	  return myAccess.rpcUser;
  }
  
  public String getAccessId() {
	  return myAccess.accessID;
  }
  
  public String getThreadName() {
	  return DispatcherFactory.getInstance().getThreadName(myAccess);
  }
  
  public boolean isWaiting() {
	  return getWaiting();
  }
  
  public boolean getWaiting() {
	  return false;
  }
  
  public String getLockName() {
	  return "";
  }
  
  public String getLockOwner() {
	  return "";
  }
  
  public String getLockClass() {
	  return "";
  }
  
  public String getStackTrace() {

	  StringBuffer stackTrace = new StringBuffer();
	  StackTraceElement [] elt = myAccess.getThread().getStackTrace();
	  for (int i = 0; i < elt.length; i++) {
		  stackTrace.append(elt[i].getClassName()+"."+elt[i].getMethodName() + " (" + elt[i].getFileName() + ":" + elt[i].getLineNumber() + ")\n");
	  }
	  return stackTrace.toString();
  }
  
  public long getRunningTime() {
	  return System.currentTimeMillis() -  myAccess.created.getTime();
  }
  
  public void kill() {
	  System.err.println("Calling kill from JMX");
	  myAccess.getCompiledScript().setKill(true);
  }
  
  public void setKill(boolean b) {
	  kill = b;
	  myAccess.getThread().interrupt();
  }

  public boolean getKill() {
    return kill;
  }

  public void setClassLoader(NavajoClassSupplier loader) {
	  this.classLoader = loader;
  }
  
  public NavajoClassSupplier getClassLoader() {
	  return this.classLoader;
  }

  public abstract void finalBlock(Access access) throws Exception;

  /**
   * Generated code for validations.
   */
  public abstract void setValidations();
  
  public void setDependencies() {
	  // Example:
	  // dependentObjects.add( new IncludeDependency(IncludeDependency.getScriptTimeStamp("MyScript1"), "MyScript1"));
	  // dependentObjects.add( new IncludeDependency(IncludeDependency.getScriptTimeStamp("MyScript2"), "MyScript2"));
  };
  
  public ArrayList<Dependency> getDependentObjects() {
	  return new ArrayList<Dependency>();
  }

  /**
   * Special 'getter' to be used from within scripts.
   * 
   * @return
   */
  public Dependency [] getDependencies() {
	  
	  Dependency [] all = new Dependency[getDependentObjects().size()];
	  all = (Dependency []) getDependentObjects().toArray(all);
//	  for ( int i = 0; i < all.length; i++ ) {
//		  // Normalize id's
//		  if ( all[i] instanceof AdapterFieldDependency ) {
//			  all[i].setId( ((AdapterFieldDependency) all[i]).getEvaluatedId() );
//		  } 
//	  }
	  return all;
	  
  }
  
  /**
   * Recursively call store() or kill() method on all "open" mappable tree nodes.
   *
   * @param mtn
   */
  private final void callStoreOrKill(MappableTreeNode mtn, String storeOrKill) {
    try {
      if (mtn.getMyMap() != null) {
        if (storeOrKill.equals("store")) {
          System.err.println("Calling store for object " + mtn.getMyMap());
          if ( mtn.getMyMap() instanceof Mappable ) {
             ((Mappable) mtn.getMyMap()).store();
          }
        }
        else {
          System.err.println("Calling kill for object " + mtn.getMyMap());
          if ( mtn.getMyMap() instanceof Mappable ) {
        	  ((Mappable) mtn.getMyMap()).kill();
          }
        }
      }
    }
    catch (Exception e) {
    	logger.error("Error: ", e);
    }
    if (mtn.getParent() != null) {
      callStoreOrKill(mtn.getParent(), storeOrKill);
    }
  }

  
  
  public final void run(Access access) throws Exception {

	  myAccess = access;
	  @SuppressWarnings("unused")
	final String myThreadName = getThreadName();
	  //JMXHelper.registerMXBean(this, JMXHelper.SCRIPT_DOMAIN, myThreadName);

	  long start = System.currentTimeMillis();
	  
	  try {
		  setValidations();

		  currentParamMsg = access.getInDoc().getMessage("__parms__");

		  ConditionData[] conditions = getValidationRules(access.getInDoc());
		  boolean conditionsFailed = false;
		  if (conditions != null && conditions.length > 0) {
			  Navajo outMessage = access.getOutputDoc();
			  Message[] failed = checkValidationRules(conditions, access.getInDoc(), outMessage);
			  if (failed != null) {
				  conditionsFailed = true;
				  Message msg = NavajoFactory.getInstance().createMessage(outMessage,
				  "ConditionErrors");
				  outMessage.addMessage(msg);
				  msg.setType(Message.MSG_TYPE_ARRAY);
				  for (int i = 0; i < failed.length; i++) {
					  msg.addMessage( (Message) failed[i]);
				  }
			  }
		  }
		  if (!conditionsFailed) {
			  try {
				  execute(access);
			  }
			  catch (com.dexels.navajo.mapping.BreakEvent be) {
				  // Be sure that all maps are killed()!
				  if (currentMap != null) {
					  callStoreOrKill(currentMap, "kill");
				  }
				  throw be;
			  }
			  catch (Exception e) {
				  if (currentMap != null && currentMap.getParent() != null) {
					  callStoreOrKill(currentMap.getParent(), "kill");
				  }
				  throw e;
			  } finally {
				  // TODO Will fail epically with continuations. Just because this thread will die doesn't mean this scirpt is done.
				  finalBlock(access);
			  }
		  }
	  } finally {
		  access.processingTime = (int) ( System.currentTimeMillis() - start );
		  try {
			  //JMXHelper.deregisterMXBean(JMXHelper.SCRIPT_DOMAIN, myThreadName);
		  } catch (Throwable t) {
			  System.err.println("WARNING: Could not register script as MBean: " + t.getMessage());
		  }
	  }
  }

  /**
   * Deprecated method to check validation errors. Use <validations> block inside webservice script instead.
   *
   * @param conditions
   * @param inMessage
   * @param outMessage
   * @return
   * @throws NavajoException
   * @throws SystemException
   * @throws UserException
   */
  private final Message[] checkValidationRules(ConditionData[] conditions,
                                                Navajo inMessage,
                                                Navajo outMessage) throws
      NavajoException, SystemException, UserException {

    if (conditions == null) {
      return null;
    }

    ArrayList<Message> messages = new ArrayList<Message>();
    int index = 0;

    for (int i = 0; i < conditions.length; i++) {
      ConditionData condition = conditions[i];
      boolean valid = false;

      try {
    	  valid = com.dexels.navajo.parser.Condition.evaluate(condition.condition,
    			  inMessage);
      }
      catch (com.dexels.navajo.parser.TMLExpressionException ee) {
    	  throw new UserException( -1, "Invalid condition: " + ee.getMessage());
      }
      
      if (!valid) {

    	  String eval = com.dexels.navajo.parser.Expression.replacePropertyValues(
    			  condition.condition, inMessage);
    	  Message msg = NavajoFactory.getInstance().createMessage(outMessage,
    			  "failed" + (index++));
    	  Property prop0 = NavajoFactory.getInstance().createProperty(outMessage,
    			  "Id", Property.STRING_PROPERTY,
    			  condition.id + "", 0, "", Property.DIR_OUT);
    	  
    	  // Evaluate comment as expression.
    	  String description = "";
    	  try {
    		  Operand o = Expression.evaluate(condition.comment, inMessage);
    		  description = o.value + "";
    	  } catch (Exception e) {
    		  description = condition.comment;
    	  }
    	  Property prop1 = NavajoFactory.getInstance().createProperty(outMessage,
    			  "Description", Property.STRING_PROPERTY,
    			  description, 0, "", Property.DIR_OUT);
    	  Property prop2 = NavajoFactory.getInstance().createProperty(outMessage,
    			  "FailedExpression", Property.STRING_PROPERTY,
    			  condition.condition, 0, "", Property.DIR_OUT);
    	  Property prop3 = NavajoFactory.getInstance().createProperty(outMessage,
    			  "EvaluatedExpression", Property.STRING_PROPERTY,
    			  eval, 0, "", Property.DIR_OUT);

    	  msg.addProperty(prop0);
    	  msg.addProperty(prop1);
    	  msg.addProperty(prop2);
    	  msg.addProperty(prop3);
    	  messages.add(msg);
      }
    }

    if (messages.size() > 0) {
      Message[] msgArray = new Message[messages.size()];

      messages.toArray(msgArray);
      return msgArray;
    }
    else {
      return null;
    }
  }

  
  private final ConditionData[] getValidationRules(Navajo inMessage) throws
      Exception {
    if (conditionArray != null) {
      //System.err.println("CHECKING CONDITIONS......, conditionArray = " + conditionArray.length);
      List<ConditionData> conditions = new ArrayList<ConditionData>();
      for (int i = 0; i < conditionArray.length; i++) {
        boolean check = (conditionArray[i].equals("") ? true :
                         Condition.evaluate(conditionArray[i], inMessage));
        //System.err.println("check = " + check);
        if (check) {
          ConditionData cd = new ConditionData();
          cd.id = codeArray[i];
          cd.comment = ( descriptionArray != null ? descriptionArray[i] : "empty" );
          cd.condition = ruleArray[i];
          //System.err.println("id = " + cd.id + ", rule = " + cd.condition);
          conditions.add(cd);
        }
      }
      ConditionData[] conditionArray = new ConditionData[conditions.size()];
      conditionArray = (ConditionData[]) conditions.toArray(conditionArray);
      return conditionArray;
    }
    else {
      return null;
    }
  }

  public abstract void execute(Access access) throws
      Exception, NavajoDoneException;

  /**
   * Pool for use of Navajo functions.
   * TODO rewrite to OSGi services
   * TODO use generics and stronger typing
   * @param name
   * @return
   */
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

  protected void finalize() {
	  functions.clear();
  }
  
  public final Object findMapByPath(String path) {
	 
	  StringTokenizer st = new StringTokenizer(path,"/");
	  int count = 0;
	  while (st.hasMoreTokens()) {
		  String element = st.nextToken();
		  if (!"..".equals(element)) {
			  System.err.println("Huh? : "+element);
		  }
		  count++;
	  }

	  Object m = ((MappableTreeNode)treeNodeStack.get(treeNodeStack.size()-count)).getMyMap();
	  
	  return m;
  }
  

  public void releaseCompiledScript() {
	  myAccess = null;
	  classLoader = null;
  }
  
  public void load(Access access) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }
 
  /**
   * Checks whether this compiled script has any dirty dependencies (i.e. needs recompilation)
   * 
   * @param a
   * @return
   */
  public boolean hasDirtyDependencies(Access a) {
	  
	  if ( getDependentObjects() == null ) {
		  return false;
	  }
	  for (int i = 0; i < getDependentObjects().size(); i++ ) {
		  Dependency dep = getDependentObjects().get(i);
		  if ( dep != null && dep.needsRecompile() ) {
			  return true;
		  }
	  }
	  return false;
  }

  public String getDescription() {
	  return "";
  }

  public String getAuthor() {
	  return "";
  }

  public String getScriptType() {
	return "tsl";  
  }

  public boolean isDebugAll() {
	  return debugAll;
  }

  public void setDebugAll(boolean debugAll) {
	  this.debugAll = debugAll;
  }
  
  public MappableTreeNode getCurrentMap() {
	  return currentMap;
  }

  public Message getCurrentOutMsg() {
	  return currentOutMsg;
  }

  public Message getCurrentParamMsg() {
	  return currentParamMsg;
  }

  public Message getCurrentInMsg() {
	  return currentInMsg;
  }

  public Selection getCurrentSelection() {
	  return currentSelection;
  }

}