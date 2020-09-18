package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;

import br.com.russo.wiksport.model.Curtida;

public class CurtidaEquipeDto {

	private Long id;
	private EquipesHomeDto equipe;
	private UsuarioHomeDto usuario;
	private LocalDateTime criado;

	public CurtidaEquipeDto(Curtida curtida, EquipesHomeDto equipe, UsuarioHomeDto usuario) {
		this.id = curtida.getId();
		this.equipe = equipe;
		this.usuario = usuario;
		this.criado = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EquipesHomeDto getEquipe() {
		return equipe;
	}

	public void setEquipe(EquipesHomeDto equipe) {
		this.equipe = equipe;
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
