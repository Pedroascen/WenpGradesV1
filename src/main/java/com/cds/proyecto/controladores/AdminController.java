package com.cds.proyecto.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ingresar")
public class AdminController {
	
	@GetMapping("index")
	public String logi(){
		return "administration/login";
	}

	@GetMapping("inicio")
	public String Dash() {
		return "components/somos";
	}
	
	@GetMapping("prueba")
	public String Dashbo() {
		return "components/carrusel";
	}
}
