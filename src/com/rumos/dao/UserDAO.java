package com.rumos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.UserException;
import com.rumos.model.User;

@Stateless
public class UserDAO {

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public void novoUser(String username, String password, String role) throws UserException {

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);

		try {
			em.persist(user);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new UserException("Item with id " + user.getUsername() + " exists.");
		}

	}
	
	public User retrieveUserByName(String userName){
		StringBuilder queryText = new StringBuilder("SELECT user FROM User as user where user.username='");
		queryText.append(userName);
		queryText.append("'");
        TypedQuery<User> query = em.createQuery(queryText.toString(), User.class);
        User singleItem = query.getSingleResult();
        return singleItem;
	}
	
    public List<User> getAllUsers() {
        StringBuilder queryText = new StringBuilder("SELECT user FROM User as user ORDER BY user.username");
        TypedQuery<User> query = em.createQuery(queryText.toString(), User.class);
        List<User> allItems = query.getResultList();
        return allItems;
    }

}
