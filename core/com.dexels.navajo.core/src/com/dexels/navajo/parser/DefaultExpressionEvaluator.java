package com.dexels.navajo.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.ExpressionChangedException;
import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.MappableTreeNode;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class DefaultExpressionEvaluator implements ExpressionEvaluator {

	private final static Logger logger = LoggerFactory.getLogger(DefaultExpressionEvaluator.class);

	public DefaultExpressionEvaluator() {
		logger.debug("Creating defaultExpressionEvaluator service");
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage)
			throws NavajoException {
		return evaluate(clause, inMessage, mappableTreeNode, parent, null,null,null,null,immutableMessage,paramMessage);
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ImmutableMessage> immutableMessage) throws NavajoException {
		return evaluate(clause, inMessage, mappableTreeNode, parent, null, null, null, null, immutableMessage, Optional.empty());
	}
	
	@Override
	public Operand evaluate(String clause,  Navajo inMessage, Object mappableTreeNode, Message parent,
			Message currentParam,Selection selection, Object tipiLink, Map<String,Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws NavajoException {
		try {
			return Expression.evaluate(clause, inMessage, (MappableTreeNode) mappableTreeNode, parent, currentParam,
					selection, (TipiLink)tipiLink, params);
		} catch (Throwable ex) {

			throw NavajoFactory.getInstance()
					.createNavajoException("Parse error: " + ex.getMessage() + "\n while parsing: " + clause, ex);
		}
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws NavajoException {
		try {
			return Expression.evaluate(clause, inMessage);
		} catch (Throwable ex) {

			throw NavajoFactory.getInstance()
					.createNavajoException("Parse error: " + ex.getMessage() + "\n while parsing: " + clause, ex);
		}
	}

	private List<Property> getExpressionList(Navajo n) throws NavajoException {
		List<Property> expressionList = new ArrayList<Property>();
		List<Message> a = n.getAllMessages();
		for (int i = 0; i < a.size(); i++) {
			Message current = a.get(i);
			getExpressionList(n, current, expressionList);
		}
		return expressionList;
	}

	private final void getExpressionList(Navajo n, Message m, List<Property> expressionList) throws NavajoException {
		List<Message> a = m.getAllMessages();
		for (int i = 0; i < a.size(); i++) {
			Message current = a.get(i);
			getExpressionList(n, current, expressionList);
		}
		List<Property> b = m.getAllProperties();
		for (int i = 0; i < b.size(); i++) {
			Property current = b.get(i);
			if (current.getType().equals(Property.EXPRESSION_PROPERTY)) {
				expressionList.add(current);
			}
		}
	}

	private final List<Property> getExpressionDependencies(Navajo n, Property p) throws NavajoException {
		if (!p.getType().equals(Property.EXPRESSION_PROPERTY)) {
			throw new UnsupportedOperationException();
		}
		String expression = p.getValue();
		return getExpressionDependencies(expression, n, p.getParentMessage());
	}

	private final List<Property> getExpressionDependencies(String expression, Navajo n, Message parent) {
		if (expression == null) {
			return null;
		}
		int startIndex = expression.indexOf('[');
		int endIndex = expression.indexOf(']');
		if (startIndex == -1) {
			// no references to properties, so no dependencies
			return null;
		}
		if (endIndex == -1) {
			throw new IllegalArgumentException("Unbalanced brackets: " + expression);
		}
		String tml = expression.substring(startIndex + 1, endIndex);
		String rest = expression.substring(endIndex + 1);
		List<Property> restList = getExpressionDependencies(rest, n, parent);
		if (restList == null) {
			restList = new ArrayList<Property>();
		}
		Property pp = parent.getProperty(tml);
		if (pp != null /* && pp.getType().equals(Property.EXPRESSION_PROPERTY) */) {
			restList.add(pp);
		}
		return restList;

	}

	// private boolean dependsOn(Property expressionProperty, Property target) {
	//
	// }
	//

	private final List<Property> processRefreshQueue(List<Property> queue) throws NavajoException {
		Object o = null;
		Object p = null;
		List<Property> refreshQueue = null;
		for (int i = 0; i < queue.size(); i++) {
			Property current = queue.get(i);

			o = current.peekEvaluatedValue();
			try {
				try {
					current.refreshExpression();
				} catch (ExpressionChangedException e) {
				}
			} catch (NavajoException e) {
				logger.info("Expression failed: " + current.getValue());
			} catch (Throwable e) {
				logger.info("Expression changed");
			}
			p = current.peekEvaluatedValue();

			if (o == null && p == null) {
				continue;
			}
			if (o == null || p == null) {
				if (refreshQueue == null) {
					refreshQueue = new ArrayList<Property>();
				}
				refreshQueue.add(current);
				continue;
			}
			// if (o.equals(p)) {
			// continue;
			// } else {
			if (refreshQueue == null) {
				refreshQueue = new ArrayList<Property>();
			}
			refreshQueue.add(current);
			// }
		}
		if (refreshQueue == null) {
			refreshQueue = new ArrayList<Property>();
		}
		return refreshQueue;
	}

	@Override
	public final List<Property> processRefreshQueue(Map<Property, List<Property>> depMap) throws NavajoException {
		List<Property> updateQueue = createUpdateQueue(depMap);
		return processRefreshQueue(updateQueue);
	}


	private final Map<Property, List<Property>> getExpressionDependencyMap(Navajo n, List<Property> exprList)
			throws NavajoException {
		Map<Property, List<Property>> depMap = new HashMap<Property, List<Property>>();
		for (int i = 0; i < exprList.size(); i++) {
			Property current = exprList.get(i);
			List<Property> l = getExpressionDependencies(n, current);
			depMap.put(current, l);
		}
		return depMap;
	}

	private final Map<Property, List<Property>> duplicateDependencyMap(Map<Property, List<Property>> original) {
		return original;
	}

	@Override
	public Map<Property, List<Property>> createDependencyMap(Navajo n) throws NavajoException {
		List<Property> l = getExpressionList(n);
		Map<Property, List<Property>> mm = getExpressionDependencyMap(n, l);
		return mm;
	}

	private final List<Property> createUpdateQueue(Map<Property, List<Property>> mm) throws NavajoException {
		Map<Property, List<Property>> dependencyMap = duplicateDependencyMap(mm);
		Set<Property> propKeys = dependencyMap.keySet();
		List<Property> queue = new ArrayList<Property>();

		Navajo first = null;
		while (dependencyMap.size() > 0) {
			for (Iterator<Property> iter = propKeys.iterator(); iter.hasNext();) {
				Property item = iter.next();
				if (first == null) {
					first = item.getRootDoc();
				}
				if (!Property.EXPRESSION_PROPERTY.equals(item.getType())) {
					if (!queue.contains(item)) {
						queue.add(item);
					}
					dependencyMap.remove(item);
					break;
				}

				List<Property> deps = dependencyMap.get(item);

				if (deps == null) {
					if (!queue.contains(item)) {
						queue.add(item);
					}
					dependencyMap.remove(item);
					break;
				}
				if (!containsExpressions(deps)) {
					if (!queue.contains(item)) {
						queue.add(item);
					}
					dependencyMap.remove(item);
					break;
				}

				if (queue.containsAll(deps)) {
					if (!queue.contains(item)) {
						queue.add(item);
					}
					dependencyMap.remove(item);
					break;
				}

				try {
					addExpressionToQueue(item, dependencyMap, queue);
					break;
				} catch (ExpressionDependencyException ex1) {
					logger.info("Did not succeed adding. Continuing", ex1);
				}
			}
		}

		// UUUUUUUUUUUGLY: MAKE SURE EVERY SINGLE ONE GETS ADDED
		if (first != null) {
			queue.addAll(getExpressionList(first));
		}
		return queue;
	}

	private final void addExpressionToQueue(Property item, Map<Property, List<Property>> depMap, List<Property> queue)
			throws ExpressionDependencyException {
		List<Property> deps = depMap.get(item);
		if (deps == null) {
			throw new RuntimeException("Huh?");
		}
		boolean problems = false;
		for (int i = 0; i < deps.size(); i++) {
			Property p = deps.get(i);
			List<Property> deps2 = depMap.get(p);
			if (deps2 == null) {
				depMap.remove(p);
				if (!queue.contains(p)) {
					queue.add(p);
				}

				continue;
			}
			if (queue.containsAll(deps2)) {
				if (!queue.contains(p)) {
					queue.add(p);
				}
				depMap.remove(item);
				continue;
			}
			try {
				addExpressionToQueue(p, depMap, queue);
			} catch (ExpressionDependencyException ex) {
				problems = true;
			}
		}
		if (problems) {
			throw new ExpressionDependencyException();
		}

	}

	private final boolean containsExpressions(List<Property> expressionList) {
		for (int i = 0; i < expressionList.size(); i++) {
			Property current = expressionList.get(i);
			if (Property.EXPRESSION_PROPERTY.equals(current.getType())) {
				return true;
			}
		}
		return false;
	}

}
