package com.cds.proyecto.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table (name = "actividades")
public class Actividad{
	
	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_actividad;
	
	@NotBlank(message = "campo requerido")
	private String nombre;
	
	@Nullable
	private String descripcion;
	
	@NotBlank(message = "campo requerido")
	private String ponderacion;
	@JoinColumn(name="id_nota",referencedColumnName = "id_nota",nullable = false)
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	private Nota nota;
	
	public Actividad() {
		// TODO Auto-generated constructor stub
	}

	public Actividad(Integer id_actividad, @NotBlank(message = "campo requerido") String nombre, String descripcion,
			@NotBlank(message = "campo requerido") String ponderacion) {
		super();
		this.id_actividad = id_actividad;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.ponderacion = ponderacion;
	}

	public Integer getId_actividad() {
		return id_actividad;
	}

	public void setId_actividad(Integer id_actividad) {
		this.id_actividad = id_actividad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(String ponderacion) {
		this.ponderacion = ponderacion;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	

}
