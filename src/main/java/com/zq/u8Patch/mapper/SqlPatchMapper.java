package com.zq.u8Patch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zq.u8Patch.entity.SqlPatch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//todo mapper 什么用
@Repository
@Mapper
public interface SqlPatchMapper extends BaseMapper<SqlPatch> {
}
