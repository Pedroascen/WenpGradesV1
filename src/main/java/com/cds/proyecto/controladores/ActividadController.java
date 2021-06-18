package com.cds.proyecto.controladores;

import java.util.*;

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
import com.cds.proyecto.entidades.Actividad;
import com.cds.proyecto.entidades.Grupoestudiante;
import com.cds.proyecto.entidades.Materia;
import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.repositorios.IGrupoestudianteRepository;
import com.cds.proyecto.repositorios.IMateriaRepository;
import com.cds.proyecto.repositorios.INotaRepository;
import com.cds.proyecto.service.ActividadService;

@Controller
@RequestMapping("actividades")
public class ActividadController {
	
	@Autowired
	IActividadRepository erActividad;
	
	@Autowired
	IMateriaRepository erMateria;
	
	@Autowired
	INotaRepository erNota;
	
	@Autowired
	IGrupoestudianteRepository erGrupoEstudiante;
	
	@Autowired
	ActividadService daoActividad;
	
	//listado de actividades
	@GetMapping("index")
	public String index(Model m) {
		m.addAttribute("items", (List<Actividad>) erActividad.findAll());
		
		
		return "actividad/listAct";
	}
	//Guardar desde el modal
	@GetMapping(value = "allMaterias",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object allMateria() {
		
		//se crea una lista de hashmap para asignar un seudoqnio
		List<HashMap<String, Object>> objetos = new ArrayList<>();
		
		//se crea uan lista para inicializar los datos
		List<Materia> m = daoActividad.getAllMaterias();
		
		//se genera el for para recorrer los datos
		for (Materia materia : m) {
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("id_materia", materia.getId_materia());
			hm.put("nombre", materia.getNombre().toString());
			hm.put("descripcion", materia.getDescripcion().toString());
			hm.put("especialidad",materia.getEspecialidad().toString());
			hm.put("opciones", "<button type='button' class='btn btn-primary mr-2 SelectMat' data-dismiss='modal'>Seleccionar</button>");
		
			objetos.add(hm);
		}
			return Collections.singletonMap("data",objetos);
	}

	
	
	@GetMapping("guardar")
	public String Viewguardar(Model model,@RequestParam(required = false) Materia materia) {
		/*
		 * List<Materia> materias=(List<Materia>) erMateria.findAll();
		 * model.addAttribute("materias", daoActividad.getAllMaterias());
		 */
		
		//poderacionTotal
		return "actividad/addAct";
	}
	
	//envio de datos para guardar
	@PostMapping(value="guardar")
	public String guardar(@RequestParam Integer id_materia,@RequestParam String nombre, @RequestParam String fecha, @RequestParam String descripcion, @RequestParam Double ponderacion) {	
		
		@Valid Actividad a=new Actividad();
		Materia m=erMateria.findById(id_materia).get();
		a.setMateria(m);
		a.setNombre(nombre);
		a.setFecha(fecha);
		a.setDescripcion(descripcion);				
		
		 //INVOCACION DEL METODO
		daoActividad.getPonderacion(id_materia); 
		Double resultado=daoActividad.getPonderacion(id_materia);	
		
		  if(ponderacion > resultado) {
		  System.err.print("Error: Sobrepaso la ponderacion"); 
		  
		 }else {
		  a.setPonderacion(ponderacion); erActividad.save(a);
		  System.out.println("si guardo"+id_materia); 
		  }		
		return "redirect:/actividades/index";
	}
	
	@GetMapping(value="modificar/{id_actividad}")
	 public String VistaModificar( @PathVariable Integer id_actividad, Model model) {
		 Actividad a=erActividad.findById(id_actividad).get();
		 model.addAttribute("item", a);
		 List<Materia> materia=(List<Materia>) erMateria.findAll();
		 model.addAttribute("materias", materia);
		 return "/actividad/updateAct";
	 }
	
	//Vista modificar
	@PostMapping(value="modificar")
	public String modificar(@RequestParam Integer id_actividad,@RequestParam Integer id_materia, @RequestParam String nombre, @RequestParam String fecha, @RequestParam String descripcion, @RequestParam Double ponderacion) {
		Actividad a=new Actividad();
		Materia m=erMateria.findById(id_materia).get();
		a.setMateria(m);
		a.setId_actividad(id_actividad);
		a.setNombre(nombre);
		a.setFecha(fecha);
		a.setDescripcion(descripcion);
		a.setPonderacion(ponderacion);
		
		erActividad.save(a);
		
		return "redirect:/actividades/index";
	}
	
	//Eliminar registro
	@GetMapping("eliminar/{id_actividad}")
	public String eliminar(@PathVariable Integer id_actividad) {
		Actividad a= erActividad.findById(id_actividad).get();
		erActividad.deleteById(id_actividad);
		return "redirect:/actividades/index";
	}
	
	@GetMapping(value = "reporteEstudiantes",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Grupoestudiante> reporteEstudiantes(@RequestParam String materia,@RequestParam String grupo){
		return erGrupoEstudiante.getEstudiantesGruposMaterias(materia,grupo);
	}
}