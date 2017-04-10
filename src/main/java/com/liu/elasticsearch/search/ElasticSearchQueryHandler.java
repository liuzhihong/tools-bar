package com.liu.elasticsearch.search;

import java.util.ArrayList;
import java.util.List;

import com.liu.elasticsearch.Blog;
import com.liu.utils.DateTool;

public class ElasticSearchQueryHandler {

	public static void main(String[] args) {
		BlogServiceImpl blogServiceImpl = new BlogServiceImpl();
		BlogParam para = new BlogParam();
		para.setFrom(0);
		para.setSize(4);
		/*List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(3);
		para.setIds(ids);*/
		para.setPosttime(DateTool.stringToDate("2016-06-19"));
		para.setMarketTime(DateTool.stringToDate("2017-03-20"));
		//para.setTitle("Shell基本知识");
		para.setContent("Hibernate");
		List<Blog> list = blogServiceImpl.fetchBlogInfoFromSearch(para);
		//System.out.println(FastJsonHelper.serialize(list));
	}
}
