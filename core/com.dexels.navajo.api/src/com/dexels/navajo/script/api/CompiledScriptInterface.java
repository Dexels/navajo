/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

import java.util.List;
import java.util.Stack;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;

public interface CompiledScriptInterface {

	public String getScriptName();

	public String getUser();

	public String getAccessId();

	public String getThreadName();

	public boolean isWaiting();

	public boolean getWaiting();

	public String getLockName();

	public String getLockOwner();

	public String getLockClass();

	public String getStackTrace();

	public long getRunningTime();

	public void kill();

	public void setKill(boolean b);

	public boolean getKill();

	public void finalBlock(Access access) throws Exception;

	/**
	 * Generated code for validations.
	 */
	public void setValidations();

	public void dumpRequest();
	
	public void dumpResponse();

	public void setDependencies();

	public List<Dependency> getDependentObjects();

	/**
	 * Special 'getter' to be used from within scripts.
	 * 
	 * @return
	 */
	public Dependency[] getDependencies();

	public void run(Access access) throws Exception;

	public void execute(Access access) throws Exception;

	/**
	 * Pool for use of Navajo functions.
	 * @param name
	 * @return
	 */
	public Object getFunction(String name) throws Exception;

	public Object findMapByPath(String path);

	public void releaseCompiledScript();

	public void load(Access access) throws MappableException,
			UserException;

	public void store() throws MappableException, UserException;

	/**
	 * Checks whether this compiled script has any dirty dependencies (i.e. needs recompilation)
	 * 
	 * @param a
	 * @return
	 */
	public boolean hasDirtyDependencies(Access a);

	public String getDescription();

	public String getAuthor();

	public String getScriptType();
	
	public void setConfigDebugMode(String mode);

	public String getScriptDebugMode();

	public boolean isDebugAll();
	
	public boolean debugRequest();
	
	public boolean debugResponse();

	public Message getCurrentOutMsg();

	public Message getCurrentParamMsg();

	public Message getCurrentInMsg();

	public Selection getCurrentSelection();

	public MappableTreeNode getCurrentMap();
	
	public void setClassLoader(NavajoClassSupplier navajoClassSupplier);

	public void setFactory(CompiledScriptFactory compiledScriptFactory);

	public NavajoClassSupplier getClassLoader();

	public Stack<MappableTreeNode> getTreeNodeStack();

	public void setInDoc(Navajo inDoc);

	public Navajo getInDoc();

	public Stack<Message> getOutMsgStack();

  

}