package com.rumos.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Empregado;
import com.rumos.model.User;
import com.rumos.model.UserEmpregado;

@Stateless
public class UserEmpregadoDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public List<UserEmpregado> retrieveAllUserEmpregado() throws NoResultFindException{
		
		List<UserEmpregado> result = new ArrayList<UserEmpregado>();
		
		StringBuilder queryText = new StringBuilder(
				"SELECT user FROM User user");
		TypedQuery<User> query = em.createQuery(queryText.toString(),
				User.class);
		List<User> allItems = null;
		try {
			allItems = query.getResultList();
			
			Iterator<User> e = allItems.iterator();
			while (e.hasNext()) {
				User usrX = (User) e.next();

				StringBuilder queryText1 = new StringBuilder(
						"SELECT empregado FROM Empregado empregado where empregado.user.username='");
				queryText1.append(usrX.getUsername());
				queryText1.append("'");
				TypedQuery<Empregado> query1 = em.createQuery(queryText1.toString(),
						Empregado.class);
				
				List<Empregado> allItems1 = null;
				allItems1 = query1.getResultList();
				if (allItems1.isEmpty()){
					UserEmpregado ueX = new UserEmpregado();
					ueX.setUser(usrX);
					result.add(ueX);
				} else {
					Iterator<Empregado> e1 = allItems1.iterator();
					while (e1.hasNext()) {
						Empregado empX = (Empregado) e1.next();
						UserEmpregado ueX = new UserEmpregado();
						ueX.setUser(usrX);
						ueX.setEmpregado(empX);
						result.add(ueX);
					}
				}
				
			}
			
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}

		return result;
		
	}
	
}
