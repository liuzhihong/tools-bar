package com.liu.elasticsearch;

import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public class JsonUtils {

	public static String modelToJson(Blog blog){
		String jsonData = null;
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject().field("id", blog.getId()).field("title", blog.getTitle())
			.field("posttime", blog.getPosttime()).field("content", blog.getContent()).endObject();
			 
			jsonData = builder.string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonData;
	}
}
