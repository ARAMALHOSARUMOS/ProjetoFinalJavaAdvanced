package com.rumos.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.rumos.exceptions.EntityNotFoundException;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.exceptions.ProdutoException;
import com.rumos.model.Categoria;
import com.rumos.model.Empregado;
import com.rumos.model.Produto;
import com.rumos.model.Subcategoria;

@Stateless
public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "ProjetoJavaAdvancedRumos")
	private EntityManager em;

	public void novoProduto(String nome, String descricao, int valor, int quantidade, Empregado empregado, Categoria categoria, Subcategoria subcategoria) throws ProdutoException {

		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setValor(valor);
		produto.setQuantidade(quantidade);
		produto.setEmpregado(empregado);
		produto.setCategoria(categoria);
		produto.setSubcategoria(subcategoria);

		try {
			em.persist(produto);
			em.flush();
		} catch (EntityExistsException pe) {
			throw new ProdutoException("Item with id "
					+ produto.getDescricao() + " exists.");
		}

	}
	
	public void alterarProduto(Produto prdAlterado) throws EntityNotFoundException{
		
		Produto produtoActualizar = findProduto(Produto.class, prdAlterado.getIdproduto());
		
		produtoActualizar.setDescricao(prdAlterado.getDescricao());
		produtoActualizar.setValor(prdAlterado.getValor());
		produtoActualizar.setQuantidade(prdAlterado.getQuantidade());
		
		em.merge(produtoActualizar);
		
	}

	
	public Produto retrieveProdutoByNome(String nome)
			throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT prd FROM Produto as prd where prd.nome='");
		queryText.append(nome);
		queryText.append("'");
		TypedQuery<Produto> query = em.createQuery(queryText.toString(),
				Produto.class);
		Produto singleItem = null;
		try {
			singleItem = query.getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return singleItem;
	}
	
	public List<Produto> retrieveAllProdutos() throws NoResultFindException {
		StringBuilder queryText = new StringBuilder(
				"SELECT prd FROM Produto as prd");
		TypedQuery<Produto> query = em.createQuery(queryText.toString(),
				Produto.class);
		List<Produto> allItem = null;
		try {
			allItem = query.getResultList();
		} catch (NoResultException e) {
			throw new NoResultFindException();
		}
		return allItem;
	}
	
	private <P extends Produto> P findProduto(Class<P> clazz, Integer id)
			throws EntityNotFoundException {
		P e = em.find(clazz, id);
		if (e == null) {
			throw new EntityNotFoundException(clazz.getClass(), id);
		}
		return e;
	}

}
