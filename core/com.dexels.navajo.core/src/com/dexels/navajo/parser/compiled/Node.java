/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/* Generated By:JJTree: Do not edit this line. Node.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;

/* All AST nodes must implement this interface.  It provides basic
   machinery for constructing the parent and child relationships
   between nodes. */

public
interface Node {

  /** This method is called after the node has been made the current
    node.  It indicates that child nodes can now be added to it. */
  public void jjtOpen();

  /** This method is called after all the child nodes have been
    added. */
  public void jjtClose();

  /** This pair of methods are used to inform the node of its
    parent. */
  public void jjtSetParent(Node n);
  public Node jjtGetParent();

  /** This method tells the node to add its argument to the node's
    list of children.  */
  public void jjtAddChild(Node n, int i);

  /** This method returns a child node.  The children are numbered
     from zero, left to right. */
  public Node jjtGetChild(int i);

  /** Return the number of children the node has. */
  public int jjtGetNumChildren();
  
  /** Interpret method */
//  default ContextExpression interpretToLambda(List<String> problems, String originalExpression) {
//	 return interpretToLambda(problems, originalExpression, ParseMode.DEFAULT);
//  } 
  
//  default ContextExpression interpretToLambda(List<String> problems, String originalExpression, Function<String, FunctionClassification> functionClassifier) {
//	  return interpretToLambda(problems, originalExpression, functionClassifier,Optional.empty());
//  }
  ContextExpression interpretToLambda(List<String> problems, String originalExpression, Function<String, FunctionClassification> functionClassifier, Function<String,Optional<Node>> mapNodeResolver);

  public Token jjtGetFirstToken();
  public void jjtSetFirstToken(Token token);
  public Token jjtGetLastToken();
  public void jjtSetLastToken(Token token);
}
/* JavaCC - OriginalChecksum=22197c64ec0b9502885f0a5dc1d3ddd7 (do not edit this line) */
