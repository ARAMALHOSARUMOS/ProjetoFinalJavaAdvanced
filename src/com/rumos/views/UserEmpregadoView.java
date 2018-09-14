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
import com.rumos.dao.UserEmpregadoDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.model.UserEmpregado;

@ManagedBean(name = "dtUserEmpregadoView")
@ViewScoped
public class UserEmpregadoView implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<UserEmpregado> lazyModel;

	private UserEmpregado selectedUserEmpregado;

	private List<UserEmpregado> filteredUserEmpregados;

	@Inject
	UserEmpregadoDAO userempregadoDao;

	@Inject
	LoginBean loginBean;

	@PostConstruct
	public void init() {
	}


	public List<UserEmpregado> getLazyModel() {
		
		try {
			lazyModel = userempregadoDao.retrieveAllUserEmpregado();
		} catch (NoResultFindException e) {
			lazyModel = new ArrayList<UserEmpregado>();
		}

		return lazyModel;
	}

	public UserEmpregado getSelectedUserEmpregado() {
		return selectedUserEmpregado;
	}

	public void setSelectedUserEmpregado(UserEmpregado selectedUserEmpregado) {
		this.selectedUserEmpregado = selectedUserEmpregado;
	}

	public List<UserEmpregado> getFilteredUserEmpregados() {
		return filteredUserEmpregados;
	}

	public void setFilteredUserEmpregados(List<UserEmpregado> filteredUserEmpregados) {
		this.filteredUserEmpregados = filteredUserEmpregados;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("UserEmpregado Selected",
				((UserEmpregado) event.getObject()).getUser().getUsername());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
