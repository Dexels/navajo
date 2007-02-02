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

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.*;

import com.dexels.navajo.parser.Condition;
import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public abstract class CompiledScript implements CompiledScriptMXBean, Mappable {

  protected NavajoClassSupplier classLoader;
  private final HashMap functions = new HashMap();

  /**
   * Fields accessable by webservice.
   */
  public String stackTrace;
  public String threadName;
  public String accessId;
  public long runnningTime;
  
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
	  return Dispatcher.getThreadName(myAccess);
  }
  
  public String getStackTrace() {
	  StringBuffer stackTrace = new StringBuffer();
	  JMXHelper jmx = new JMXHelper();
	  boolean connected = false;
	  try {
		  jmx.connect();
		  connected = true;
		  ThreadInfo ti = jmx.getThread(myAccess.getThread());
		  StackTraceElement [] elt = ti.getStackTrace();
		 
		  for (int i = 0; i < elt.length; i++) {
			  stackTrace.append(elt[i].getClassName()+"."+elt[i].getMethodName() + " (" + elt[i].getFileName() + ":" + elt[i].getLineNumber() + ")\n");
		  }
	  } catch (Exception e) {
		  e.printStackTrace(System.err);
		  return null;
	  } finally {
		  if ( connected ) {
			  jmx.disconnect();
		  }
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

  private final ObjectName getObjectName() {
	  try {
		return new ObjectName(JMXHelper.SCRIPT_DOMAIN + getThreadName());
	} catch (MalformedObjectNameException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return  null;
	} catch (NullPointerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} 
  }
  
  private final void registerMXBean() {
	  MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
	  ObjectName name = getObjectName();
	  if ( name != null ) {
		  try {
			mbs.registerMBean(this, name);
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace(System.err);
		} catch (MBeanRegistrationException e) {
			e.printStackTrace(System.err);
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace(System.err);
		} 
	  }
  }
  
  private final void deregisterMXBean() {
	  MBeanServer mbs = ManagementFactory.getPlatformMBeanServer(); 
	  ObjectName name = getObjectName();
	  if ( name != null ) {
		  try {
			mbs.unregisterMBean(name);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace(System.err);
		} catch (MBeanRegistrationException e) {
			e.printStackTrace(System.err);
		}
	  }
  }
  
  public final void run(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {

	  myAccess = access;
	  registerMXBean();

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
			  deregisterMXBean();
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
  
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }


}