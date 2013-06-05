package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.tipi.TipiComponent;

public abstract class AbstractQueryAllComponentsFunction extends FunctionInterface {
	
	public static final String QUERY_TYPE_AND = "AND";
	public static final String QUERY_TYPE_OR = "OR";
	
	/**
	 * Should return true or false depending on the state of the given component
	 * @param tc the given TipiComponent
	 * @return dependent on the subclass, cannot be null.
	 */
	protected abstract Boolean querySingleComponent(TipiComponent tc);
	
	/**
	 * Queries all components in the tree starting with the given rootComponent. Returns the result of the "AND" or "OR" of the method querySingleComponent.
	 * This is a recursive method.
	 * @param root The rootComponent.
	 * @param queryType the queryType. Use the constants. If null, default is queryType AND.
	 * @return
	 */
	protected Boolean queryAllComponents(TipiComponent root, String queryType)
	{
		Boolean result;
		if (queryType == null || queryType.equals(QUERY_TYPE_AND))
		{
			result = Boolean.TRUE;
			queryType = QUERY_TYPE_AND;
		}
		else if (queryType.equals(QUERY_TYPE_OR))
		{
			result = Boolean.FALSE;
		}
		else
		{
			throw new IllegalArgumentException("Wrong queryType (" + queryType + ") for queryAllComponents.");
		}
		if (root.getChildCount() == 0)
		{
			return querySingleComponent(root);
		}
		else
		{
			for (TipiComponent child : root.getChildren())
			{
				Boolean singleResult = queryAllComponents(child, queryType);
				if (queryType.equals(QUERY_TYPE_AND))
				{
					result = result && singleResult;
					if (result.equals(Boolean.FALSE))
					{	// stop looping since the result can no longer change
						return result;
					}
				}
				else if (queryType.equals(QUERY_TYPE_OR))
				{
					result = result || singleResult;
					if (result.equals(Boolean.TRUE))
					{	// stop looping since the result can no longer change
						return result;
					}
				}
			}
			return result;
		}
	}

}
