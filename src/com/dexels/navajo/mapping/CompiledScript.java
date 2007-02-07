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

import java.lang.management.LockInfo;
import java.lang.management.ThreadInfo;
import java.util.*;

import com.dexels.navajo.parser.Condition;
import java.util.ArrayList;

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

  protected boolean kill = false;

  private JMXHelper jmx = null;
  private boolean connected = false;
  private ThreadInfo myThread = null;
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
	  return Dispatcher.getInstance().getThreadName(myAccess);
  }
  
  public boolean isWaiting() {
	  return getWaiting();
  }
  
  public boolean getWaiting() {
	  try {
		  connectJMX();
		  LockInfo [] monitors = myThread.getLockedSynchronizers();
		  return monitors.length != 0;
	  } finally {
		  if (!keepJMXConnectionAlive) {
			  disconnectJMX();
		  }
	  }
  }
  
  public String getLockName() {
	  try {
		  connectJMX();
		  return myThread.getLockName();
	  } finally {
		  if (!keepJMXConnectionAlive) {
			  disconnectJMX();
		  }
	  }
  }
  
  public String getLockOwner() {
	  try {
		  connectJMX();
		  return myThread.getLockOwnerName();
	  } finally {
		  if (!keepJMXConnectionAlive) {
			  disconnectJMX();
		  }
	  }
  }
  
  public String getLockClass() {
	  try {
		  connectJMX();
		  LockInfo lockInfo = myThread.getLockInfo();
		  if ( lockInfo != null ) {
			  return lockInfo.getClassName();
		  } else {
			  return null;
		  }
	  } finally {
		  if (!keepJMXConnectionAlive) {
			  disconnectJMX();
		  }
	  }
  }
  
  public String getStackTrace() {
	  try {
		  connectJMX();
		  StringBuffer stackTrace = new StringBuffer();
		  StackTraceElement [] elt = myThread.getStackTrace();

		  for (int i = 0; i < elt.length; i++) {
			  stackTrace.append(elt[i].getClassName()+"."+elt[i].getMethodName() + " (" + elt[i].getFileName() + ":" + elt[i].getLineNumber() + ")\n");
		  }

		  return stackTrace.toString();
	  } finally {
		  if (!keepJMXConnectionAlive) {
			  disconnectJMX();
		  }
	  }
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

  public abstract void finalBlock(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception;

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
          mtn.getMyMap().store();
        }
        else {
          System.err.println("Calling kill for object " + mtn.getMyMap());
          mtn.getMyMap().kill();
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

  
  
  public final void run(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {

	  myAccess = access;
	  JMXHelper.registerMXBean(this, JMXHelper.SCRIPT_DOMAIN, getThreadName());

	  try {
		  setValidations();

		  currentParamMsg = inMessage.getMessage("__parms__");

		  ConditionData[] conditions = checkValidations(inMessage);
		  boolean conditionsFailed = false;
		  if (conditions != null && conditions.length > 0) {
			  Navajo outMessage = access.getOutputDoc();
			  Message[] failed = Dispatcher.getInstance().checkConditions(conditions, inMessage,
					  outMessage);
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
				  execute(parms, inMessage, access, config);
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
				  finalBlock(parms, inMessage, access, config);
			  }
		  }
	  } finally {
		  try {
			  JMXHelper.deregisterMXBean(JMXHelper.SCRIPT_DOMAIN, getThreadName());
		  } catch (Throwable t) {
			  System.err.println("WARNING: Could not register script as MBean: " + t.getMessage());
		  }
	  }
  }

  public final ConditionData[] checkValidations(Navajo inMessage) throws
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

  public abstract void execute(Parameters parms, Navajo inMessage,
                               Access access, NavajoConfig config) throws
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

  public void finalize() {
	  functions.clear();
	  disconnectJMX();
  }
  
  public final Mappable findMapByPath(String path) {
	 
	  StringTokenizer st = new StringTokenizer(path,"/");
	  int count = 0;
	  while (st.hasMoreTokens()) {
		  String element = st.nextToken();
		  if (!"..".equals(element)) {
			  System.err.println("Huh? : "+element);
		  }
		  count++;
	  }

	  Mappable m = ((MappableTreeNode)treeNodeStack.get(treeNodeStack.size()-count)).getMyMap();
	  
	  return m;
  }
  
  private void connectJMX() {
	  if (!connected) {
		  jmx = new JMXHelper();
		  connected = false;
		  try {
			  jmx.connect();
			  connected = true;
			  myThread = jmx.getThread(myAccess.getThread());
		  } catch (Exception e) {
			  e.printStackTrace(System.err);
		  } 
	  }
  }
  
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
  
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	  keepJMXConnectionAlive = true;
	  connectJMX();
  }

  public void store() throws MappableException, UserException {
	disconnectJMX();
  }


}