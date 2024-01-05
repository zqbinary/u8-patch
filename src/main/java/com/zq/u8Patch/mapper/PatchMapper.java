package com.zq.u8Patch.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zq.u8Patch.entity.Patch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PatchMapper extends BaseMapper<Patch> {
}
