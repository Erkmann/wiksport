package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;

import br.com.russo.wiksport.model.Curtida;

public class CurtidaLigaDto {

	private Long id;
	private LigasHomeDto liga;
	private UsuarioHomeDto usuario;
	private LocalDateTime criado;

	public CurtidaLigaDto(Curtida curtida, LigasHomeDto liga, UsuarioHomeDto usuario) {
		this.id = curtida.getId();
		this.liga = liga;
		this.usuario = usuario;
		this.criado = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LigasHomeDto getLiga() {
		return liga;
	}

	public void setLiga(LigasHomeDto liga) {
		this.liga = liga;
	}

	public UsuarioHomeDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioHomeDto usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

}
