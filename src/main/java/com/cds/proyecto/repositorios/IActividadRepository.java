package com.cds.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cds.proyecto.entidades.Actividad;

@Repository
public interface IActividadRepository extends CrudRepository<Actividad, Integer>{
	
	@Query(value = "select SUM(act.ponderacion) from actividades act WHERE act.id_materia = :id_materia", nativeQuery = true)
	Double obtenerPonderacion(Integer id_materia);
	
	@Query("select ac from Actividad ac where ac.materia.nombre like %:materia")
	public List<Actividad> getActividades(String materia);
}
