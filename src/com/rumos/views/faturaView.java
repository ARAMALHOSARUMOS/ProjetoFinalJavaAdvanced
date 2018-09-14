package com.rumos.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.rumos.beans.LoginBean;
import com.rumos.dao.FaturaDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.Linhasdefatura;

@ManagedBean(name = "dtFaturaView")
@ViewScoped
public class faturaView implements Serializable {

	private static final long serialVersionUID = 1L;

	// private LazyDataModel<Linhasdefatura> lazyModel;

	private List<Linhasdefatura> lazyModel;

	private Linhasdefatura selectedLinhasdefatura;

	private boolean empregadoLinhasdefatura;

	@Inject
	FaturaDAO faturaDao;

	@Inject
	LoginBean loginBean;

	@PostConstruct
	public void init() {

	}

	public List<Linhasdefatura> getLazyModel() {

		try {
			lazyModel = faturaDao.retrieveAllLinhasFatura();
		} catch (NoResultFindException e) {
			lazyModel = new ArrayList<Linhasdefatura>();
		}

		return lazyModel;
	}

	public Linhasdefatura getSelectedLinhasdefatura() {
		return selectedLinhasdefatura;
	}

	public void setSelectedLinhasdefatura(Linhasdefatura selectedLinhasdefatura) {
		this.selectedLinhasdefatura = selectedLinhasdefatura;
	}

}
