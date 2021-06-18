package com.cds.proyecto.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cds.proyecto.entidades.Estudiante;
import com.cds.proyecto.entidades.Institucion;
import com.cds.proyecto.repositorios.IEstudianteRepository;
import com.cds.proyecto.repositorios.IInstitucionRepository;

@Service
public class EstudianteService {

	@Autowired
	IInstitucionRepository erInstitucion;

	@Autowired
	IEstudianteRepository erEstudiante;

	// listado de estudiantes
	@Transactional
	public List<Estudiante> getAllEstudiantes() {
		return (List<Estudiante>) erEstudiante.findAll();
	}

	// Obtener el id de la entidad
	@Transactional
	public Estudiante getEstudiante(Integer id_estudiante) {
		return erEstudiante.findById(id_estudiante).get();
	}

	// metodo para guardar los estudiantes
	@Transactional
	public Boolean SaveorUpdate(Estudiante entity) {
		try {
			erEstudiante.save(entity);
		return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	//metodo para eliminar
	@Transactional
	public Boolean delete(Estudiante entity) {
		try {
			erEstudiante.delete(entity);
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	// listado de entidad
	@Transactional
	public List<Institucion> getAllInstituciones() {
		return (List<Institucion>) erInstitucion.findAll();
	}

	// Obtener el id de la entidad
	@Transactional
	public Institucion getInstitucion(Integer id_institucion) {
		return erInstitucion.findById(id_institucion).get();
	}
}
