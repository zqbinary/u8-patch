package com.zq.u8Patch.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.Date;

@ToString
@Data
@TableName("patches")
@Component
@Accessors(chain = true)
public class Patch {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String title;
    private String info;
    private String url;
    private String content;

    private Date publishedAt;
    //todo test
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
}
