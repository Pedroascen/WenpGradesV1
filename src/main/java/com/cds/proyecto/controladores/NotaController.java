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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cds.proyecto.entidades.Actividad;
import com.cds.proyecto.entidades.Estudiante;
import com.cds.proyecto.entidades.Grupo;
import com.cds.proyecto.entidades.Grupoestudiante;
import com.cds.proyecto.entidades.Institucion;
import com.cds.proyecto.entidades.Materia;
import com.cds.proyecto.entidades.Nota;
import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.repositorios.IGrupoRepository;
import com.cds.proyecto.repositorios.IGrupoestudianteRepository;
import com.cds.proyecto.repositorios.IMateriaRepository;
import com.cds.proyecto.repositorios.INotaRepository;
import com.cds.proyecto.service.NotaService;

@Controller
@RequestMapping("notas")
public class NotaController {
	
	@Autowired
	NotaService daoNota;
	
	@Autowired
	IMateriaRepository erMateria;
	
	@Autowired
	IActividadRepository erActividad;
	
	@Autowired
	IGrupoRepository erGrupo;
	
	@Autowired
	INotaRepository erNota;
	
	@Autowired
	IGrupoestudianteRepository erGrupoEstudiante;
	
	//metodo para guardar las notas en la ram
	public static List<Nota> notasHechas = new ArrayList<Nota>(); 

	//cosntructor para la lista de notas
	public NotaController() {
		notasHechas = new ArrayList<>();
	}
	
	//metodo para guardar los datos de notas
	@GetMapping(value="agregarNota")
	@ResponseBody
	public Nota agregarNota(
			@RequestParam Integer id_actividad,
			@RequestParam Integer id_grupoestudiante,
			@RequestParam Double nota,
			@RequestParam String observaciones) {
		
		Nota nt = new Nota();
			 nt.setActividad(daoNota.getActividad(id_actividad));
			 nt.setGrupoestudiante(daoNota.getGrupoestudiante(id_grupoestudiante));
			 nt.setNota(nota);
			 nt.setObservaciones(observaciones);
			 
			 notasHechas.add(nt);
			 return nt;
	}
	
	//metodo para listar las notas de la rom
	@GetMapping("allNotas")
	@ResponseBody
	public List<Nota> getAllNota() {
		return (List<Nota>) daoNota.getAllNotas();
	}
	
	//metodo para resetear las notas
	/*
	 * @GetMapping("resetLisNota")
	 * 
	 * @ResponseBody public Object resetDetalles() { notasHechas = new
	 * ArrayList<>(); notasHechas.clear(); return "Lista Reseteada"; }
	 */
	
	//Metodo para llamar la vista index
	@GetMapping("index")
	public String vista(Model m) {
		m.addAttribute("materias", (List<Materia>) erMateria.findAll());
		m.addAttribute("grupos", (List<Grupo>) erGrupo.findAll());
		m.addAttribute("actividades", (List<Actividad>) erActividad.findAll());
		
		return "nota/indexNotas";
	}
	
