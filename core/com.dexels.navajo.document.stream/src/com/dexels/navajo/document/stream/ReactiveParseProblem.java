package com.dexels.navajo.document.stream;

import java.util.Optional;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ReactiveParseProblem {

	public final String message;
	public final Optional<Integer> startLine;
	public final Optional<Integer> endLine;
	public final Optional<Integer> startOffset;
	public final Optional<Integer> endOffset;
	public final Optional<String> relativePath;
	public final Optional<XMLElement> tag;
	
	private ReactiveParseProblem(String message, Optional<Integer> startLine, Optional<Integer> endLine, Optional<Integer> startOffset, Optional<Integer> endOffset, Optional<String> relativePath, Optional<XMLElement> tag) {
		this.message = message;
		this.startLine = startLine;
		this.endLine = endLine;
		this.startOffset = startOffset;
		this.endOffset = endOffset;
		this.relativePath = relativePath;
		this.tag = tag;
	}
	private ReactiveParseProblem(String message) {
		this(message,Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty());
	}

	
	public ReactiveParseProblem withLocation(int line, int offset) {
		return new ReactiveParseProblem(message, Optional.of(line), Optional.empty(), Optional.of(offset), Optional.empty(), relativePath, tag);
	}

	public ReactiveParseProblem withRange(int startLine, int endLine, int startOffset, int endOffset) {
		return new ReactiveParseProblem(message, Optional.of(startLine), Optional.of(endLine), Optional.of(startOffset), Optional.of(endOffset), relativePath, tag);
	}

	public ReactiveParseProblem withRelativePath(String relativePath) {
		return new ReactiveParseProblem(message, startLine, endLine, startOffset, endOffset, Optional.of(relativePath), tag);
	}

	public ReactiveParseProblem withTag(XMLElement tag) {
		return new ReactiveParseProblem(message, startLine, endLine, startOffset, endOffset, relativePath, Optional.of(tag));
	}

	public static ReactiveParseProblem of(String message) {
		return new ReactiveParseProblem(message);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(message+" at ");
		sb.append(startLine.orElse(-1)+":"+startOffset.orElse(-1)+"->");
		sb.append(endLine.orElse(-1)+":"+endOffset.orElse(-1));
		return sb.toString();
	}
}
