package com.cds.proyecto.service;

import java.util.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cds.proyecto.repositorios.IActividadRepository;
import com.cds.proyecto.repositorios.IMateriaRepository;
import com.cds.proyecto.entidades.Materia;

//Actividad service
@Service
public class ActividadService {

	@Autowired
	IMateriaRepository rMaterias;
	
	@Autowired
	IActividadRepository rActividad;
	
	@Transactional
	public List<Materia> getAllMaterias(){
		return ( List<Materia>) rMaterias.findAll();	
	}
	
	@Transactional
	public Materia getMateria(Integer id_materia) {
		return rMaterias.findById(id_materia).get();
	}
	
	//METODO PARA LA PONDERACIÓN
		@Transactional
		public Double getPonderacion(Integer id_materia) {
			
			Double poderacionTotal = 1.0 ;		
			
			//PONDERACION ACUMULADA
			Double ponderacionAcumulada = rActividad.obtenerPonderacion(id_materia);
			
			if(ponderacionAcumulada == null) {
				ponderacionAcumulada = 0.0;
			}
			
			//PONDERACION DISPONIBLE
			Double resultado = poderacionTotal - ponderacionAcumulada;
			System.out.println(resultado);
			//CANTIDAD DISPONIBLE
			return resultado;
		}
}
