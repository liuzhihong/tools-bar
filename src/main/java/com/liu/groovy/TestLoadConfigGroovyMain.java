package com.liu.groovy;

import java.io.IOException;

import groovy.lang.GroovyObject;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

public class TestLoadConfigGroovyMain {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ResourceException, ScriptException{
		String[] roots = new String[] { "src/main/java/com/liu/groovy/" };
		// 通过指定的roots来初始化GroovyScriptEngine
		GroovyScriptEngine gse = new GroovyScriptEngine(roots);
		GroovyObject groovyObject = (GroovyObject) gse.loadScriptByName("LoadConfigGroovy.groovy").newInstance();
		String result = (String) groovyObject.invokeMethod("getConfig", "你好");
		System.out.println(result);
	}
}