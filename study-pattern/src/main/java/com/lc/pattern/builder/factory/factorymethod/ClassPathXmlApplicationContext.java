package com.lc.pattern.builder.factory.factorymethod;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * org.springframework.context.support.ClassPathXmlApplicationContext
 * @author LittleCharacter
 */
public class ClassPathXmlApplicationContext implements BeanFactory{
	Map<String, Object> container = new HashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	public ClassPathXmlApplicationContext(String fileName) throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(ClassPathXmlApplicationContext.class.getClassLoader().getResourceAsStream(fileName));
		Element root = document.getRootElement();
		Iterator<Element> iterator = root.elementIterator("bean");
		while(iterator.hasNext()){
			Element bean = iterator.next();
			
			String id = bean.attributeValue("id");
			String clazz = bean.attributeValue("class");
			
			Object object = Class.forName(clazz).newInstance();
			
			container.put(id, object);
		}
	}
	
	@Override
	public Object getBean(String id) {
		return container.get(id);
	}
}
