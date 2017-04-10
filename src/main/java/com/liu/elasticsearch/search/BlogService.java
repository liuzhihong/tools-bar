package com.liu.elasticsearch.search;

import java.util.List;

import com.liu.elasticsearch.Blog;

public interface BlogService {

	List<Blog> fetchBlogInfoFromSearch(BlogParam para);
}
