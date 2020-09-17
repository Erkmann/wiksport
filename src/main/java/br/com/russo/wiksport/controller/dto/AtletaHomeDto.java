package br.com.russo.wiksport.controller.dto;

import org.springframework.data.domain.Page;

import br.com.russo.wiksport.model.Atletas;

public class AtletaHomeDto {

	private Long id;
	private String nome;
	private String icon;

	public AtletaHomeDto(Long id, String nome, String icon) {
		this.id = id;
		this.nome = nome;
		this.icon = icon;
	}
	
	public AtletaHomeDto(Atletas atleta) {
		this.id = atleta.getId();
		this.nome = atleta.getNome();
		this.icon = atleta.getIcon();
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

	public static Page<AtletaHomeDto> converter(Page<Atletas> atletas) {
		return atletas.map(AtletaHomeDto::new);
	}

}
