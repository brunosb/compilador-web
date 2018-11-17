package br.com.uece.compilador.compiladorweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Bruno Barbosa - 09/11/2018
 */
@Controller
public class HomeController {

    @RequestMapping({"/home", "/"})
    public String home() {
        return "home";
    }
}
