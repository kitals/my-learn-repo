package org.hrj.s.xsd;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.xmlet.xsdparser.core.XsdParser;
import org.xmlet.xsdparser.xsdelements.XsdAbstractElement;
import org.xmlet.xsdparser.xsdelements.XsdAttribute;
import org.xmlet.xsdparser.xsdelements.XsdComplexType;
import org.xmlet.xsdparser.xsdelements.XsdElement;
import org.xmlet.xsdparser.xsdelements.XsdSchema;
import org.xmlet.xsdparser.xsdelements.XsdSequence;
import org.xmlet.xsdparser.xsdelements.elementswrapper.ReferenceBase;
import org.xmlet.xsdparser.xsdelements.visitors.XsdAbstractElementVisitor;

public class XsdParserTest {
	public static void main(String[] args) {
		String xsd = "E:\\Plugin\\hrj-s-xsd\\src\\main\\resources\\fix.xsd";
		XsdParser xsdParser = new XsdParser(xsd);
		
		Stream<XsdElement> resultXsdElements = xsdParser.getResultXsdElements();
		System.out.println(resultXsdElements);
		Stream<XsdSchema> resultXsdSchemas = xsdParser.getResultXsdSchemas();
		System.out.println(resultXsdSchemas);
		
//		XsdComplexType htmlComplexType = xsdElement.getXsdComplexType();
//		XsdChoice choiceElement = htmlComplexType.getChildAsChoice();
//        XsdGroup flowContentGroup = choiceElement.getChildrenGroups().findFirst().get();
//        XsdAll flowContentAll = flowContentGroup.getChildAsAll();
//        XsdElement elem1 = flowContentAll.getChildrenElements().findFirst().get();
//        System.out.println(elem1.getAttributesMap());
		
		XsdElement xsdElement = resultXsdElements.findAny().get();
		Map<String, String> attributesMap = xsdElement.getAttributesMap();
		System.out.println(attributesMap);
		
		XsdComplexType xsdComplexType = xsdElement.getXsdComplexType();
		System.out.println(xsdComplexType);
		XsdSequence childAsSequence = xsdComplexType.getChildAsSequence();
		List<ReferenceBase> elements = childAsSequence.getElements();
		System.out.println(elements.size());
		ReferenceBase referenceBase = elements.get(0);
		XsdAbstractElement element = referenceBase.getElement();
		System.out.println(element.getAttributesMap());
		XsdAbstractElementVisitor visitor = element.getVisitor();
		
		XsdElement owner = (XsdElement)visitor.getOwner();
		System.out.println(owner);
		
		XsdComplexType xsdComplexType2 = owner.getXsdComplexType();
		Stream<XsdAttribute> xsdAttributes = xsdComplexType2.getXsdAttributes();
//		XsdAttribute xsdAttribute = xsdAttributes.findAny().get();
//		System.out.println(xsdAttribute.getAttributesMap());
		
		Iterator<XsdAttribute> iterator = xsdAttributes.iterator();
		while (iterator.hasNext()) {
			XsdAttribute next = iterator.next();
			System.out.println(next.getAttributesMap());
		}
	}
}
