package com.zq.u8Patch.vo;

import lombok.Data;

import java.util.Date;

@Data
public class PatchVo {

    private Integer id;

    private String title;
    private String desc;
    private String url;
    //todo 缩略
//    private String content;

    private Date publishedAt;
    private Date createdAt;
}
