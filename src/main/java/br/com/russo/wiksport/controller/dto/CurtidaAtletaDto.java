package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;

import br.com.russo.wiksport.model.Curtida;

public class CurtidaAtletaDto {

	private Long id;
	private AtletaHomeDto atleta;
	private UsuarioHomeDto usuario;
	private LocalDateTime criado;

	public CurtidaAtletaDto(Curtida curtida, AtletaHomeDto atleta, UsuarioHomeDto usuario) {
		this.id = curtida.getId();
		this.atleta = atleta;
		this.usuario = usuario;
		this.criado = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AtletaHomeDto getAtleta() {
		return atleta;
	}

	public void setAtleta(AtletaHomeDto atleta) {
		this.atleta = atleta;
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
