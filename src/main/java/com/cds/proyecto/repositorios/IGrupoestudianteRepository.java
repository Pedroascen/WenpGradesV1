package com.cds.proyecto.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cds.proyecto.entidades.Grupoestudiante;

@Repository
public interface IGrupoestudianteRepository extends CrudRepository<Grupoestudiante, Integer>{

}
