package com.zq.u8Patch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zq.u8Patch.entity.SqlPatch;
import com.zq.u8Patch.mapper.SqlPatchMapper;
import com.zq.u8Patch.service.ISqlPatchService;
import org.springframework.stereotype.Service;

@Service
public class SqlPatchServiceImpl extends ServiceImpl<SqlPatchMapper, SqlPatch> implements ISqlPatchService {
}
