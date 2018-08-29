package com.uz83.trademark.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/order")
@RestController
public class OrderController {

    @GetMapping("/get")
    public Object get() {
        return "order---panyh";
    }

}
