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

import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;
import java.util.HashMap;
import java.util.Stack;
import com.dexels.navajo.parser.Condition;
import java.util.ArrayList;

public abstract class CompiledScript {

  protected NavajoClassLoader classLoader;
  private final HashMap functions = new HashMap();

  public MappableTreeNode currentMap = null;
  public final Stack treeNodeStack = new Stack();
  public Navajo outDoc = null;
  public Navajo inDoc = null;
  public Message currentOutMsg = null;
  public final Stack outMsgStack = new Stack();
  public Message currentInMsg = null;
  public Selection currentSelection = null;
  public final Stack inMsgStack = new Stack();
  public Message parmMessage = null;
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

  public String [] conditionArray;
  public String [] ruleArray;
  public String [] codeArray;

  protected boolean kill = false;

  public void setKill(boolean b) {
    kill = b;
  }

  public boolean getKill() {
    return kill;
  }

  public void setClassLoader(NavajoClassLoader loader) {
    this.classLoader = loader;
    //System.out.println("in setClassLoader(): " + classLoader);
    String [] aap = {"aap",
    "noot"};
  }

  public abstract void setValidations();

  public final void run(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception {
    setValidations();
    ConditionData[] conditions = checkValidations(inMessage);
    boolean conditionsFailed = false;
    if (conditions != null && conditions.length > 0) {
      Navajo outMessage = access.getOutputDoc();
      Message[] failed = Dispatcher.checkConditions(conditions, inMessage, outMessage);
      if (failed != null) {
        conditionsFailed = true;
        Message msg = NavajoFactory.getInstance().createMessage(outMessage, "ConditionErrors");
        outMessage.addMessage(msg);
        msg.setType(Message.MSG_TYPE_ARRAY);
        for (int i = 0; i < failed.length; i++) {
          msg.addMessage( (Message) failed[i]);
        }
      }
    }
    if (!conditionsFailed) {
      execute(parms, inMessage, access, config);
    }
  }

  public final ConditionData [] checkValidations(Navajo inMessage) throws Exception {
    if (conditionArray != null) {
      //System.err.println("CHECKING CONDITIONS......, conditionArray = " + conditionArray.length);
      ArrayList conditions = new ArrayList();
      for (int i = 0; i < conditionArray.length; i++) {
        boolean check = (conditionArray[i].equals("") ? true : Condition.evaluate(conditionArray[i], inMessage));
        //System.err.println("check = " + check);
        if (check) {
          ConditionData cd = new ConditionData();
          cd.id = Integer.parseInt(codeArray[i]);
          cd.condition = ruleArray[i];
          //System.err.println("id = " + cd.id + ", rule = " + cd.condition);
          conditions.add(cd);
        }
      }
      ConditionData [] conditionArray = new ConditionData[conditions.size()];
      conditionArray = (ConditionData []) conditions.toArray(conditionArray);
      return conditionArray;
    } else {
      return null;
    }
  }

  public abstract void execute(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws Exception;

  /**
   * Pool for use of Navajo functions.
   *
   * @param name
   * @return
   */
  public final Object getFunction(String name) throws Exception {
    Object f = functions.get(name);
    if (f != null)
      return f;
    f = Class.forName(name, false, classLoader).newInstance();
    functions.put(name, f);
    return f;
  }

  public void finalize() {
    functions.clear();
  }

}