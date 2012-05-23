package com.dexels.navajo.test;

import java.io.IOException;

import com.dexels.navajo.document.Navajo;

public interface ScriptInputLocator {
	public  Navajo getInput(String scriptName) throws IOException;

}
