package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the FATURA database table.
 * 
 */
@Entity
@NamedQuery(name="Fatura.findAll", query="SELECT f FROM Fatura f")
public class Fatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idfatura;

	private Timestamp datahora;

	//bi-directional many-to-one association to Empregado
	@ManyToOne
	@JoinColumn(name="IDEMPREGADO")
	private Empregado empregado;

	//bi-directional many-to-one association to Linhasdefatura
	@OneToMany(mappedBy="fatura")
	private List<Linhasdefatura> linhasdefaturas;

	public Fatura() {
	}

	public int getIdfatura() {
		return this.idfatura;
	}

	public void setIdfatura(int idfatura) {
		this.idfatura = idfatura;
	}

	public Timestamp getDatahora() {
		return this.datahora;
	}

	public void setDatahora(Timestamp datahora) {
		this.datahora = datahora;
	}

	public Empregado getEmpregado() {
		return this.empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public List<Linhasdefatura> getLinhasdefaturas() {
		return this.linhasdefaturas;
	}

	public void setLinhasdefaturas(List<Linhasdefatura> linhasdefaturas) {
		this.linhasdefaturas = linhasdefaturas;
	}

	public Linhasdefatura addLinhasdefatura(Linhasdefatura linhasdefatura) {
		getLinhasdefaturas().add(linhasdefatura);
		linhasdefatura.setFatura(this);

		return linhasdefatura;
	}

	public Linhasdefatura removeLinhasdefatura(Linhasdefatura linhasdefatura) {
		getLinhasdefaturas().remove(linhasdefatura);
		linhasdefatura.setFatura(null);

		return linhasdefatura;
	}

}