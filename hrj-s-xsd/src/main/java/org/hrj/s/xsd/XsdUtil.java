package org.hrj.s.xsd;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;
import org.hrj.s.xsd.model.XsdNode;
import org.hrj.s.xsd.model.XsdResources;

/**
 * xsd解析工具
 * 
 * @author 214
 *
 */
public class XsdUtil {
	
	public static void main(String[] args) {
//		String xsd = "E:\\Plugin\\hrj-s-xsd\\src\\main\\resources\\fix.xsd";
		String cufirSsd = "E:\\Plugin\\hrj-s-xsd\\src\\main\\resources\\AcceptorAuthorisationResponseV08.xsd";
		XsdNode xsdNode = read(cufirSsd);
		System.out.println(xsdNode);
	}

	public static XsdNode read(String xsdPath) {
		XsdResources xsdResources = toXsdResources(xsdPath);
		
		//找出根节点（对于cufir的xsd而言，根节点就是name为Document的非xs:element节点）
		Element rootElement = xsdResources.getRootElement();
		System.out.println(rootElement.getName());
		//
		XsdNode xsdNode = readElement(rootElement,xsdResources);
		return xsdNode;
	} 
	
	private static boolean isEmpty(String str) {
		return str==null || "".equals(str);
	}
	
	private static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 获得指定元素的指定属性名称的属性值
	 * 
	 * @param element
	 * @param attrName
	 * @return
	 */
	public static String getAttrValue(Element element ,String attrName) {
		if(element != null && isNotEmpty(element.attributeValue(attrName))) {
			return element.attributeValue(attrName);
		}
		return "";
		
	}
	
	/**
	 * 将xsd初步解析为XsdResources
	 * 
	 * @param xsdPath
	 * @return
	 */
	private static XsdResources toXsdResources(String xsdPath) {
		XsdResources xsdResources = new XsdResources();
		try {
			xsdResources.setXsdfilePath(xsdPath);
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(xsdPath));
			Element rootElement = document.getRootElement();
			
			//root下的元素是可以被其它元素引用的
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				String name = getAttrValue(element, XsdConstant.ATTR_NAME);
				XsdNode xsdNode = parseToXsdNode(element);
				if(isNotEmpty(name)) {
					xsdResources.addElement(name,element);
					xsdResources.addXsdNode(name, xsdNode);
					
					String elementName = element.getName();
					if(XsdConstant.ATTR_DOCUMENT.equals(name) && !XsdConstant.ATTR_ELEMENT.equals(elementName)) {
						//表示当前节点时根节点
						xsdResources.setRootElement(element);
					}
				}
			}
		
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return xsdResources;
	}

	/**
	 * 
	 * 
	 * @param element
	 * @param xsdResources 
	 * @param xsdResources
	 */
	private static XsdNode readElement(Element element, XsdResources xsdResources) {
		XsdNode xsdNode = parseToXsdNode(element);
		List<Element> elements = element.elements();
		if(elements!=null && elements.size()>0) {
			for (Element element2 : elements) {
				XsdNode xsdNode1 = readElement(element2,xsdResources);
				if(xsdNode1 != null) {
					xsdNode.addChildNode(xsdNode1);
				}
			}
		}else {
			setRefNode(xsdNode,xsdResources);
		}
		return xsdNode;
	}

	/**
	 * 	为xsdNode设置引用节点
	 * 
	 * @param xsdNode
	 * @param xsdResources
	 */
	private static void setRefNode(XsdNode xsdNode, XsdResources xsdResources) {
		//寻找映射元素
		String ref = xsdNode.getAttrRef();
		String type = xsdNode.getAttrType();
		if(isEmpty(ref) && isNotEmpty(type)) {
			ref = type;
		}
		
		//寻找被引用的节点
		if(isNotEmpty(ref)) {
			XsdNode refedXsdNode = xsdResources.getXsdNode(ref);
			if(refedXsdNode == null) {
				Element refedElement = xsdResources.getElement(ref);
				if(refedElement!= null) {
					refedXsdNode = parseToXsdNode(refedElement);
					setRefNode(refedXsdNode, xsdResources);
				}
			}
			if(refedXsdNode != null) {
				xsdNode.setRefNode(refedXsdNode);
			}
		}else {
			//子节点的引用节点
			System.out.println();
		}
	}

	/**
	 * 将Element转换为XsdNode
	 * 
	 * @param element
	 * @return
	 */
	private static XsdNode parseToXsdNode(Element element) {
		XsdNode xsdNode = new XsdNode();
		xsdNode.setName(element.getName());
		xsdNode.setPrefix(element.getNamespacePrefix());
		
		Namespace namespace = element.getNamespace();
		if(namespace != null) {
			xsdNode.setNamespace(namespace.getText());
		}
		
		String attrName = getAttrValue(element, XsdConstant.ATTR_NAME);
		String attrRef = getAttrValue(element, XsdConstant.ATTR_REF);
		String attrType = getAttrValue(element, XsdConstant.ATTR_TYPE);
		
		xsdNode.setAttrName(getValue(attrName));
		xsdNode.setAttrRef(getValue(attrRef));
		xsdNode.setAttrType(getValue(attrType));
		
		List<Attribute> attributes = element.attributes();
		for (Attribute attribute : attributes) {
			Map<String, String> hashMap = new HashMap<String,String>();
			hashMap.put(attribute.getName(), attribute.getValue());
			xsdNode.setAttrs(hashMap);
		}
		
		List<Element> elements = element.elements();
		if(elements!= null && elements.size()>0) {
			for (Element element2 : elements) {
				XsdNode parseToXsdNode = parseToXsdNode(element2);
				xsdNode.addChildNode(parseToXsdNode);
			}
		}
		
		return xsdNode;
	}

	private static String getValue(String str) {
		if(isNotEmpty(str)) {
			return str;
		}
		return "";
	}
}
