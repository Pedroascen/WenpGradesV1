package com.cds.proyecto.controladores;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cds.proyecto.entidades.Actividad;
import com.cds.proyecto.entidades.Nota;
import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.repositorios.INotaRepository;

@Controller
@RequestMapping("actividades")
public class ActividadController {
	@Autowired
	IActividadRepository erActividad;
	@Autowired
	INotaRepository erNota;
	
	//listado de actividades
	@GetMapping("index")
	public String index(Model m) {
		m.addAttribute("items", (List<Actividad>) erActividad.findAll());
		return "actividad/listAct";
	}
	//vista guardar
	@GetMapping("guardar")
	public String Viewguardar(Model model) {
		List<Nota> notas=(List<Nota>) erNota.findAll();
		model.addAttribute("notas", notas);
		return "actividad/addAct";
	}
	//envio de datos para guardar
	@PostMapping(value="guardar")
	public String Postguardar(@RequestParam Integer nota, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam String ponderacion) {	
		@Valid Actividad a=new Actividad();
		Nota n=erNota.findById(nota).get();
		a.setNota(n);
		a.setNombre(nombre);
		a.setDescripcion(descripcion);
		a.setPonderacion(ponderacion);
		erActividad.save(a);
		return "redirect:/actividad/index";
	}
	//Vista modificar
	@PostMapping(value="modificar")
	public String modificar(@RequestParam Integer id_actividad, @RequestParam Integer nota, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam String ponderacion) {
	 Actividad a=new Actividad();
	 Nota n=erNota.findById(nota).get();
	 a.setId_actividad(id_actividad);
	 a.setNota(n);
	 a.setNombre(nombre);
	 a.setDescripcion(descripcion);
	 a.setPonderacion(ponderacion);
	 erActividad.save(a);
	 return "redirect:/actividades/index";
	}
	//Eliminar registro
	@GetMapping("eliminar/{id_actividad}")
	public String eliminar(@PathVariable Integer id_actividad) {
		Actividad a= erActividad.findById(id_actividad).get();
		erActividad.delete(a);
		return "redirect:/actividades/index";
	}
}