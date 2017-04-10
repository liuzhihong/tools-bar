package com.liu.elasticsearch;

import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;

import com.liu.utils.ClientBuilder;

public class ElasticSearchHandler {

	public static void main(String[] args) {
		Client client = ClientBuilder.getJestClient();
		List<String> jsonData = DataFactory.getInitJsonData();
		for(String str : jsonData){
			IndexResponse response = client.prepareIndex("blog", "article").setSource(str).get();
			if(response.isCreated()){
				System.out.println("创建成功!"+response.getIndex()+":"+response.getType());
			}
		}
		/*QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(queryBuilder).execute().actionGet();
		SearchHits searchHits = searchResponse.getHits();
		Date start = DateTool.stringToDate("2017-03-01");
		try {
			for(SearchHit hit : searchHits){
				UpdateRequest updateRequest = new UpdateRequest();
				updateRequest.index("blog");
				updateRequest.type("article");
				updateRequest.id(hit.getId());
				updateRequest.doc(XContentFactory.jsonBuilder().startObject().field("marketTime", start).endObject());
				UpdateResponse response = client.update(updateRequest).actionGet();
				start = DateUtils.addDays(start, 1);
			}
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.close();
		}*/
		//删除索引
		//client.admin().indices().prepareDelete("blog").execute().actionGet();
	}
}
