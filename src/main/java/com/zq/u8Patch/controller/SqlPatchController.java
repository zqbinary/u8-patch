package com.zq.u8Patch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zq.u8Patch.entity.SqlPatch;
import com.zq.u8Patch.service.ISqlPatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/sql-patches")
public class SqlPatchController {
    @Autowired
    private ISqlPatchService sqlPatchService;

    @RequestMapping(value = "/{id}")
    private String detail(@PathVariable("id") Long id, Model model) {
        SqlPatch patch = sqlPatchService.getById(id);
        model.addAttribute("title", patch.getTitle());
        model.addAttribute("data", patch);
        return "sql_patch";
    }

    @RequestMapping("")
    private String list(Model model
            , @RequestParam(name = "keyword", required = false) String keyword
            , @RequestParam(name = "page", defaultValue = "1") Long pageNo
            , @RequestParam(name = "size", defaultValue = "10") Long pageSize
    ) {

        QueryWrapper<SqlPatch> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            String[] keywords = keyword.split(" ");
            for (String s : keywords) {
                wrapper = wrapper.like("content", s);
            }
        }
        Page<SqlPatch> page = new Page<>(pageNo, pageSize);
        Page<SqlPatch> pageRes = sqlPatchService.page(page, wrapper);
        for (SqlPatch record : pageRes.getRecords()) {
            record.setContent(null);
        }

        model.addAttribute("title", "sql补丁列表");
        model.addAttribute("data", pageRes);
        return "sql_patches";
    }
}
