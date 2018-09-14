package com.rumos.views;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.rumos.beans.LoginBean;
import com.rumos.dao.EmpregadoDAO;
import com.rumos.dao.FaturaDAO;
import com.rumos.dao.ProdutoDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Empregado;
import com.rumos.model.Fatura;
import com.rumos.model.Linhasdefatura;
import com.rumos.model.Produto;

@ManagedBean(name = "dtVendaView")
@ViewScoped
public class vendaView implements Serializable {

	private static final long serialVersionUID = 1L;

	private LazyDataModel<Linhasdefatura> lazyModel;

	private List<Linhasdefatura> linhasNovaFatura = new ArrayList<Linhasdefatura>();

	private Linhasdefatura selectedLinha;

	private String produtoNovaLinha;

	private List<String> produtosNovaLinha = new ArrayList<String>();

	private int quantidadeNovaLinha;

	private int quantidadeTotal;

	private int valorTotal;

	@Inject
	ProdutoDAO produtoDao;

	@Inject
	EmpregadoDAO empregadoDao;

	@Inject
	FaturaDAO faturaDao;

	@Inject
	LoginBean loginBean;

	@PostConstruct
	public void init() {

		List<Linhasdefatura> lvazia = new ArrayList<Linhasdefatura>();
		lazyModel = new LazyLinhasFaturaDataModel(lvazia);
	}

	public LazyDataModel<Linhasdefatura> getLazyModel() {
		return lazyModel;
	}

	public Linhasdefatura getSelectedLinha() {
		return selectedLinha;
	}

	public void setSelectedLinha(Linhasdefatura selectedLinha) {
		this.selectedLinha = selectedLinha;
	}

	public int getQuantidadeNovaLinha() {
		return quantidadeNovaLinha;
	}

	public void setQuantidadeNovaLinha(int quantidadeNovaLinha) {
		this.quantidadeNovaLinha = quantidadeNovaLinha;
	}

	public String getProdutoNovaLinha() {
		return produtoNovaLinha;
	}

	public void setProdutoNovaLinha(String produtoNovaLinha) {
		this.produtoNovaLinha = produtoNovaLinha;
	}

	public int getQuantidadeTotal() {

		quantidadeTotal = 0;

		for (Linhasdefatura linhasdefatura : lazyModel) {
			quantidadeTotal += linhasdefatura.getQuantidade();
		}

		return quantidadeTotal;
	}

	public int getValorTotal() {

		valorTotal = 0;

		for (Linhasdefatura linhasdefatura : lazyModel) {
			valorTotal += (linhasdefatura.getQuantidade() * linhasdefatura
					.getProduto().getValor());
		}

		return valorTotal;
	}

	public List<String> getProdutosNovaLinha() {

		if (produtosNovaLinha.isEmpty() && linhasNovaFatura.isEmpty()) {

			List<Produto> allProdutos = new ArrayList<Produto>();
			List<String> descProdutos = new ArrayList<String>();

			try {

				allProdutos = produtoDao.retrieveAllProdutos();

				Iterator<Produto> e = allProdutos.iterator();
				while (e.hasNext()) {
					Produto prdX = (Produto) e.next();

					descProdutos.add(prdX.getNome());
				}

			} catch (NoResultFindException e) {

			}

			produtosNovaLinha = descProdutos;
		}

		return produtosNovaLinha;
	}

	public void setProdutosNovaLinha(List<String> produtosNovaLinha) {
		this.produtosNovaLinha = produtosNovaLinha;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Produto Selected",
				((Linhasdefatura) event.getObject()).getProduto().getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String fecharVenda() {

		Empregado empregadoVenda;
		try {
			empregadoVenda = empregadoDao.retrieveEmpregadoByUserName(loginBean
					.getUsername());
		} catch (NoResultFindException e1) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
							"O utilizador não tem ficha de empregado!"));

			return "";

		}

		try {

			for (Linhasdefatura linhasdefatura : lazyModel) {

				int quantidadeProdutoDB = produtoDao.retrieveProdutoByNome(
						linhasdefatura.getProduto().getNome()).getQuantidade();
				if (quantidadeProdutoDB < linhasdefatura.getQuantidade()) {

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error!",
									"Quantidade insuficiente em stock para o produto: "
											+ linhasdefatura.getProduto()
													.getNome()));

					return "";
				}

			}

			Fatura novaFatura = new Fatura();

			novaFatura.setDatahora(Timestamp.valueOf(LocalDateTime.now()));
			novaFatura.setEmpregado(empregadoVenda);

			faturaDao.novaFatura(novaFatura);

			for (Linhasdefatura linhadefatura : lazyModel) {

				Produto prd = produtoDao.retrieveProdutoByNome(linhadefatura
						.getProduto().getNome());
				prd.setQuantidade(prd.getQuantidade()
						- linhadefatura.getQuantidade());
				produtoDao.alterarProduto(prd);

				Linhasdefatura novaLinhaFatura = new Linhasdefatura();

				novaLinhaFatura.setFatura(novaFatura);
				novaLinhaFatura.setProduto(prd);
				novaLinhaFatura.setQuantidade(linhadefatura.getQuantidade());
				novaLinhaFatura.setValor(linhadefatura.getQuantidade() * linhadefatura.getProduto().getValor());

				faturaDao.novaLinhaFatura(novaLinhaFatura);
			}

		} catch (NoResultFindException e) {

		} catch (Exception e) {

		}

		return "index";
	}

	public void alterarQuantidadeLinha() {

//		Linhasdefatura linhaSelecionada = getSelectedLinha();
//
//		for (Linhasdefatura linhasdefatura : lazyModel) {
//			if (linhasdefatura.getProduto().getNome()
//					.equals(linhaSelecionada.getProduto().getNome())) {
//				//Produto p = linhasdefatura.getProduto();
//				linhasdefatura.setQuantidade(getQuantidadeNovaLinha());
//				//linhasdefatura.setProduto(p);
//			}
//		}

	}

	public void acrescentarNovaLinha() {

		if (getProdutoNovaLinha() != null) {
			Linhasdefatura novaL = new Linhasdefatura();
			try {
				Produto novoP = produtoDao
						.retrieveProdutoByNome(getProdutoNovaLinha());
				novaL.setProduto(novoP);
				novaL.setQuantidade(getQuantidadeNovaLinha());
				novaL.setValor(novoP.getValor() * getQuantidadeNovaLinha());

				linhasNovaFatura.add(novaL);
				lazyModel = new LazyLinhasFaturaDataModel(linhasNovaFatura);
				produtosNovaLinha.remove(getProdutoNovaLinha());
				setProdutoNovaLinha("");
				setQuantidadeNovaLinha(0);

			} catch (NoResultFindException e) {
				System.out.println("Erro ao incluir nova linha de fatura;");
				e.printStackTrace();
			}
		}

	}

}
