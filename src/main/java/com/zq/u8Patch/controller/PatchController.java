package com.zq.u8Patch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zq.u8Patch.entity.Patch;
import com.zq.u8Patch.service.IPatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;


@Controller
@RequestMapping("/patches")
public class PatchController {
    @Autowired
    private IPatchService patchService;


    //todo result 封装
    @RequestMapping("")
    private String list(Model model
            , @RequestParam(name = "keyword", required = false) String keyword
            , @RequestParam(name = "page", defaultValue = "1") Long pageNo
            , @RequestParam(name = "size", defaultValue = "10") Long pageSize
    ) {

        QueryWrapper<Patch> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            String[] keywords = keyword.split(" ");
            for (String s : keywords) {
                wrapper = wrapper.like("content", s);
            }
        }
        Page<Patch> page = new Page<>(pageNo, pageSize);
        Page<Patch> pageRes = patchService.page(page, wrapper);
        for (Patch record : pageRes.getRecords()) {
            record.setContent("");
        }

        model.addAttribute("title", "补丁列表");
        model.addAttribute("data", pageRes);
        return "patches";
    }

    @RequestMapping(value = "/{id}")
    private String index(@PathVariable("id") Long id, Model model) {
        Patch patch = patchService.getById(id);
        model.addAttribute("title", patch.getTitle());
        model.addAttribute("data", patch);
        return "patch";
    }

}
