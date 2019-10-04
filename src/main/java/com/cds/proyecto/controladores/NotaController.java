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

import com.cds.proyecto.entidades.Grupoestudiante;
import com.cds.proyecto.entidades.Nota;
import com.cds.proyecto.repositorios.IGrupoestudianteRepository;
import com.cds.proyecto.repositorios.INotaRepository;

@Controller
@RequestMapping("notas")
public class NotaController {
	
	@Autowired
	INotaRepository erNota;
	
	@Autowired
	IGrupoestudianteRepository erGrupoestudiante;
	
	@GetMapping("index")
	public String Listar(Model m) {
		m.addAttribute("items", (List<Nota>) erNota.findAll());
		return "nota/listNota";
	}
	//vista de guardar
	@GetMapping("guardar")
	public String Guardar(Model model) {
		List<Grupoestudiante> grupoestudiantes=(List<Grupoestudiante>) erGrupoestudiante.findAll();
		model.addAttribute("grupoestudiante", grupoestudiantes);
		return "/nota/addNota";
	}
	@PostMapping(value="guardar")
	public String guardar(@RequestParam Integer grupoestudiante,@RequestParam Double nota, @RequestParam String observaciones) {
		@Valid Nota i=new Nota();
		Grupoestudiante g= erGrupoestudiante.findById(grupoestudiante).get();
		i.setGrupoestudiante(g);
		i.setNota(nota);
		i.setObservaciones(observaciones);
		erNota.save(i);
		return "redirect:/notas/index";
	}
	@GetMapping(value="modificar/{id_nota}")
	public String VistaModificar(@PathVariable Integer id_nota, Model model) {
		Nota i=erNota.findById(id_nota).get();
		model.addAttribute("item", i);
		List<Grupoestudiante> grupoestudiantes=(List<Grupoestudiante>) erGrupoestudiante.findAll();
		model.addAttribute("grupoestudiantes", grupoestudiantes);
		return "/nota/updateNota";
	}
	@PostMapping(value="modificar")
	public String modificar(@RequestParam Integer id_nota,@RequestParam Double nota, @RequestParam String observaciones, @RequestParam Integer grupoestudiante) {
		Nota i=new Nota(id_nota,nota, observaciones);
		Grupoestudiante g=erGrupoestudiante.findById(grupoestudiante).get();
		i.setGrupoestudiante(g);
		erNota.save(i);
		return "redirect:/notas/index";
	}
	@GetMapping(value="eliminar/{id_nota}")
	public String eliminar(@PathVariable Integer id_nota) {
		Nota i=erNota.findById(id_nota).get();
		erNota.delete(i);
		return "redirect:/notas/index";
	}
}
