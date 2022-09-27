package com.travelers.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kei
 * @since 2022-09-13
 */
@RestController
@RequestMapping("/api")
public class HomeController {
    @GetMapping({"","/"})
    public String hello() {
        return "hello";
    }
}
