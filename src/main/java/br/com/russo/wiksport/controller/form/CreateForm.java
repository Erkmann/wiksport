package br.com.russo.wiksport.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.istack.NotNull;

import br.com.russo.wiksport.model.Usuario;

public class CreateForm {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String nome;
	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String email;
	@NotNull
	@NotEmpty
	@Length(min = 8)
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario converter() {
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setVerificado(false);
		usuario.setCriado(LocalDateTime.now());
		usuario.setEmail(email);
		usuario.setNome(nome);
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

		return usuario;
	}

}
