package com.rumos.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USERS database table.
 * 
 */
@Entity
@Table(name="USERS")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int iduser;

	private String password;

	private String role;

	private String username;

	//bi-directional many-to-one association to Empregado
	@OneToMany(mappedBy="user")
	private List<Empregado> empregados;

	public User() {
	}

	public int getIduser() {
		return this.iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Empregado> getEmpregados() {
		return this.empregados;
	}

	public void setEmpregados(List<Empregado> empregados) {
		this.empregados = empregados;
	}

	public Empregado addEmpregado(Empregado empregado) {
		getEmpregados().add(empregado);
		empregado.setUser(this);

		return empregado;
	}

	public Empregado removeEmpregado(Empregado empregado) {
		getEmpregados().remove(empregado);
		empregado.setUser(null);

		return empregado;
	}

}