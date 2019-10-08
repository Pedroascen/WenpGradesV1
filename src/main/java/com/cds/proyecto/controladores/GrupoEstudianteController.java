package com.cds.proyecto.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cds.proyecto.repositorios.IEstudianteRepository;
import com.cds.proyecto.repositorios.IGrupoRepository;

@Controller
@RequestMapping("grupoestudiante")
public class GrupoEstudianteController {

	@Autowired
	IEstudianteRepository erEstudiante;
	
	@Autowired
	IGrupoRepository erGrupo;
	

}
