package com.liu.elasticsearch.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;

import com.liu.elasticsearch.Blog;
import com.liu.utils.ClientBuilder;
import com.liu.utils.DateUtil;

public class BlogServiceImpl implements BlogService {

	@Override
	public List<Blog> fetchBlogInfoFromSearch(BlogParam para) {
		if(para == null){
			return null;
		}
		List<Blog> list = new ArrayList<Blog>();
		Client client = ClientBuilder.getJestClient();
		QueryBuilder queryDsl = QueryBuilders.constantScoreQuery(ElasticSearchQueryUtils.blogQueryWapper(para));
		FilterBuilder filter = ElasticSearchQueryUtils.blogFilterWapper(para);
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch("blog");
        // 设置查询索引类型
        searchRequestBuilder.setTypes("article");
        // 设置查询类型
        // 1.SearchType.DFS_QUERY_THEN_FETCH = 精确查询
        // 2.SearchType.SCAN = 扫描查询,无序
        // 3.SearchType.COUNT = 不设置的话,这个为默认值,还有的自己去试试吧
        searchRequestBuilder.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        searchRequestBuilder.addSort("posttime", SortOrder.DESC);
        searchRequestBuilder.setQuery(queryDsl);
        searchRequestBuilder.setPostFilter(filter);
        setShardRouting(searchRequestBuilder, para, "sys_user");
        // 创建查询索引
        //int startRow = pageIndex * pageSize;
        searchRequestBuilder.setFrom(para.getFrom());
        searchRequestBuilder.setSize(para.getSize());
        SearchResponse res = searchRequestBuilder.execute().actionGet();
        SearchHits hits = res.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            System.out.println("从搜索引擎检索的数据为"+json);
            Blog blog = com.liu.utils.FastJSONHelper.deserialize(json, Blog.class);
            list.add(blog);
        }
		return list;
	}

	private void setShardRouting(SearchRequestBuilder searchRequestBuilder, BlogParam para, String string) {
		Date endTime = para.getMarketTime() != null ? para.getMarketTime() : new Date();
        Date startTime = para.getPosttime() != null ? para.getPosttime() : DateUtil.addMonths(endTime, -3);
        Date start = DateUtil.getFirstDayOfTheMonth(startTime);
        Date end = DateUtil.getFirstDayOfTheMonth(endTime);
        List<String> routeTable = new ArrayList<String>();
        for (Date i = start; i.compareTo(end) <= 0; i = DateUtil.addMonths(i, 1)) {
            routeTable.add(DateUtil.formatMonth(i));
        }
        if (CollectionUtils.isNotEmpty(routeTable)) {
            searchRequestBuilder.setRouting(routeTable.toArray(new String[routeTable.size()]));
        } else {
            searchRequestBuilder.setRouting(DateUtil.formatMonth(start));
        }
	}

}
