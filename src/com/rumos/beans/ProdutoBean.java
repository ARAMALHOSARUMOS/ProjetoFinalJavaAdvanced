/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumos.beans;

/**
 *
 * @author admin
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.rumos.dao.CategoriaDAO;
import com.rumos.dao.EmpregadoDAO;
import com.rumos.dao.ProdutoDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.exceptions.PreexistingUserException;
import com.rumos.model.Categoria;
import com.rumos.model.Empregado;
import com.rumos.model.Produto;
import com.rumos.model.Subcategoria;

@Named("produto")
@SessionScoped
public class ProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ProdutoDAO produtoDao;

	@Inject
	EmpregadoDAO empregadoDao;

	@Inject
	CategoriaDAO categoriaDao;

	private String nome;
	private String descricao;
	private int valor;
	private int quantidade;
	private String empregado;
	private String categoria;
	private String subcategoria;
	private List<String> listaSubcategorias;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getEmpregado() {
		return empregado;
	}

	public void setEmpregado(String empregado) {
		this.empregado = empregado;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}

	public String addProduto() throws PreexistingUserException, Exception {

		Produto x = null;

		try {
			x = produtoDao.retrieveProdutoByNome(nome);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("O produto "
					+ x.getNome() + " já existe!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(null, facesMessage);
			return null;

		} catch (NoResultFindException e) {
			Subcategoria subCat;
			try{
				subCat = categoriaDao.retrieveSubCategoriaByDesc(subcategoria,
						categoria);
			} catch(NoResultFindException e1){
				subCat = categoriaDao.retrieveSubCategoriaByDesc(CategoriaBean.CONST_SEM_SUBCATEGORIA,
						categoria);
			}
			produtoDao.novoProduto(nome, descricao, valor, quantidade,
					empregadoDao.retrieveEmpregadoByNome(empregado),
					categoriaDao.retrieveCategoriaByDesc(categoria), subCat);
		}

		return "index";

	}

	public List<String> getEmpregadoList() {

		List<Empregado> list;

		try {
			list = empregadoDao.retrieveAllEmpregados();
		} catch (NoResultFindException e1) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();

		Iterator<Empregado> e = list.iterator();
		while (e.hasNext()) {
			Empregado x = (Empregado) e.next();
			returnList.add(x.getNome());
		}

		return returnList;
	}

	public List<String> getCategoriaList() {

		List<Categoria> list;

		try {
			list = categoriaDao.retrieveAllCategorias();
		} catch (NoResultFindException e1) {
			return null;
		}

		List<String> returnList = new ArrayList<String>();

		Iterator<Categoria> e = list.iterator();
		while (e.hasNext()) {
			Categoria x = (Categoria) e.next();
			returnList.add(x.getDescricao());
		}

		return returnList;
	}

	public List<String> getSubcategoriaList() {

		List<Subcategoria> list;

		try {
			list = categoriaDao.retrieveAllSubCategorias(categoria);
		} catch (NoResultFindException e1) {
			return null;
		}

		List<String> returnList = new ArrayList<String>();

		Iterator<Subcategoria> e = list.iterator();
		while (e.hasNext()) {
			Subcategoria x = (Subcategoria) e.next();
			returnList.add(x.getDescricao());
		}

		return returnList;
	}

	public List<String> getProdutoList() {

		List<Produto> produtolist;
		try {
			produtolist = produtoDao.retrieveAllProdutos();
		} catch (NoResultFindException e1) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();

		Iterator<Produto> e = produtolist.iterator();
		while (e.hasNext()) {
			Produto produtoX = (Produto) e.next();
			returnList.add(produtoX.getNome());
		}

		return returnList;
	}

	public List<String> getListaSubcategorias() {
		return listaSubcategorias;
	}

	public void changeCategoria(AjaxBehaviorEvent event) {
		listaSubcategorias = getSubcategoriaList();
		subcategoria = "";
	}
}
