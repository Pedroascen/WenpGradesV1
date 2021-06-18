package com.cds.proyecto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.entidades.Actividad;
import com.cds.proyecto.entidades.Grupoestudiante;
import com.cds.proyecto.entidades.Nota;
import com.cds.proyecto.repositorios.IGrupoestudianteRepository;
import com.cds.proyecto.repositorios.INotaRepository;

@Service
public class NotaService {
	@Autowired
	INotaRepository erNota;
	
	@Autowired
	IActividadRepository erActividad;
	
	@Autowired
	IGrupoestudianteRepository erGrupoestudiante;
	
	//listado de notas
	@Transactional
	public List<Nota> getAllNotas(){
		return (List<Nota>) erNota.findAll();
	}
	//Obtener el id de la entidad
	@Transactional
	public Nota getNota(Integer id_nota) {
		return erNota.findById(id_nota).get();
	}
	//metodo para guardar las notas
	@Transactional
	public Boolean SaveorUpdate(Nota entity) {
		//try {
			erNota.save(entity);
		/*return true;
		}catch(Exception e) {
			return false;
		}*/
			return true;
	}
	//metodo para eliminar
		@Transactional
		public Boolean delete(Nota entity) {
			try {
				erNota.delete(entity);
				return true;
			}catch(Exception e) {
				return false;
			}
		}
		// listado de entidad
		@Transactional
		public List<Actividad> getAllActividades() {
			return (List<Actividad>) erActividad.findAll();
		}
		@Transactional
		public List<Grupoestudiante> getAllgetGrupoestudiante() {
			return (List<Grupoestudiante>) erGrupoestudiante.findAll();
		}
		// Obtener el id de la entidad
		@Transactional
		public Actividad getActividad(Integer id_actividad) {
			return erActividad.findById(id_actividad).get();
		}
		@Transactional
		public Grupoestudiante getGrupoestudiante(Integer id_grupoestudiante) {
			return erGrupoestudiante.findById(id_grupoestudiante).get();
		}

}
