package org.hrj.s.xsd;

/**
 * xsd中的一些常量
 * 
 * @author 214
 *
 */
public final class XsdConstant {
	
	//被引用的元素必定是二级元素（root的直属元素）

	//name属性可以唯一表示一个元素
	//在普通xsd中，name与ref/type配合，ref/type指向的元素就是name代表的元素
	public final static String ATTR_NAME = "name";
	
	public final static String ATTR_REF = "ref";
	
	public final static String ATTR_TYPE = "type";
	public final static String ATTR_DOCUMENT = "Document";
	public final static String ATTR_ELEMENT = "element";
	public final static String ATTR_SEQUENCE = "sequence";
	
	
}