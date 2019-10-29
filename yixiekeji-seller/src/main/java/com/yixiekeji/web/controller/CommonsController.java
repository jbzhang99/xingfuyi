package com.yixiekeji.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "seller/system")
public class CommonsController extends BaseController {

    @RequestMapping(value = "error404", method = RequestMethod.GET)
    public String error404() {
        return "seller/404";
    }

    @RequestMapping(value = "error500", method = RequestMethod.GET)
    public String error500() {
        return "seller/500";
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public String error() {
        return "seller/500";
    }
}
