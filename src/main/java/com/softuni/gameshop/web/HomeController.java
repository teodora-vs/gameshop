package com.softuni.gameshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/return-policy")
    public String returnPolicy(){
        return "return-policy";
    }

}
