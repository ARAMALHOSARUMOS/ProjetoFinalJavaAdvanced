package com.rumos.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rumos.dao.EmpregadoDAO;
import com.rumos.dao.UserDAO;
import com.rumos.exceptions.NoResultFindException;
import com.rumos.exceptions.PreexistingUserException;
import com.rumos.model.Empregado;
import com.rumos.model.User;

@Named("empregado")
@SessionScoped
public class EmpregadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmpregadoDAO empregadoDao;

	@Inject
	UserDAO userDao;

	private String nome;
	private Date dataAdmissao;
	private Date dataNascimento;
	private int telemovel;
	private int nif;
	private String cargo;
	private String userName;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public int getTelemovel() {
		return telemovel;
	}

	public void setTelemovel(int telemovel) {
		this.telemovel = telemovel;
	}

	public int getNif() {
		return nif;
	}

	public void setNif(int nif) {
		this.nif = nif;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getUserList() {

		List<User> userslist = userDao.getAllUsers();
		List<Empregado> empregadoslist;
		try {
			empregadoslist = empregadoDao.retrieveAllEmpregados();
		} catch (NoResultFindException e1) {
			empregadoslist = null;
		}
		List<String> usersEmpregados = new ArrayList<String>();
		List<String> empReturn = new ArrayList<String>();

		Iterator<Empregado> e = empregadoslist.iterator();
		while (e.hasNext()) {
			Empregado empregadoX = (Empregado) e.next();
			usersEmpregados.add(empregadoX.getUser().getUsername());
		}

		Iterator<User> g = userslist.iterator();
		while (g.hasNext()) {
			User userX = (User) g.next();
			if (!usersEmpregados.contains(userX.getUsername())) {
				empReturn.add(userX.getUsername());
			}
		}

		return empReturn;
	}

	public String addEmpregado() throws PreexistingUserException, Exception {

		User empregadoUser = userDao.retrieveUserByName(userName);

		empregadoDao.novoEmpregado(nome, dataAdmissao, dataNascimento,
				telemovel, nif, cargo, empregadoUser);
		nome = "";
		dataAdmissao = new Date(19900101);
		dataNascimento = new Date(19900101);
		telemovel = 0;
		nif = 0;
		cargo = "";
		return "index";

	}

}
