package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the EMPREGADO database table.
 * 
 */
@Entity
@NamedQuery(name="Empregado.findAll", query="SELECT e FROM Empregado e")
public class Empregado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idempregado;

	private String cargo;

	@Temporal(TemporalType.DATE)
	private Date dataadmissao;

	@Temporal(TemporalType.DATE)
	private Date datenascimento;

	private int nif;

	private String nome;

	private int telemovel;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="IDUSER")
	private User user;

	//bi-directional many-to-one association to Fatura
	@OneToMany(mappedBy="empregado")
	private List<Fatura> faturas;

	//bi-directional many-to-one association to Produto
	@OneToMany(mappedBy="empregado")
	private List<Produto> produtos;

	public Empregado() {
	}

	public int getIdempregado() {
		return this.idempregado;
	}

	public void setIdempregado(int idempregado) {
		this.idempregado = idempregado;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getDataadmissao() {
		return this.dataadmissao;
	}

	public void setDataadmissao(Date dataadmissao) {
		this.dataadmissao = dataadmissao;
	}

	public Date getDatenascimento() {
		return this.datenascimento;
	}

	public void setDatenascimento(Date datenascimento) {
		this.datenascimento = datenascimento;
	}

	public int getNif() {
		return this.nif;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTelemovel() {
		return this.telemovel;
	}

	public void setTelemovel(int telemovel) {
		this.telemovel = telemovel;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Fatura> getFaturas() {
		return this.faturas;
	}

	public void setFaturas(List<Fatura> faturas) {
		this.faturas = faturas;
	}

	public Fatura addFatura(Fatura fatura) {
		getFaturas().add(fatura);
		fatura.setEmpregado(this);

		return fatura;
	}

	public Fatura removeFatura(Fatura fatura) {
		getFaturas().remove(fatura);
		fatura.setEmpregado(null);

		return fatura;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto addProduto(Produto produto) {
		getProdutos().add(produto);
		produto.setEmpregado(this);

		return produto;
	}

	public Produto removeProduto(Produto produto) {
		getProdutos().remove(produto);
		produto.setEmpregado(null);

		return produto;
	}

}