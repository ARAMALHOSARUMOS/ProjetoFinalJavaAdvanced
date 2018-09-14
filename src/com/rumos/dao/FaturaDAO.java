package com.rumos.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.CategoriaException;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Fatura;
import com.rumos.model.Linhasdefatura;

@Stateless
public class FaturaDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public void novaFatura(Fatura fat) throws Exception {

		try {
			em.persist(fat);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new CategoriaException("Item with id " + fat.getIdfatura()
					+ " exists.");
		}

	}

	public void novaLinhaFatura(Linhasdefatura lfat) throws Exception {

		try {
			em.persist(lfat);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new CategoriaException("Item with id "
					+ lfat.getIdlinhafatura() + " exists.");
		}

	}

	public List<Linhasdefatura> retrieveLinhasFatura(Fatura fat)
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT lfat FROM Linhasdefatura as lfat where lfat.fatura.idfatura='");
		queryText.append(Integer.valueOf(fat.getIdfatura()));
		queryText.append("'");
		TypedQuery<Linhasdefatura> query = em.createQuery(queryText.toString(),
				Linhasdefatura.class);
		List<Linhasdefatura> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return allItem;
	}

	public List<Linhasdefatura> retrieveAllLinhasFatura()
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT lfat FROM Linhasdefatura as lfat order by lfat.fatura.datahora desc");
		TypedQuery<Linhasdefatura> query = em.createQuery(queryText.toString(),
				Linhasdefatura.class);
		List<Linhasdefatura> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return allItem;
	}

}
