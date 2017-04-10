package com.liu.utils;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;

public class XmlUtil {

	public static Object xml2Object(String inputXml, Class<?>[] types){
		if (StringUtils.isBlank(inputXml)) {
			return null;
		}
		XStream xstream = new XStream();
		xstream.processAnnotations(types);
		return xstream.fromXML(inputXml);
	}

	public static String object2Xml(Object ro, Class<?>[] types){
		if (null == ro) {
			return null;
		}
		XStream xstream = new XStream();
		xstream.processAnnotations(types);
		return xstream.toXML(ro);
	}

}
