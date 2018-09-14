package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SUBCATEGORIA database table.
 * 
 */
@Entity
@NamedQuery(name="Subcategoria.findAll", query="SELECT s FROM Subcategoria s")
public class Subcategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idsubcategoria;

	private String descricao;

	//bi-directional many-to-one association to Produto
	@OneToMany(mappedBy="subcategoria")
	private List<Produto> produtos;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="IDCATEGORIA")
	private Categoria categoria;

	public Subcategoria() {
	}

	public int getIdsubcategoria() {
		return this.idsubcategoria;
	}

	public void setIdsubcategoria(int idsubcategoria) {
		this.idsubcategoria = idsubcategoria;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto addProduto(Produto produto) {
		getProdutos().add(produto);
		produto.setSubcategoria(this);

		return produto;
	}

	public Produto removeProduto(Produto produto) {
		getProdutos().remove(produto);
		produto.setSubcategoria(null);

		return produto;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}