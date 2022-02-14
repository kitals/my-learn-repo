package org.hrj.s.xsd;

import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XsdDom4jTest {
	public static void main(String[] args) {
		String xsd = "E:\\Plugin\\hrj-s-xsd\\src\\main\\resources\\fix.xsd";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(xsd));
			
			Element rootElement = document.getRootElement();
			System.out.println(rootElement.getName());
			System.out.println(rootElement.getNamespacePrefix());
			System.out.println(rootElement.getNamespace().getText());
			System.out.println(rootElement.getNamespaceForPrefix(rootElement.getNamespacePrefix()).getText());
			
			System.out.println(rootElement.attributes().size());
			List<Attribute> attributes = rootElement.attributes();
			for (Attribute attribute : attributes) {
				System.out.println(attribute.getName()+":"+attribute.getStringValue());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
