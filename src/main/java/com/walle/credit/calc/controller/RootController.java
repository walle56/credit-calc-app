package com.walle.credit.calc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class will be used later when frontend will be implemented
 */
//@Controller
public class RootController {

    // spring reads file from 'static' folder with 'index.html' name
    @RequestMapping("/")
    public String index(Model model) {
        return "index.html";
    }

}
