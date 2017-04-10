package com.liu.elasticsearch;

import java.util.Date;

import lombok.Data;

@Data
public class Blog {

	private Integer id;
    private String title;
    private Date posttime;
    private String content;

    public Blog() {
    }

    public Blog(Integer id, String title, Date posttime, String content) {
        this.id = id;
        this.title = title;
        this.posttime = posttime;
        this.content = content;
    }
}
