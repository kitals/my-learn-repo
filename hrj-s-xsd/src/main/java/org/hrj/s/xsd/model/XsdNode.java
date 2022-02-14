package org.hrj.s.xsd.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * xsd元素封装
 * 
 * @author 214
 *
 */
@Data
public class XsdNode {
	//元素名称
	private String name = "";
	
	//元素前缀（）
	private String prefix = "";
	//命名空间
	private String namespace = "";
	
	//name属性
	private String attrName = "";
	//ref属性
	private String attrRef = "";
	private String attrType = "";
	//所有属性
	private Map<String,String> attrs = new HashMap<String, String>();
	//子节点
	private List<XsdNode> childNode = new ArrayList<XsdNode>();
	//父节点
	private XsdNode parentNode;
	private XsdNode refNode;

	public void addChildNode( XsdNode xsdNode) {
		this.childNode.add(xsdNode);
		xsdNode.setParentNode(this);
	}

}
