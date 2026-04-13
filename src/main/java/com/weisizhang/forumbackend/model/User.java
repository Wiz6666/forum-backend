package com.weisizhang.forumbackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String phonenum;

    private String email;

    private Byte gender;

    private String salt;

    private String avatarurl;

    private Integer articlecount;

    private Byte isadmin;

    private String remark;

    private Byte state;

    private Byte deletestate;

    private Date createtime;

    private Date updatetime;
}