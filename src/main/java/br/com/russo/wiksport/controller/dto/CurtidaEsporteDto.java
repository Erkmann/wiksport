package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;

import br.com.russo.wiksport.model.Curtida;

public class CurtidaEsporteDto {

	private Long id;
	private EsporteHomeDto esporte;
	private UsuarioHomeDto usuario;
	private LocalDateTime criado;

	public CurtidaEsporteDto(Curtida curtida, EsporteHomeDto esporte, UsuarioHomeDto usuario) {
		this.id = curtida.getId();
		this.esporte = esporte;
		this.usuario = usuario;
		this.criado = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EsporteHomeDto getEsporte() {
		return esporte;
	}

	public void setEsporte(EsporteHomeDto esporte) {
		this.esporte = esporte;
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
