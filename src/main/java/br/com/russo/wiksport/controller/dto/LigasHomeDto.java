package br.com.russo.wiksport.controller.dto;

public class LigasHomeDto {
	
	private Long id;
	private String nome;
	private String icon;
	
	public LigasHomeDto(Long id, String nome, String icon) {
		this.id = id;
		this.nome = nome;
		this.icon = icon;
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

}
