
package com.cds.proyecto.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "notas")
public class Nota {

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_nota;
	
    private String fecha;
    private String nota;
    private String observaciones;
    
    @JoinColumn(name="id_grupoestudiante",referencedColumnName = "id_grupoestudiante",nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Grupoestudiante grupoestudiante;
    
    public Nota() {
		// TODO Auto-generated constructor stub
	}

	public Nota(Integer id_nota, String fecha, String nota, String observaciones) {
		super();
		this.id_nota = id_nota;
		this.fecha = fecha;
		this.nota = nota;
		this.observaciones = observaciones;
	}

	public Integer getId_nota() {
		return id_nota;
	}

	public void setId_nota(Integer id_nota) {
		this.id_nota = id_nota;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Grupoestudiante getGrupoestudiante() {
		return grupoestudiante;
	}

	public void setGrupoestudiante(Grupoestudiante grupoestudiante) {
		this.grupoestudiante = grupoestudiante;
	}
    
}
