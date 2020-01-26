package com.marcellus.recipes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class IndexController {

    public String getIndex(){

        System.out.println("Some message to say...");
        return "index";
    }
}
