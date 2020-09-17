package br.com.russo.wiksport.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginForm {

	private String email;
	private String senha;

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public void setEmai(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsernamePasswordAuthenticationToken converter() {
		return new UsernamePasswordAuthenticationToken(email, senha);
	}

}
