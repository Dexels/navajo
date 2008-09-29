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

import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.server.*;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.document.*;

import java.lang.management.ThreadInfo;
import java.util.*;

import com.dexels.navajo.parser.Condition;
import java.util.ArrayList;

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
  public LazyMessageImpl lm = null;
  public String fullMsgName = "";
  public boolean matchingConditions = false;
  public HashMap evaluatedAttributes = null;
  public boolean inSelectionRef = false;
  public final Stack inSelectionRefStack = new Stack();
  public int count = 1;

  public String[] conditionArray;
  public String[] ruleArray;
  public String[] codeArray;

  /**
   * This HashMap is used for user defined expressions in <definitions> section of a script.
   */
  public HashMap userDefinedRules = new HashMap();
  
  protected boolean kill = false;

  private JMXHelper jmx = null;
  private boolean connected = false;
  @SuppressWarnings("unused")
private ThreadInfo myThread = null;
  @SuppressWarnings("unused")
private boolean keepJMXConnectionAlive = false;
  
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
//	  try {
//		  connectJMX();
//		  LockInfo [] monitors = myThread.getLockedSynchronizers();
//		  return monitors.length != 0;
//	  } finally {
//		  if (!keepJMXConnectionAlive) {
//			  disconnectJMX();
//		  }
//	  }
  }
  
  public String getLockName() {
	  return "";
//	  try {
//		  connectJMX();
//		  return myThread.getLockName();
//	  } finally {
//		  if (!keepJMXConnectionAlive) {
//			  disconnectJMX();
//		  }
//	  }
  }
  
  public String getLockOwner() {
	  return "";
//	  try {
//		  connectJMX();
//		  return myThread.getLockOwnerName();
//	  } finally {
//		  if (!keepJMXConnectionAlive) {
//			  disconnectJMX();
//		  }
//	  }
  }
  
  public String getLockClass() {
	  return "";
//	  try {
//		  connectJMX();
//		  LockInfo lockInfo = myThread.getLockInfo();
//		  if ( lockInfo != null ) {
//			  return lockInfo.getClassName();
//		  } else {
//			  return null;
//		  }
//	  } finally {
//		  if (!keepJMXConnectionAlive) {
//			  disconnectJMX();
//		  }
//	  }
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
	  disconnectJMX();
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

  public abstract void finalBlock(Access access) throws Exception;

  /**
   * Generated code for validations.
   */
  public abstract void setValidations();

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
      e.printStackTrace(System.err);
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
    	  Property prop1 = NavajoFactory.getInstance().createProperty(outMessage,
    			  "Description", Property.STRING_PROPERTY,
    			  condition.comment, 0, "", Property.DIR_OUT);
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
      ArrayList conditions = new ArrayList();
      for (int i = 0; i < conditionArray.length; i++) {
        boolean check = (conditionArray[i].equals("") ? true :
                         Condition.evaluate(conditionArray[i], inMessage));
        //System.err.println("check = " + check);
        if (check) {
          ConditionData cd = new ConditionData();
          cd.id = Integer.parseInt(codeArray[i]);
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
      Exception;

  /**
   * Pool for use of Navajo functions.
   *
   * @param name
   * @return
   */
  public final Object getFunction(String name) throws Exception {
    Object f = functions.get(name);
    if (f != null) {
      return f;
    }
    f = Class.forName(name, false, classLoader).newInstance();
    functions.put(name, f);
    return f;
  }

  protected void finalize() {
	  functions.clear();
	  disconnectJMX();
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
  
//  private void connectJMX() {
//	  if (!connected) {
//		  jmx = new JMXHelper();
//		  connected = false;
//		  try {
//			  jmx.connect();
//			  connected = true;
//			  myThread = jmx.getThread(myAccess.getThread());
//		  } catch (Exception e) {
//			  e.printStackTrace(System.err);
//		  } 
//	  }
//  }
  
  private void disconnectJMX() {
	  try {
		  if ( connected && jmx != null ) {
			  jmx.disconnect();
		  }
	  } finally {
		  keepJMXConnectionAlive = false;
		  jmx = null;
		  connected = false;
	  }
  }
  
  public void load(Access access) throws MappableException, UserException {
	  keepJMXConnectionAlive = true;
	  //connectJMX();
  }

  public void store() throws MappableException, UserException {
	disconnectJMX();
  }


}