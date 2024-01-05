package com.zq.u8Patch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zq.u8Patch.entity.Patch;
import com.zq.u8Patch.mapper.PatchMapper;
import com.zq.u8Patch.service.IPatchService;
import org.springframework.stereotype.Service;

@Service
public class PatchServiceImpl extends ServiceImpl<PatchMapper, Patch> implements IPatchService {
}
