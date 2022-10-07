package com.alunoapp.alunoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//Classe que invoca página na web

@Controller
public class IndexController {
	
	@RequestMapping("/ ")
	public String index() {
		return "src/main/resources/templates/index";
	}
}
