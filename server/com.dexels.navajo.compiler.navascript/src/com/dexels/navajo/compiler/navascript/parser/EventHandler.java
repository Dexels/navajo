package com.dexels.navajo.compiler.navascript.parser;

public interface EventHandler
{
	public void reset(CharSequence string);
	public void startNonterminal(String name, int begin);
	public void endNonterminal(String name, int end);
	public void terminal(String name, int begin, int end);
	public void whitespace(int begin, int end);
}
