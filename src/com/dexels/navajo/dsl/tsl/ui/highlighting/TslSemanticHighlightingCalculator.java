package com.dexels.navajo.dsl.tsl.ui.highlighting;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.parsetree.CompositeNode;
import org.eclipse.xtext.parsetree.LeafNode;
import org.eclipse.xtext.parsetree.NodeAdapter;
import org.eclipse.xtext.parsetree.NodeUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator;

import com.dexels.navajo.dsl.model.expression.ExistsTmlReference;
import com.dexels.navajo.dsl.model.expression.FunctionCall;
import com.dexels.navajo.dsl.model.expression.MapGetReference;
import com.dexels.navajo.dsl.model.expression.TmlReference;
import com.dexels.navajo.dsl.model.expression.TopLevel;
import com.dexels.navajo.dsl.model.tsl.Map;
import com.dexels.navajo.dsl.model.tsl.MapMethod;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.Tml;

public class TslSemanticHighlightingCalculator implements
		ISemanticHighlightingCalculator {



//	// adapted from Sebastian Zarnekow's semantic highlighting implementation
//	// navigate to the parse node corresponding to the semantic object and
//	// fetch the leaf node that corresponds to the first feature with the given
//	// name
//	public LeafNode getFirstFeatureNode(EObject semantic, String feature) {
//		NodeAdapter adapter = NodeUtil.getNodeAdapter(semantic);
//		System.err.println("Adapter: "+adapter);
//		if (adapter != null) {
//			CompositeNode node = adapter.getParserNode();
//			if (node != null) {
//				if (feature == null) {
//					return null;
//				}
//				
//				for (LeafNode nn : node.getLeafNodes()) {
//					System.err.println("NODe: "+nn.getText());
//					
//				}
//				
//				for (AbstractNode child : node.getChildren()) {
//					if (child instanceof LeafNode) {
//						
//						LeafNode leafNode = (LeafNode) child;
//						System.err.println("LEAF: "+leafNode.getText());
//						System.err.println("Checking feature: "+leafNode.getFeature());
//						if (feature.equals(leafNode.getFeature())) {
//							return (LeafNode) child;
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	public void provideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor) {
		EList<EObject> contents = resource.getContents();
		if(contents==null || contents.size()==0) {
			return;
		}
		Tml m = (Tml) contents.get(0);
		System.err.println("Resource: "+m.getClass());
	    highlightFunctionCall(acceptor, m);
	    highlightTmlImput(acceptor, m);
	    highlightMapGetReference(acceptor,m);
	    highlightMapTag(acceptor,m);
	    highlightTmlToplevel(acceptor,m);
	    highlightMapMethod(acceptor,m);
	    NodeUtil.getAllContents(
	    		  resource.getParseResult().getRootNode());
	    
	    


	}

	private void highlightFunctionCall(IHighlightedPositionAcceptor acceptor,
			Tml m) {
		List<FunctionCall> aa = EcoreUtil2.eAllOfType(m, FunctionCall.class);
	    for (FunctionCall call : aa) {
	    	highlightFirstLeaf(acceptor, call,TslHighlightingConfiguration.FUNCTION_CALL_ID);
		}
	}

	private void highlightMapTag(IHighlightedPositionAcceptor acceptor,
			Tml m) {
		List<Map> aa = EcoreUtil2.eAllOfType(m, Map.class);
	    // TODO Create a separate category for map refs
	    for (Map tag : aa) {
//	    	highlightLeafWithIndex(0,acceptor, tag,TslHighlightingConfiguration.MAP_ID);
	    	highlightLeafWithIndex(new int[]{0,-5,-4,-3,-2,-1},acceptor, tag,TslHighlightingConfiguration.MAP_ID);
	    	
	    	for (PossibleExpression p : tag.getAttributes()) {
				if("object".equals( p.getKey())) {
					TopLevel expressionValue = p.getExpressionValue();
					if(expressionValue!=null) {
						highlightFirstLeaf(acceptor, expressionValue, TslHighlightingConfiguration.MAP_ID);
					}
				}
			} 
	    	
	    }
	}

	private void highlightMapGetReference(IHighlightedPositionAcceptor acceptor,
			Tml m) {
		List<MapGetReference> aa = EcoreUtil2.eAllOfType(m, MapGetReference.class);
	    for (MapGetReference ref : aa) {
	    	highlightAllLeaves(acceptor, ref,TslHighlightingConfiguration.MAP_ID);
		}
	}
	private void highlightMapMethod(IHighlightedPositionAcceptor acceptor,
			Tml m) {
		List<MapMethod> aa = EcoreUtil2.eAllOfType(m, MapMethod.class);
	    for (MapMethod ref : aa) {
//	    	ref.
	    	highlightFirstLeaf(acceptor,ref,TslHighlightingConfiguration.MAP_ID);
//	    	highlightAllLeaves(acceptor, ref,TslHighlightingConfiguration.MAP_ID);
		}
	}

	
	
	private void highlightTmlImput(IHighlightedPositionAcceptor acceptor,Tml m) {
		List<TmlReference> aa = EcoreUtil2.eAllOfType(m, TmlReference.class);
	    for (TmlReference call : aa) {
	    	highlightAllLeaves(acceptor, call,TslHighlightingConfiguration.TML_INPUT);
		}

		List<ExistsTmlReference> bb = EcoreUtil2.eAllOfType(m, ExistsTmlReference.class);
	    for (ExistsTmlReference call : bb) {
	    	highlightAllLeaves(acceptor, call,TslHighlightingConfiguration.TML_INPUT);
		}

	}

	private void highlightTmlToplevel(IHighlightedPositionAcceptor acceptor,Tml m) {
		for(PossibleExpression a :m.getAttributes()) {
			highlightFirstLeaf(acceptor, a, TslHighlightingConfiguration.TSL_COMMENT_ID);
		}
	}

	private void highlightAllLeaves(IHighlightedPositionAcceptor acceptor, EObject m, String highlightId) {
		NodeAdapter adapter = NodeUtil.getNodeAdapter(m);
		CompositeNode node = adapter.getParserNode();
		for (LeafNode nn : node.getLeafNodes()) {
			acceptor.addPosition(nn.getOffset(), nn.getLength(),  highlightId);
		}
	}
	
	private void highlightFirstLeaf(IHighlightedPositionAcceptor acceptor, EObject m, String highlightId) {
		highlightLeafWithIndex(0, acceptor, m, highlightId);
	}

	private void highlightLeafWithIndex(int[] indices, IHighlightedPositionAcceptor acceptor, EObject m, String highlightId) {
		for (int i : indices) {
			highlightLeafWithIndex(i,acceptor,m,highlightId);
		}
	}
	
	
	/**
	 * Negative index = count from last
	 * @param index
	 * @param acceptor
	 * @param m
	 * @param highlightId
	 */
	private void highlightLeafWithIndex(int index, IHighlightedPositionAcceptor acceptor, EObject m, String highlightId) {
		NodeAdapter adapter = NodeUtil.getNodeAdapter(m);
		CompositeNode node = adapter.getParserNode();
		//LeafNode nn = node.getLeafNodes().get(0);
		int ind = 0;
		if(index<0) {
			index = node.getLeafNodes().size() + index;
			
//			System.err.println("Highlighting from last: "+index);
//			LeafNode nnn = node.getLeafNodes().get(index);
//			System.err.println("LEAD: "+nnn.getText());
		}
		for(LeafNode nn: node.getLeafNodes()) {
			if(!nn.isHidden()) {
				if(ind==index) {
					acceptor.addPosition(nn.getOffset(), nn.getLength(),  highlightId);
					return;
				}
				ind++;
			}
		}
	}
}
