package com.ctf.jxpath.controller;

import org.apache.commons.jxpath.JXPathContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    public MainController(){
    }

    @RequestMapping({"/"})
    @ResponseBody
    public String index(){
        return "welcome";
    }

    @RequestMapping({"/evil"})
    @ResponseBody
    public String evil(@RequestParam(name = "payload", required = false) String payload){
        JXPathContext context = JXPathContext.newContext(null);
        context.getValue(payload);
        return "you can get it!";
    }
}
