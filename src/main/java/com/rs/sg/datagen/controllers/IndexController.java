package com.rs.sg.datagen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/generator", method = RequestMethod.GET)
    String home(Model model) {
        //model.addAttribute("fam_name", "Glushkov");
        return "generator.xhtml";
    }

    @RequestMapping(value = "/generator", method = RequestMethod.POST)
    String onPost() {

        return "asas";
    }

}
