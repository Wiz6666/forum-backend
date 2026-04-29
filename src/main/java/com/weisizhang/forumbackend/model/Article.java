package com.weisizhang.forumbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Long id;

    private Long boardId;

    private Long userid;

    private String title;

    private Integer visitCount;

    private Integer replyCount;

    private Integer likeCount;


    private Byte state;

    private Byte deleteState;

    private Date createTime;

    private Date updateTime;

    private String content;

    // 关联的用户信息
    private User user;

    // 关联的版块信息
    private Board board;

    // 用户是 不 是 作 者
    @Schema(description = "用户是不是作者")
    private boolean isOwn;
}