	//metodo para mandar datos mediante consulta especializada
	@GetMapping(value = "reporteActividad",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Actividad> reporteActividad(@RequestParam String materia){
		return erActividad.getActividades(materia);
	}
	
	@GetMapping(value="reporteNotasAct",produces = MediaType.APPLICATION_JSON_VALUE) 
	@ResponseBody
	public List<Nota> NotasAct(@RequestParam Integer id_actividad,@RequestParam Integer id_grupoestudiante){
		return erNota.getNotaActividad(id_actividad,id_grupoestudiante);
	}
	
	
	
	//listar estudiantes segun grupo
	
	
	//datos para cargar estudiantes
	@GetMapping(value = "reporteEstudiante",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllEstudiantes(@RequestParam String materia,@RequestParam String grupo,@RequestParam Integer actividad){
		System.out.println(actividad);
		//Se crea una lista de hashmap para asignar un seudonimo
		List<HashMap<String,Object>> objetos = new ArrayList<>();
		
		List<Nota> nt = (List<Nota>) erNota.findAll();
		List<Grupoestudiante> ge = erGrupoEstudiante.findByGrupoMateriaNombreAndGrupoGrupo(materia, grupo);
		
		for (Grupoestudiante grupoestudiante : ge) {
			HashMap<String,Object> hm = new HashMap<>();
			
			hm.put("id_estudiante", grupoestudiante.getId_grupoestudiante());
			hm.put("nombre", grupoestudiante.getEstudiante().getNombre());
			hm.put("apellido", grupoestudiante.getEstudiante().getApellido());
			hm.put("nota", "<input class='form-control nota' value='' readonly>"+"<input class='idNota' type='hidden' readonly>");
			hm.put("observacion","<input class='form-control observacion' value='' readonly>");
			for (Nota nota : nt) {
				
				if (nota.getGrupoestudiante().getId_grupoestudiante() == grupoestudiante.getId_grupoestudiante()&nota.getActividad().getId_actividad()==actividad) {
					hm.put("nota", "<input class='form-control nota' value='"+nota.getNota()+"' readonly>"+""+"<input class='idNota' value='"+nota.getId_nota()+"' type='hidden'>");
					hm.put("observacion","<input class='form-control observacion' value='"+nota.getObservaciones().toString()+"' readonly>");
				}
				hm.put("opcion", "<button type='button' class='btn btn-primary btn-sm mr-2 CargarNota'><i class='fa fa-save'></i></button>" + 
						"<button type='button' class='btn btn-danger btn-sm btn BorrarNota'><i class='fa fa-edit'></i></button>");	
			}
			
			objetos.add(hm);
		}
		
		return Collections.singletonMap("data",objetos);
	}
	
	
	
	//metodo para listar actividades
		@GetMapping(value = "allActividad", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public List<Actividad> getActividades() {
			return (List<Actividad>) daoNota.getAllActividades();
		}
		
		//metodo para listar grupoestudiantes
		@GetMapping(value = "allgetGrupoestudiante", produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public List<Grupoestudiante> getActividad() {
			return (List<Grupoestudiante>) daoNota.getAllgetGrupoestudiante();
		}

		//metodo para traer los datos de entidad
		@GetMapping(value = "allNotas",produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getAllNotas(){
			//Se crea una lista de hashmap para asignar un seudonimo
			List<HashMap<String,Object>> objetos = new ArrayList<>();
			
			//Se crea una lista para inicializar los datos
			List<Nota> n=daoNota.getAllNotas();
			
			//se genera un for para recorrer la lista
			for (Nota nota : n) {
				HashMap<String,Object> hm = new HashMap<>();
				hm.put("id_nota",nota.getId_nota().toString());
				hm.put("id_grupoestudiante", nota.getGrupoestudiante().getEstudiante().getNombre().toString());
				hm.put("id_actividad",nota.getActividad().getNombre());
				hm.put("nota", nota.getNota().toString());
				hm.put("observaciones", nota.getObservaciones().toString());
				hm.put("opciones","<button type='button' class='btn btn-primary mr-2 ModNota' data-toggle='modal' data-target='#ModalMod'><i class='fa fa-pen-nib fa-1x mr-1'></i>Editar</button>"
								+ "<button type='button' class='btn btn-danger dropNota' data-toggle='modal' data-target='#ModalDropNota'><i class='fa fa-trash-alt fa-1x mr-1'></i>Eliminar</button>");			
				objetos.add(hm);
			}
			return Collections.singletonMap("data",objetos);
		}
		
		//metodo para editar
				@GetMapping(value = "getNota/{id_nota}",produces = MediaType.APPLICATION_JSON_VALUE)
				@ResponseBody
				public Nota modificar(@PathVariable Integer id_nota){
					return daoNota.getNota(id_nota);
				}
				
				//envio de datos para guardar
				@GetMapping(value="guardar",produces = MediaType.APPLICATION_JSON_VALUE)
				@ResponseBody
				@CrossOrigin
				public HashMap<String,String> guardar(
						@RequestParam Integer id_actividad, 
						@RequestParam Integer id_grupoestudiante,
						@RequestParam Double  nota,
						@RequestParam String  observaciones,
						@RequestParam Integer id_nota) {

					HashMap<String,String> jsonReturn = new HashMap<>();
					
					Nota entity = new Nota();
					
					//seteamos los campos a sobreescribir
					if(id_nota!=null)
						entity.setId_nota(id_nota);
						entity.setActividad(daoNota.getActividad(id_actividad));
						entity.setGrupoestudiante(daoNota.getGrupoestudiante(id_grupoestudiante));
						entity.setNota(nota);
						entity.setObservaciones(observaciones);
					try {
						daoNota.SaveorUpdate(entity);
						
						jsonReturn.put("estado","OK");
						jsonReturn.put("mensaje","Registro Guardado");
						return jsonReturn;	
					}catch(Exception e) {
						jsonReturn.put("estado","ERROR");
						jsonReturn.put("mensaje","Registro no Guardado"+e.getMessage());
						return jsonReturn;	
					}
				}
				
				//envio de datos a modificar
				@GetMapping("modificar/{id_nota}")
				@ResponseBody
				public HashMap<String,String> modificar(
						@RequestParam Integer id_actividad,
						@RequestParam Integer id_grupoestudiante,
						@RequestParam Integer id_nota,
						@RequestParam Double nota,
						@RequestParam String observaciones) {
					
					HashMap<String,String> jsonReturn = new HashMap<>();
					
					Nota entity = new Nota();
						entity.setActividad(daoNota.getActividad(id_actividad));
						entity.setGrupoestudiante(daoNota.getGrupoestudiante(id_grupoestudiante));
						entity.setId_nota(id_nota);
						entity.setNota(nota);
						entity.setObservaciones(observaciones);
					try {
						daoNota.SaveorUpdate(entity);
						
						jsonReturn.put("estado", "OK");
						jsonReturn.put("mensaje", "Registro Actualizado");
						
						return jsonReturn;
					}catch(Exception e) {
						jsonReturn.put("estado", "ERROR");
						jsonReturn.put("mensaje", "Registro no Actualizado");
						
						return jsonReturn;
					}
				}
				
				//metodo eliminar
				@GetMapping("eliminar/{id_nota}")
				@ResponseBody
				@CrossOrigin
				public HashMap<String,String> eliminar(@PathVariable Integer id_nota) {
					HashMap<String,String> jsonReturn = new HashMap<>();
					try {
						Nota entity = daoNota.getNota(id_nota);
						daoNota.delete(entity);
						
						jsonReturn.put("estado", "OK");
						jsonReturn.put("mensaje", "Registro Eliminado");
						
						return jsonReturn;
					}catch(Exception e) {
						jsonReturn.put("estado", "ERROR");
						jsonReturn.put("mensaje", "Registro no Eliminado");
						
						return jsonReturn;
					}
				}
}
