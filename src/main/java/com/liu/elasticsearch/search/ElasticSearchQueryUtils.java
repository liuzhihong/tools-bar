package com.liu.elasticsearch.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

public class ElasticSearchQueryUtils {

	private static Pattern pattern = Pattern.compile("^[A-Za-z\\.]+$");
	
	public static QueryBuilder blogQueryWapper(BlogParam request) {
        BoolQueryBuilder mustQueryBuilder=QueryBuilders.boolQuery();
        
        if (request.getTitle()!= null) {
            MatchQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("title", request.getTitle());
            mustQueryBuilder = mustQueryBuilder.must(matchPhraseQueryBuilder);
        }
        
        /*if (request.getPosttime() != null) {
            MatchQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("posttime", request.getPosttime());
            mustQueryBuilder = mustQueryBuilder.must(matchPhraseQueryBuilder);
        }*/
        if(request.getContent() !=null){
            Matcher m = pattern.matcher(request.getContent());
            if(m.find()){
                //通配符查询
                WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("content", "*"+request.getContent().toLowerCase()+"*");
                mustQueryBuilder = mustQueryBuilder.must(wildcardQueryBuilder);
            }else{
                MatchQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("content",request.getContent());
                mustQueryBuilder = mustQueryBuilder.must(matchPhraseQueryBuilder);
            }

        }
        return mustQueryBuilder;
    }
	
	public static FilterBuilder blogFilterWapper(BlogParam request) {
        AndFilterBuilder andFilterBuilder = FilterBuilders.andFilter();
        
        if (request.getTitle() != null) {
            andFilterBuilder.add(FilterBuilders.termFilter("title", request.getTitle()));
        }
        
        if (request.getPosttime() != null || request.getMarketTime() != null) {
            RangeFilterBuilder rangeFilterBuilder=FilterBuilders.rangeFilter("posttime");
            if (request.getPosttime() != null) {
                rangeFilterBuilder.gte(request.getPosttime().getTime());
            }
            if (request.getMarketTime() != null) {
                rangeFilterBuilder.lte(request.getMarketTime().getTime());
            }
            andFilterBuilder.add(rangeFilterBuilder);
        }

        if (request.getId() != null) {
            andFilterBuilder.add(FilterBuilders.termFilter("id", request.getId()));
        }else if(CollectionUtils.isNotEmpty(request.getIds())){
            andFilterBuilder.add(FilterBuilders.inFilter("id", request.getIds().toArray()));
        }
        /*if (request.getContent() != null) {
            andFilterBuilder.add(FilterBuilders.termFilter("content", request.getContent()));
        }*/
        /*if(request.getIsHaveMemo()!=null&&request.getIsHaveMemo()){
            andFilterBuilder.add(FilterBuilders.orFilter(FilterBuilders.existsFilter("order.sellerMemo"),
                    FilterBuilders.existsFilter("order.buyerMemo")));
        }
        if(request.getIsHaveMemo()!=null&&(!request.getIsHaveMemo())){
            andFilterBuilder.add(FilterBuilders.missingFilter(("order.sellerMemo")));
            andFilterBuilder.add(FilterBuilders.missingFilter(("order.buyerMemo")));
        }
        if(request.getStatus()!=null&&request.getStatus()==(byte)0){
            andFilterBuilder.add(FilterBuilders.notFilter(FilterBuilders.termFilter("items.status",(byte)1)));
        }*/
        return andFilterBuilder;
    }
}
