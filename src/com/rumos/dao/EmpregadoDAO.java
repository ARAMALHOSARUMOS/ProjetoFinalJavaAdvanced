package com.rumos.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.EmpregadoException;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Empregado;
import com.rumos.model.Produto;
import com.rumos.model.User;

@Stateless
public class EmpregadoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public void novoEmpregado(String nome, Date dataAdmissao,
			Date dataNascimento, int telemovel, int nif, String cargo, User user)
			throws EmpregadoException {

		Empregado empregado = new Empregado();
		empregado.setNome(nome);
		empregado.setDataadmissao(dataAdmissao);
		empregado.setDatenascimento(dataNascimento);
		empregado.setTelemovel(telemovel);
		empregado.setNif(nif);
		empregado.setCargo(cargo);
		empregado.setUser(user);

		try {
			em.persist(empregado);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new EmpregadoException("Item with id " + empregado.getNome()
					+ " exists.");
		}

	}

	public Empregado retrieveEmpregadoByNome(String nome)
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT emp FROM Empregado as emp where emp.nome='");
		queryText.append(nome);
		queryText.append("'");
		TypedQuery<Empregado> query = em.createQuery(queryText.toString(),
				Empregado.class);
		Empregado singleItem = null;
		try {
			singleItem = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return singleItem;
	}
	
	public Empregado retrieveEmpregadoByUserName(String nome)
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT emp FROM Empregado as emp where emp.user.username='");
		queryText.append(nome);
		queryText.append("'");
		TypedQuery<Empregado> query = em.createQuery(queryText.toString(),
				Empregado.class);
		Empregado singleItem = null;
		try {
			singleItem = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return singleItem;
	}

	public List<Empregado> retrieveAllEmpregados() throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT empregado FROM Empregado as empregado ORDER BY empregado.nome");
		TypedQuery<Empregado> query = em.createQuery(queryText.toString(),
				Empregado.class);
		List<Empregado> allItems = null;
		try {
			allItems = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}

		return allItems;
	}

}
