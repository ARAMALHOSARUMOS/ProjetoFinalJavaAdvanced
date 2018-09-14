package com.rumos.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.CategoriaException;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Categoria;
import com.rumos.model.CategoriaSubCategoria;
import com.rumos.model.Subcategoria;

@Stateless
public class CategoriaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public void novaCategoria(String descricao) throws CategoriaException {

		Categoria categoria = new Categoria();
		categoria.setDescricao(descricao);

		try {
			em.persist(categoria);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new CategoriaException("Item with id "
					+ categoria.getDescricao() + " exists.");
		}

	}

	public void novaSubCategoria(String descricao, Categoria categoria)
			throws CategoriaException {

		Subcategoria subcategoria = new Subcategoria();
		subcategoria.setDescricao(descricao);
		subcategoria.setCategoria(categoria);

		try {
			em.persist(subcategoria);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new CategoriaException("Item with id "
					+ subcategoria.getDescricao() + " exists.");
		}

	}

	public Categoria retrieveCategoriaByDesc(String desc)
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT cat FROM Categoria as cat where cat.descricao='");
		queryText.append(desc);
		queryText.append("'");
		TypedQuery<Categoria> query = em.createQuery(queryText.toString(),
				Categoria.class);
		Categoria singleItem = null;
		try {
			singleItem = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return singleItem;
	}

	public Subcategoria retrieveSubCategoriaByDesc(String desc,
			String CategoriaDesc) throws NoResultFindException {

		Subcategoria singleItem = new Subcategoria();
		singleItem.setDescricao("");
		StringBuilder queryText = new StringBuilder(
				"SELECT sub FROM Subcategoria as sub where sub.descricao='");
		queryText.append(desc);
		queryText.append("'");
		TypedQuery<Subcategoria> query = em.createQuery(queryText.toString(),
				Subcategoria.class);
		List<Subcategoria> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}

		Iterator<Subcategoria> e = allItem.iterator();
		while (e.hasNext()) {
			Subcategoria subX = (Subcategoria) e.next();
			if (subX.getCategoria().getDescricao().equals(CategoriaDesc)) {
				singleItem = subX;
			}
		}

		if (singleItem.getDescricao().isEmpty()) {
			throw new NoResultFindException();
		}

		return singleItem;
	}

	public List<Categoria> retrieveAllCategorias() throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT cat FROM Categoria as cat");
		TypedQuery<Categoria> query = em.createQuery(queryText.toString(),
				Categoria.class);
		List<Categoria> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return allItem;
	}

	public List<Subcategoria> retrieveAllSubCategorias(String CategoriaDesc)
			throws NoResultFindException {

		List<Subcategoria> returnItem = new ArrayList<Subcategoria>();
		StringBuilder queryText = new StringBuilder(
				"SELECT sub FROM Subcategoria as sub");
		TypedQuery<Subcategoria> query = em.createQuery(queryText.toString(),
				Subcategoria.class);
		List<Subcategoria> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}

		Iterator<Subcategoria> e = allItem.iterator();
		while (e.hasNext()) {
			Subcategoria subX = (Subcategoria) e.next();
			if (subX.getCategoria().getDescricao().equals(CategoriaDesc)) {
				returnItem.add(subX);
			}
		}
		
		return returnItem;
	}

	public List<CategoriaSubCategoria> retrieveAllCategoriasSubCategorias()
			throws NoResultFindException {

		List<CategoriaSubCategoria> returnItem = new ArrayList<CategoriaSubCategoria>();
		
		StringBuilder queryText = new StringBuilder(
				"SELECT cat FROM Categoria cat");
		TypedQuery<Categoria> query = em.createQuery(queryText.toString(),
				Categoria.class);
		List<Categoria> allItems = null;
		try {
			allItems = query.getResultList();
			
			Iterator<Categoria> e = allItems.iterator();
			while (e.hasNext()) {
				Categoria catX = (Categoria) e.next();

				StringBuilder queryText1 = new StringBuilder(
						"SELECT sub FROM Subcategoria sub where sub.categoria.idcategoria=");
				queryText1.append(catX.getIdcategoria());
				TypedQuery<Subcategoria> query1 = em.createQuery(queryText1.toString(),
						Subcategoria.class);
				
				List<Subcategoria> allItems1 = null;
				allItems1 = query1.getResultList();
				if (allItems1.isEmpty()){
					CategoriaSubCategoria subX = new CategoriaSubCategoria();
					subX.setCategoria(catX);
					returnItem.add(subX);
				} else {
					Iterator<Subcategoria> e1 = allItems1.iterator();
					while (e1.hasNext()) {
						Subcategoria empX = (Subcategoria) e1.next();
						CategoriaSubCategoria subX = new CategoriaSubCategoria();
						subX.setCategoria(catX);
						subX.setSubcategoria(empX);
						returnItem.add(subX);
					}
				}
				
			}
			
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		
		return returnItem;
	}
	
	
}
