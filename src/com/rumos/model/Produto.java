package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PRODUTO database table.
 * 
 */
@Entity
@NamedQuery(name="Produto.findAll", query="SELECT p FROM Produto p")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idproduto;

	private String descricao;

	private String nome;

	private int quantidade;

	private int valor;

	//bi-directional many-to-one association to Linhasdefatura
	@OneToMany(mappedBy="produto")
	private List<Linhasdefatura> linhasdefaturas;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="IDCATEGORIA")
	private Categoria categoria;

	//bi-directional many-to-one association to Empregado
	@ManyToOne
	@JoinColumn(name="IDEMPREGADOCRIACAO")
	private Empregado empregado;

	//bi-directional many-to-one association to Subcategoria
	@ManyToOne
	@JoinColumn(name="IDSUBCATEGORIA")
	private Subcategoria subcategoria;

	public Produto() {
	}

	public int getIdproduto() {
		return this.idproduto;
	}

	public void setIdproduto(int idproduto) {
		this.idproduto = idproduto;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public List<Linhasdefatura> getLinhasdefaturas() {
		return this.linhasdefaturas;
	}

	public void setLinhasdefaturas(List<Linhasdefatura> linhasdefaturas) {
		this.linhasdefaturas = linhasdefaturas;
	}

	public Linhasdefatura addLinhasdefatura(Linhasdefatura linhasdefatura) {
		getLinhasdefaturas().add(linhasdefatura);
		linhasdefatura.setProduto(this);

		return linhasdefatura;
	}

	public Linhasdefatura removeLinhasdefatura(Linhasdefatura linhasdefatura) {
		getLinhasdefaturas().remove(linhasdefatura);
		linhasdefatura.setProduto(null);

		return linhasdefatura;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Empregado getEmpregado() {
		return this.empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public Subcategoria getSubcategoria() {
		return this.subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

}