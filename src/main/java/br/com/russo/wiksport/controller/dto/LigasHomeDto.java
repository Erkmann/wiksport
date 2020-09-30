package br.com.russo.wiksport.controller.dto;

import org.springframework.data.domain.Page;

import br.com.russo.wiksport.model.Ligas;

public class LigasHomeDto {

	private Long id;
	private String nome;
	private String icon;
	private Boolean curtido = false;

	public LigasHomeDto(Long id, String nome, String icon) {
		super();
		this.id = id;
		this.nome = nome;
		this.icon = icon;
	}

	public LigasHomeDto(Ligas liga) {
		this.id = liga.getId();
		this.nome = liga.getNome();
		this.icon = liga.getIcon();
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

	public Boolean getCurtido() {
		return curtido;
	}

	public void setCurtido(Boolean curtido) {
		this.curtido = curtido;
	}

	public static Page<LigasHomeDto> converter(Page<Ligas> ligas) {
		return ligas.map(LigasHomeDto::new);
	}

}
