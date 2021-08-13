package com.dexels.navajo.document.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Break;
import com.dexels.navajo.document.Navajo;

public class BaseBreakTagImpl extends BaseNode implements Break {

	private String condition;
	private String id;
	private String description;
	private String error;

	public BaseBreakTagImpl(Navajo n) {
		super(n);
	}
	
	public BaseBreakTagImpl(Navajo n, String condition, String id, String description) {
		super(n);
		this.condition = condition;
		this.id = id;
		this.description = description;
	}
	
	@Override
	public String getCondition() {
		return condition;
	}

	@Override
	public String getConditionId() {
		return id;
	}

	@Override
	public String getConditionDescription() {
		return description;
	}

	@Override
	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public void setConditionId(String id) {
		this.id = id;
	}

	@Override
	public void setConditionDescription(String description) {
		this.description = description;
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		if ( condition != null && !condition.equals("") )  {
			m.put("condition", condition);
		}
		if ( id != null && !id.equals("") )  {
			m.put("conditionId", id);
		}
		if ( description != null && !description.equals("") )  {
			m.put("conditionDescription", description);
		}
		if ( error != null && !error.equals("") )  {
			m.put("error", error);
		}
		return m;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "break";
	}

}
