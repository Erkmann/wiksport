package br.com.russo.wiksport.controller.form;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sun.istack.NotNull;

import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.UsuarioRepository;

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

	public Usuario converter(UsuarioRepository repository) {
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setVerificado(false);
		usuario.setCriado(LocalDateTime.now());
		usuario.setEmail(email);
		usuario.setNome(nome);
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		
		StringBuffer random = new StringBuffer(RandomStringUtils.randomAlphanumeric(30));
		Optional<Usuario> optional = repository.findByConfirmacaoString(random.toString());
		System.out.println(random.toString());
		
		Boolean existe = false;
		if (optional.isPresent()) {
			existe = true;
		}
		
		while (existe == true) {
			random = new StringBuffer(RandomStringUtils.randomAlphanumeric(30));
			Optional<Usuario> opt = repository.findByConfirmacaoString(random.toString());
			existe = false;
			if (opt .isPresent()) {
				existe = true;
			}
		}
		
		usuario.setConfirmacaoString(random.toString());
		
		
		return usuario;
	}

}
