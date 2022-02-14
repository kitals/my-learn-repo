package org.hrj.s.xsd.model;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;

import lombok.Data;

/**
 * 包含整个XSD的所有元素节点
 * 
 * @author 214
 *
 */
@Data
public class XsdResources {
	
	private String xsdfilePath = "";
	
	private Element rootElement ;
	
	private XsdNode xsdNode ;
	
	//这个Map中的元素是可以被引用的元素
	private Map<String,Element> xsdElementMap = new HashMap<String,Element>();
	
	private Map<String,XsdNode> xsdNodeMap = new HashMap<String,XsdNode>();

	
	
	public void addElement(String attrName,Element element) {
		if(element != null) {
			xsdElementMap.put(attrName, element);
		}
	}
	
	public void addXsdNode(String attrName,XsdNode xsdNode) {
		if(xsdNode != null) {
			xsdNodeMap.put(attrName, xsdNode);
		}
	}

	public Element getElement(String ref) {
		return xsdElementMap.get(ref);
	}

	public XsdNode getXsdNode(String ref) {
		return xsdNodeMap.get(ref);
	}
	
}
