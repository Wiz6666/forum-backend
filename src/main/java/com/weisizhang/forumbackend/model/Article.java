package com.weisizhang.forumbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Long id;

    private Long boardid;

    private Long userid;

    private String title;

    private Integer vistcount;

    private Integer replycount;

    private Integer likecount;

    private Byte state;

    private Byte deletestate;

    private Date createtime;

    private Date updatetime;

    private String content;
}