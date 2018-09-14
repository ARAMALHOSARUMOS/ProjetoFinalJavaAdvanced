package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LINHASDEFATURA database table.
 * 
 */
@Entity
@NamedQuery(name="Linhasdefatura.findAll", query="SELECT l FROM Linhasdefatura l")
public class Linhasdefatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idlinhafatura;

	private int quantidade;

	private int valor;

	//bi-directional many-to-one association to Fatura
	@ManyToOne
	@JoinColumn(name="IDFATURA")
	private Fatura fatura;

	//bi-directional many-to-one association to Produto
	@ManyToOne
	@JoinColumn(name="IDPRODUTO")
	private Produto produto;

	public Linhasdefatura() {
	}

	public int getIdlinhafatura() {
		return this.idlinhafatura;
	}

	public void setIdlinhafatura(int idlinhafatura) {
		this.idlinhafatura = idlinhafatura;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Fatura getFatura() {
		return this.fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}