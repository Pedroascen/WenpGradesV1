package com.cds.proyecto.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cds.proyecto.entidades.Nota;

@Repository
public interface INotaRepository extends CrudRepository<Nota, Integer>{
//ae.actividad.nombre like %:actividad and
	@Query("select ae, ge from Nota ae "
			+ "INNER JOIN Grupoestudiante ge "
			+ "ON ae.grupoestudiante.id_grupoestudiante = ge.id_grupoestudiante "
			+ "where ae.actividad.materia.nombre like %:materia and ae.grupoestudiante.grupo.grupo like %:grupo")
	public List<Nota> getNotasEstudiantesActividades(String materia, String grupo);
	
	
	@Query("select ae, ge from Grupoestudiante ge "
			+ "LEFT OUTER JOIN Nota ae "
			+ "ON ge.id_grupoestudiante = ae.grupoestudiante.id_grupoestudiante ")
	public List<Nota> getNotasEstudiantes();
	
	@Query("select ne from Nota ne where ne.actividad.id_actividad = :id_actividad and ne.grupoestudiante.id_grupoestudiante = :id_grupoestudiante")
	public List<Nota> getNotaActividad(Integer id_actividad, Integer id_grupoestudiante);
	
	
	@Query("select ne from Nota ne where ne.actividad.id_actividad = :id_actividad and ne.grupoestudiante.id_grupoestudiante = :id_grupoestudiante")
	public List<Nota> getNotaActividadMateria(Integer id_actividad, Integer id_grupoestudiante);
	
}
