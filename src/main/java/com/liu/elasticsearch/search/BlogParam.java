package com.liu.elasticsearch.search;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BlogParam {

	private Integer id;//主键id
	private List<Integer> ids;
    private String title;//标题
    private Date posttime;//发版时间
    private Date marketTime;//上市时间
    private String content;//简介
    private Integer from;
    private Integer size;
}
