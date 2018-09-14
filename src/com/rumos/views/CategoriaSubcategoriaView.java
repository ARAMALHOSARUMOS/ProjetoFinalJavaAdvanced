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
import com.rumos.dao.CategoriaDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.CategoriaSubCategoria;

@ManagedBean(name = "dtCategoriaSubcategoriaView")
@ViewScoped
public class CategoriaSubcategoriaView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CategoriaSubCategoria> lazyModel;

	private CategoriaSubCategoria selectedCategoriaSubcategoria;

	private List<CategoriaSubCategoria> filteredCategoriaSubcategorias;

	@Inject
	CategoriaDAO categoriaDao;

	@Inject
	LoginBean loginBean;

	@PostConstruct
	public void init() {
	}


	public List<CategoriaSubCategoria> getLazyModel() {
		
		try {
			lazyModel = categoriaDao.retrieveAllCategoriasSubCategorias();
		} catch (NoResultFindException e) {
			lazyModel = new ArrayList<CategoriaSubCategoria>();
		}

		return lazyModel;
	}

	public CategoriaSubCategoria getSelectedCategoriaSubcategoria() {
		return selectedCategoriaSubcategoria;
	}

	public void setSelectedCategoriaSubcategoria(CategoriaSubCategoria selectedCategoriaSubcategoria) {
		this.selectedCategoriaSubcategoria = selectedCategoriaSubcategoria;
	}

	public List<CategoriaSubCategoria> getFilteredCategoriaSubcategorias() {
		return filteredCategoriaSubcategorias;
	}

	public void setFilteredCategoriaSubcategorias(List<CategoriaSubCategoria> filteredCategoriaSubcategorias) {
		this.filteredCategoriaSubcategorias = filteredCategoriaSubcategorias;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("CategoriaSubcategoria Selected",
				((CategoriaSubCategoria) event.getObject()).getCategoria().getDescricao());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
