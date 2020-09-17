package br.com.russo.wiksport.controller.dto;

import org.springframework.data.domain.Page;

import br.com.russo.wiksport.model.Esportes;

public class EsporteHomeDto {

	private Long id;
	private String nome;
	private String icon;

	public EsporteHomeDto(Long id, String nome, String icon) {
		this.id = id;
		this.nome = nome;
		this.icon = icon;
	}
	
	public EsporteHomeDto(Esportes esporte) {
		this.id = esporte.getId();
		this.nome = esporte.getNome();
		this.icon = esporte.getIcon();
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

	public static Page<EsporteHomeDto> converter(Page<Esportes> esportes) {
		return esportes.map(EsporteHomeDto::new);
	}

}
