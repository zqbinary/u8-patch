package com.zq.u8Patch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class IndexController {
    @RequestMapping()
    private String index() {
        return "redirect:patches";
    }
}
