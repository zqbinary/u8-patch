package com.zq.u8Patch.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Accessors(chain = true)
@ToString
@TableName("sql_patches")
@Repository
public class SqlPatch {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date runAt;
    private Integer sort;
    private String title;
    private String content;


}
