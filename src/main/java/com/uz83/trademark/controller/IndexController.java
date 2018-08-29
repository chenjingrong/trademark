package com.uz83.trademark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    /**
     * 登录页面
     *
     * @param model
     * @param error
     * @return
     */
    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "index";
    }


}
