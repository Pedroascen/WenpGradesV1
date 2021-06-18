package com.cds.proyecto.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cds.proyecto.entidades.Estudiante;
import com.cds.proyecto.entidades.Grupo;
import com.cds.proyecto.entidades.Grupoestudiante;
import com.cds.proyecto.repositorios.IEstudianteRepository;
import com.cds.proyecto.repositorios.IGrupoRepository;
import com.cds.proyecto.repositorios.IGrupoestudianteRepository;
import com.cds.proyecto.service.EstudianteService;

@Controller
@RequestMapping("grupoestudiantes")
public class GrupoEstudianteController {

	@Autowired
	IGrupoestudianteRepository erGrupoestudiante;
	
	@Autowired
	IEstudianteRepository erEstudiante;
	
	@Autowired
	EstudianteService daoEstudiante;
	
	
	@Autowired
	IGrupoRepository erGrupo;
	
	@GetMapping("index")
	public String Listar(Model m) {
		m.addAttribute("items", (List<Grupoestudiante>) erGrupoestudiante.findAll());
		return "/grupoEstu/listGrupEst";
	}
	
	@GetMapping(value="allEstudiantes",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object allEstudiantes() {
		//creamos un listado de tipo HashMap
		List<HashMap<String,Object>> objetos = new ArrayList<>();
		
		//Se crea un listado de la entidad
		List<Estudiante> e = daoEstudiante.getAllEstudiantes();
		
		//se genera un for para recorrer los datos
		for (Estudiante estudiante : e) {
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("id_estudiante", estudiante.getId_estudiante().toString());
			hm.put("nombre", estudiante.getNombre().toString());
			hm.put("apellido", estudiante.getApellido().toString());
			hm.put("id_institucion",estudiante.getInstitucion().getNombre());
			hm.put("opciones", "<button type='button' class='btn btn-primary mr-2 SelectEstu' data-dismiss='modal'>Seleccionar</button>");
			
			objetos.add(hm);
		}
		return Collections.singletonMap("data",objetos);
	}
	
	@GetMapping("guardar")
	public String VistaGuardar (Model model) {
		
		List<Estudiante> estudiantes=(List<Estudiante>) erEstudiante.findAll();
		model.addAttribute("estudiantes", estudiantes);
		
		
		List<Grupo> grupos=(List<Grupo>) erGrupo.findAll();
		model.addAttribute("grupos", grupos);
		
		
		return "/grupoEstu/addGrupEst";
	}
	
	@PostMapping(value="guardar")
	public String Guardar (@RequestParam Integer id_estudiante, @RequestParam Integer grupo) {
		
		@Valid Grupoestudiante ge = new Grupoestudiante();
		
		Estudiante e = erEstudiante.findById(id_estudiante).get();
		ge.setEstudiante(e);
		Grupo g = erGrupo.findById(grupo).get();
		ge.setGrupo(g);
		
		
		erGrupoestudiante.save(ge);
		
		return "redirect:/grupoestudiantes/index";
	}
	
	@GetMapping(value="modificar/{id_grupoestudiante}")
	public String VistaModificar(@PathVariable Integer id_grupoestudiante, Model model) {
		
		Grupoestudiante ge = erGrupoestudiante.findById(id_grupoestudiante).get();
		model.addAttribute("item", ge);
		
		List<Estudiante> estudiante = (List<Estudiante>) erEstudiante.findAll();
		model.addAttribute("estudiantes", estudiante);
		
		List<Grupo> grupos = (List<Grupo>) erGrupo.findAll();
		model.addAttribute("grupos", grupos);
		
		return "/grupoEstu/updateGrupEst";
	}
	
	@PostMapping(value="modificar")
	public String Modificar(@RequestParam Integer id_grupoestudiante, @RequestParam Integer id_estudiante, @RequestParam Integer grupo) {
		Grupoestudiante ge = new Grupoestudiante(id_grupoestudiante);
		Estudiante e = erEstudiante.findById(id_estudiante).get();
		ge.setEstudiante(e);
		Grupo g = erGrupo.findById(grupo).get();
		ge.setGrupo(g);
		erGrupoestudiante.save(ge);
		return "redirect:/grupoestudiantes/index";
	}
	
	@GetMapping("eliminar/{id_grupoestudiante}")
	public String eliminar(@PathVariable Integer id_grupoestudiante) {
		Grupoestudiante ge = erGrupoestudiante.findById(id_grupoestudiante).get();
		erGrupoestudiante.deleteById(id_grupoestudiante);
		return "redirect:/grupoestudiantes/index";
	}
	
	@GetMapping(value = "reporteEstudiante",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Grupoestudiante> reporte(@RequestParam String materia, @RequestParam String grupo){
		return	erGrupoestudiante.getEstudiantesGruposMaterias(materia,grupo);
	}
}
