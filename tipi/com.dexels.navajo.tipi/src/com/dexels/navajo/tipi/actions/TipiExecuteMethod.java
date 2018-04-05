package com.dexels.navajo.tipi.actions;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiReentrantLockManager;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiMethod;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiExecuteMethod extends TipiAction {

	private final static Logger logger = LoggerFactory
			.getLogger(TipiExecuteMethod.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 472131866710083014L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
            com.dexels.navajo.tipi.TipiBreakException, InterruptedException {
		// Set<String> ss = getParameterNames();

        ReentrantLock mylock = null;
        String lockName = null;
        int lockTimeoutSeconds = 30;

		Object o = getEvaluatedParameterValue("name", event);
		Object p = getEvaluatedParameterValue("rootComponent", event);

        Object l = (String) getEvaluatedParameterValue("lock", event);
        Object lt = (String) getEvaluatedParameterValue("lockTimeout", event);

		if (o == null) {
            throw new TipiException("TipiExecuteMethod: name missing ");
		}
		if (!(o instanceof String))
		{
            throw new TipiException("TipiExecuteMethod: name wrong type");
		}
		if (p != null && !(p instanceof TipiComponent)) {
            throw new TipiException("TipiExecuteMethod: rootComponent wrong type");
		}
		String name = (String) o;

        if (l != null) {
            if (!(l instanceof String)) {
                throw new TipiException("TipiExecuteMethod: lock wrong type. Should be string");
            }
            // Set the lockName only if l is not null and is string
            lockName = (String) l;
        }

        if (lt != null && lt instanceof String) {
            String ltString = (String) lt;
            if (!ltString.matches("-?\\d+")) {
                throw new TipiException("TipiExecuteMethod: lockTimeout wrong type. Should be int");
            }
            // set the lockTimeoutSeconds only if the parsed string is an integer
            // representation
            lockTimeoutSeconds = Integer.parseInt(ltString);
        }

		// is rootComponent defined? if not, use event.getComponent() for now.
		if (getParameter("rootComponent") == null && event != null)
		{
			p = event.getComponent();
		}
		TipiComponent tc = (TipiComponent) p;
		
		// find the method, is it local or global? Local first
		TipiComponent localMethodDefaultRootComponent = null;
		XMLElement localMethodXML = null;
		if (tc != null && tc.getHomeComponent() != null)
		{
			localMethodDefaultRootComponent =  tc.getHomeComponent();
			localMethodXML  = localMethodDefaultRootComponent.getLocalMethod(name);
		}
		// else global
		XMLElement globalMethodXML = null;
		if (tc != null && tc.getContext() != null)
		{
			globalMethodXML = tc.getContext().getGlobalMethod(name);
		}
		
		if (globalMethodXML == null && localMethodXML == null) {
			logger.warn("TipiExecuteMethod: Cannot find local nor global method with name " + name + ", looking in component: " + localMethodDefaultRootComponent);
			return;
		}
		if (getParameter("rootComponent") == null)
		{ // find default root component
			if (localMethodXML != null)
			{
				tc = localMethodDefaultRootComponent;
			}
			else if (globalMethodXML != null)
			{
				tc = event.getContext().getDefaultTopLevel();
			}
		}
        if (tc != null) {
            logger.debug("Executing method " + name + " using as rootComponent " + tc.getName() + "/" + tc.getPath());
        } else {
            logger.debug("Executing method " + name + " using as rootComponent null");
        }
		XMLElement methodXML = localMethodXML == null ? globalMethodXML : localMethodXML;
		TipiMethod tipiMethod = new TipiMethod(event.getContext());
		tipiMethod.load(methodXML, tc, null);

        if (lockName != null) {
            mylock = TipiReentrantLockManager.getInstance().getLock(lockName);
            if (mylock.tryLock(lockTimeoutSeconds, TimeUnit.SECONDS)) {
                // Lock is mine :)
                try {
                    logger.debug("Executing method with lock {} and timeout {} seconds ", lockName, lockTimeoutSeconds);
                    tipiMethod.performAction(event, event.getParent(), event.getExeIndex(this));
                } finally {
                    TipiReentrantLockManager.getInstance().releaseLock(lockName, mylock);
                }
            } else {
                // Lock is acquired and we have timed out.
                logger.error("Waited for {} seconds but lock was acquired by another thread. Sorry :( ", lockTimeoutSeconds);
                throw new InterruptedException();
            }
        } else {
            tipiMethod.performAction(event, event.getParent(), event.getExeIndex(this));
        }
	}
}
