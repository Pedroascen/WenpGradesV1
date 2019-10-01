package com.cds.proyecto.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cds.proyecto.entidades.Estudiante;

@Repository
public interface IEstudianteRepository extends CrudRepository<Estudiante, Integer>{

}
