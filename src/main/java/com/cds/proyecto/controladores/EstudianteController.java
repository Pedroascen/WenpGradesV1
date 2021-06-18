package com.cds.proyecto.controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cds.proyecto.entidades.Estudiante;
import com.cds.proyecto.entidades.Institucion;
import com.cds.proyecto.service.EstudianteService;

@Controller
@RequestMapping("estudiantes")
public class EstudianteController {
	
	@Autowired
	EstudianteService daoEstudiante;
	
	// metodo para llamar vista
	@GetMapping("index")
	public String vista() {
		return "estudiante/indexEstu";
	}
	
	//metodo para listar
	@GetMapping(value = "allInstitucion", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Institucion> getInstitucion() {
		return (List<Institucion>) daoEstudiante.getAllInstituciones();
	}
	
	//metodo para traer los datos de entidad
	@GetMapping(value = "allEstudiantes",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getAllEstudiante(){
		//Se crea una lista de hashmap para asignar un seudonimo
		List<HashMap<String,Object>> objetos = new ArrayList<>();
		
		//Se crea una lista para inicializar los datos
		List<Estudiante> e=daoEstudiante.getAllEstudiantes();
		
		//se genera un for para recorrer la lista
		for (Estudiante estudiante : e) {
			HashMap<String,Object> hm = new HashMap<>();
			hm.put("id_estudiante",estudiante.getId_estudiante().toString());
			hm.put("institucion", estudiante.getInstitucion().getNombre());
			hm.put("nombre", estudiante.getNombre().toString());
			hm.put("apellido", estudiante.getApellido().toString());
			hm.put("email",estudiante.getEmail().toString());
			hm.put("telefono", estudiante.getTelefono().toString());
			hm.put("opciones","<button type='button' class='btn btn-primary mr-2 ModEstu' data-toggle='modal' data-target='#ModalMod'><i class='fa fa-pen fa-fw mr-2'></i>Editar</button>"
							+ "<button type='button' class='btn btn-danger dropEstu' data-toggle='modal' data-target='#ModalDropEstu'><i class='fa fa-trash-alt fa-1x mr-1'></i>Eliminar</button>");			
			objetos.add(hm);
		}
		return Collections.singletonMap("data",objetos);
	}
	
		//vista de guardar
		@GetMapping(value = "getEstudiante/{id_estudiante}",produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Estudiante modificar(@PathVariable Integer id_estudiante){
			return daoEstudiante.getEstudiante(id_estudiante);
		}
		
		//envio de datos para guardar
		@GetMapping(value="save",produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		@CrossOrigin
		public HashMap<String,String> guardar(
				@RequestParam Integer id_institucion, 
				@RequestParam String nombre,
				@RequestParam String apellido,
				@RequestParam String telefono,
				@RequestParam String email) {

			HashMap<String,String> jsonReturn = new HashMap<>();
			
			Estudiante entity = new Estudiante();
			
			//seteamos los campos a sobreescribir
				entity.setInstitucion(daoEstudiante.getInstitucion(id_institucion));
				entity.setNombre(nombre);
				entity.setApellido(apellido);
				entity.setTelefono(telefono);
				entity.setEmail(email);
			try {
				daoEstudiante.SaveorUpdate(entity);
				
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
		@GetMapping("modificar/{id_estudiante}")
		@ResponseBody
		public HashMap<String,String> modificar(
				@RequestParam Integer id_institucion,
				@RequestParam Integer id_estudiante,
				@RequestParam String nombre,
				@RequestParam String apellido,
				@RequestParam String telefono,
				@RequestParam String email) {
			
			HashMap<String,String> jsonReturn = new HashMap<>();
			
			Estudiante entity = new Estudiante();
				entity.setInstitucion(daoEstudiante.getInstitucion(id_institucion));
				entity.setId_estudiante(id_estudiante);
				entity.setNombre(nombre);
				entity.setApellido(apellido);
				entity.setTelefono(telefono);
				entity.setEmail(email);
			try {
				daoEstudiante.SaveorUpdate(entity);
				
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
		@GetMapping("eliminar/{id_estudiante}")
		@ResponseBody
		@CrossOrigin
		public HashMap<String,String> eliminar(@PathVariable Integer id_estudiante) {
			HashMap<String,String> jsonReturn = new HashMap<>();
			try {
				Estudiante entity = daoEstudiante.getEstudiante(id_estudiante);
				daoEstudiante.delete(entity);
				
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
