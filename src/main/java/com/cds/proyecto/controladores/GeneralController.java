package com.cds.proyecto.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cds.proyecto.entidades.Actividad;
import com.cds.proyecto.entidades.Grupo;
import com.cds.proyecto.entidades.Materia;
import com.cds.proyecto.entidades.Nota;
import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.repositorios.IGrupoRepository;
import com.cds.proyecto.repositorios.IMateriaRepository;
import com.cds.proyecto.repositorios.INotaRepository;
import com.cds.proyecto.service.NotaService;

@Controller
@RequestMapping("general")
public class GeneralController {

	@Autowired
	IMateriaRepository erMateria;
	@Autowired
	IGrupoRepository ergrupo;
	@Autowired
	INotaRepository erNota;
	@Autowired
	IActividadRepository erActividad;
	@Autowired
	NotaService daoNota;
	
	
	/*@GetMapping("listar")
	public String Vista(Model m) {
		m.addAttribute("materias", (List<Materia>) erMateria.findAll());
		m.addAttribute("grupos", (List<Grupo>) ergrupo.findAll());
		return "general/listGene";
	}*/
	
	@GetMapping("listar")
	public String VistaGeneral(Model m) {
		m.addAttribute("materias", (List<Materia>) erMateria.findAll());
		m.addAttribute("grupos", (List<Grupo>) ergrupo.findAll());
		return "/general/indexGeneral";
	}
	
	//metodo para listar actividades
	@GetMapping(value = "allActividad", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Actividad> getActividades() {
		return (List<Actividad>) daoNota.getAllActividades();
	}
	
	//metodo para mandar datos mediante consulta especializada
	@GetMapping(value = "reporteNota",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object Notareporte(@RequestParam String materia,@RequestParam String grupo){
		
		//Se crea una lista de hashmap para asignar un seudonimo
		List<HashMap<String,Object>> objetos = new ArrayList<>();
		//Se crea una lista para inicializar los datos
		List<Nota> ntg = erNota.getNotasEstudiantesActividades(materia, grupo);
		

		//se genera un for para recorrer la lista
		for (Nota nota : ntg) {
		HashMap<String,Object> hm = new HashMap<>();
		hm.put("estudiante", nota.getGrupoestudiante().getEstudiante().getNombre().toString()+"  "+nota.getGrupoestudiante().getEstudiante().getApellido().toString());
		hm.put("actividad", nota.getActividad().getNombre().toString()+" = ");
		hm.put("nota", nota.getNota());
		
		objetos.add(hm);
		}
		return Collections.singletonMap("data", objetos);
	}
	//metodo para mandar datos de actividad mediante consulta especializada
		@GetMapping(value = "reporteActividad",produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public List<Actividad> reporteActividad(@RequestParam String materia){
			return erActividad.getActividades(materia);
		}
		
		
}
