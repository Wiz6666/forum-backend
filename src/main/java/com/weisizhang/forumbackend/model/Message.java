package com.weisizhang.forumbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;

    private Long postuserid;

    private Long receiveuserid;

    private String content;

    private Byte state;

    private Date createtime;

    private Date updatetime;
}