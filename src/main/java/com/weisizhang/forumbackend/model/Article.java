package com.weisizhang.forumbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Long id;

    private Long boardId;

    private Long userid;

    private String title;

    private Integer vistCount;

    private Integer replyCount;

    private Integer likeCount;


    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;

    private String content;
}