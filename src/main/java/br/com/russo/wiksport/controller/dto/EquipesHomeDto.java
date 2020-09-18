package br.com.russo.wiksport.controller.dto;

import org.springframework.data.domain.Page;

import br.com.russo.wiksport.model.Equipes;

public class EquipesHomeDto {

	private Long id;
	private String nome;
	private String icon;
	private Boolean curtido = false;

	public EquipesHomeDto(Long id, String nome, String icon) {
		this.id = id;
		this.nome = nome;
		this.icon = icon;
	}

	public EquipesHomeDto(Equipes equipe) {
		this.id = equipe.getId();
		this.nome = equipe.getNome();
		this.icon = equipe.getIcon();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getIcon() {
		return icon;
	}

	public boolean isCurtido() {
		return curtido;
	}

	public void setCurtido(Boolean curtido) {
		this.curtido = curtido;
	}

	public static Page<EquipesHomeDto> converter(Page<Equipes> equipes) {
		return equipes.map(EquipesHomeDto::new);
	}

}
