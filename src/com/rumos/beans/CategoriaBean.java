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
import javax.inject.Inject;
import javax.inject.Named;

import com.rumos.dao.CategoriaDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.exceptions.PreexistingUserException;
import com.rumos.model.Categoria;
import com.rumos.model.Subcategoria;

@Named("categoria")
@SessionScoped
public class CategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CategoriaDAO categoriaDao;

	private String descricaoCategoria;
	private String descricaoSubCategoria;
	private String categoriaSubCategoria;
	public static final String CONST_SEM_SUBCATEGORIA="Sem subcategoria";

	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}

	public String getDescricaoSubCategoria() {
		return descricaoSubCategoria;
	}

	public void setDescricaoSubCategoria(String descricaoSubCategoria) {
		this.descricaoSubCategoria = descricaoSubCategoria;
	}

	public String getCategoriaSubCategoria() {
		return categoriaSubCategoria;
	}

	public void setCategoriaSubCategoria(String categoriaSubCategoria) {
		this.categoriaSubCategoria = categoriaSubCategoria;
	}

	public String addCategoria() throws PreexistingUserException, Exception {

		Categoria x = null;

		try {
			x = categoriaDao.retrieveCategoriaByDesc(descricaoCategoria);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("A categoria "
					+ x.getDescricao() + " já existe!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(null, facesMessage);
			return null;

		} catch (NoResultFindException e) {
			categoriaDao.novaCategoria(descricaoCategoria);
			categoriaDao.novaSubCategoria(CONST_SEM_SUBCATEGORIA, categoriaDao.retrieveCategoriaByDesc(descricaoCategoria));
		}

		return "index";

	}
	
	public String addSubCategoria() throws PreexistingUserException, Exception {

		Subcategoria x = null;

		try {
			x = categoriaDao.retrieveSubCategoriaByDesc(descricaoSubCategoria, categoriaSubCategoria);
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("A subcategoria "
					+ x.getDescricao() + " já existe!");
			facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			facesContext.addMessage(null, facesMessage);
			return null;

		} catch (NoResultFindException e) {
			categoriaDao.novaSubCategoria(descricaoSubCategoria, categoriaDao.retrieveCategoriaByDesc(categoriaSubCategoria));
		}

		return "index";

	}
	
	public List<String> getCategoriaList() {
		
		List<Categoria> categorialist;
		try {
			categorialist = categoriaDao.retrieveAllCategorias();
		} catch (NoResultFindException e1) {
			return null;
		}
		List<String> returnList = new ArrayList<String>();
		
		Iterator<Categoria> e = categorialist.iterator();
        while (e.hasNext()) {
        	Categoria categoriaX = (Categoria) e.next();
        	returnList.add(categoriaX.getDescricao());
        }		
			
		return returnList;
	}
}
