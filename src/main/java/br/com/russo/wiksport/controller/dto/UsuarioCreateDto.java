package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;

import br.com.russo.wiksport.model.Usuario;

public class UsuarioCreateDto {

	private Long id;
	private String nome;
	private String email;
	private LocalDateTime criado;

	public UsuarioCreateDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.criado = usuario.getCriado();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

}
