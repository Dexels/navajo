package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Check;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Validations;

public class BaseValidationsTagImpl extends BaseNode implements Validations {

	List<BaseCheckTagImpl> children = new ArrayList<>();

	public BaseValidationsTagImpl(Navajo n) {
		super(n);
	}

	public void addCheck(BaseCheckTagImpl c) {
		children.add(c);
	}

	@Override
	public Map<String, String> getAttributes() {
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return children;
	}

	@Override
	public String getTagName() {
		return "validations";
	}

	@Override
	public List<Check> getChecks() {
		List<Check> checks = new ArrayList<>();
		for ( BaseCheckTagImpl a : children ) {
			checks.add(a);
		}
		return checks;
	}

}
