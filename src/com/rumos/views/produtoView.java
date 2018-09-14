package com.rumos.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.rumos.beans.LoginBean;
import com.rumos.dao.ProdutoDAO;
import com.rumos.exceptions.EntityNotFoundException;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Produto;

@ManagedBean(name = "dtProdutoView")
@ViewScoped
public class produtoView implements Serializable {

	private static final long serialVersionUID = 1L;

	// private LazyDataModel<Produto> lazyModel;

	private List<Produto> lazyModel;

	private Produto selectedProduto;

	private boolean empregadoProduto;

	private List<Produto> filteredProdutos;

	@Inject
	ProdutoDAO produtoDao;

	@Inject
	LoginBean loginBean;

	@PostConstruct
	public void init() {
		// try {
		// lazyModel = new LazyProdutoDataModel(
		// produtoDao.retrieveAllProdutos());
		// } catch (NoResultFindException e) {
		// lazyModel = new LazyProdutoDataModel(null);
		// }
	}

	// public LazyDataModel<Produto> getLazyModel() {
	// return lazyModel;
	// }

	public List<Produto> getLazyModel() {
		
		try {
			lazyModel = produtoDao.retrieveAllProdutos();
		} catch (NoResultFindException e) {
			lazyModel = new ArrayList<Produto>();
		}

		return lazyModel;
	}

	public Produto getSelectedProduto() {
		return selectedProduto;
	}

	public void setSelectedProduto(Produto selectedProduto) {
		this.selectedProduto = selectedProduto;
	}

	public List<Produto> getFilteredProdutos() {
		return filteredProdutos;
	}

	public void setFilteredProdutos(List<Produto> filteredProdutos) {
		this.filteredProdutos = filteredProdutos;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Produto Selected",
				((Produto) event.getObject()).getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void alterarProduto() {

		try {
			produtoDao.alterarProduto(selectedProduto);
		} catch (EntityNotFoundException e) {

			System.out.println("Problemas ao atualizar o produto: ");
			e.printStackTrace();
		}

	}

	public boolean isEmpregadoProduto() {

		if (selectedProduto.getEmpregado().getUser().getUsername()
				.equals(loginBean.getUsername())) {
			return true;
		} else {
			return false;
		}

	}
}
