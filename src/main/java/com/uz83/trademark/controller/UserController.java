package com.uz83.trademark.controller;

import com.alibaba.fastjson.JSONObject;
import com.uz83.trademark.model.SysUser;
import com.uz83.trademark.service.ISysUserService;
import com.uz83.trademark.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RequestMapping("/user")
@Controller
public class UserController {

    @Resource
    private ISysUserService sysUserService;

    @GetMapping(value = {"", "/", "/index"})
    public String index() {
        return "/user/index";
    }

    @GetMapping("/add")
    public Object add() {
        return "user";
    }

    /**
     * 根据ID查询数据
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public String get(Model model, @PathVariable("id") Integer id) {
        SysUser user = sysUserService.selectByPrimaryKey(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/view";
        }
        return "/common/noRecord";
    }

    /**
     * 按ID编辑数据
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        SysUser user = sysUserService.selectByPrimaryKey(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/edit";
        }
        return "/common/noRecord";
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(@ModelAttribute SysUser user) {
        JSONObject json = new JSONObject();
        int count = sysUserService.updateByPrimaryKeySelective(user);
        if (count > 0) {
            json.put("state", true);
        } else {
            json.put("state", false);
        }
        return json.toJSONString();
    }

}
