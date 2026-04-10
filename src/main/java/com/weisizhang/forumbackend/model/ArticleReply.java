package com.weisizhang.forumbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleReply {
    private Long id;

    private Long articleid;

    private Long postuserid;

    private Long replyid;

    private Long replyuserid;

    private String content;

    private Integer likecount;

    private Byte state;

    private Byte deletestate;

    private Date createtime;

    private Date updatetime;

}