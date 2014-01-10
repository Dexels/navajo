package com.dexels.navajo.karaf.command;

import java.util.List;

import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.StringsCompleter;
public class YourCompleter implements Completer {
	 /**
	* @param buffer it's the beginning string typed by the user
	* @param cursor it's the position of the cursor
	* @param candidates the list of completions proposed to the user
	*/
	 @Override
	public int complete(String buffer, int cursor, List candidates) {
	  StringsCompleter delegate = new StringsCompleter();
	  delegate.getStrings().add("one");
	  delegate.getStrings().add("two");
	  delegate.getStrings().add("three");
	  return delegate.complete(buffer, cursor, candidates);
	 }
	}

