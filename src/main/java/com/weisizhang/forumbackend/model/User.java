package com.weisizhang.forumbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @JsonIgnore
    private String password;
    @Schema(description = "昵称")
    private String nickname;
    @Schema(description = "电话号码")
    private String phonenum;
    @Schema(description = "邮箱")
    private String email;
    @Schema(description = "性别")
    private Byte gender;

    @JsonIgnore
    private String salt;
    @Schema(description = "头像地址")
    private String avatarurl;
    @Schema(description = "发帖数量")
    private Integer articleCount;
    @Schema(description = "是否管理员")
    private Byte isadmin;
    @Schema(description = "个人简介")
    private String remark;
    @Schema(description = "用户状态")
    private Byte state;

    @JsonIgnore
    private Byte deletestate;
    @Schema(description = "注册时间")
    private Date createtime;

    private Date updatetime;
}