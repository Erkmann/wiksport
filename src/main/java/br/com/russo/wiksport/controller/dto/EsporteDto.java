package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.russo.wiksport.model.Esportes;

public class EsporteDto {

	private Long id;
	private String nome;
	private String historia;
	private String praticantes;
	private String regras;
	private String icon = "";
	private LocalDateTime criado = LocalDateTime.now();
	private List<LigasHomeDto> ligas;

	public EsporteDto(Esportes esporte) {
		this.id = esporte.getId();
		this.nome = esporte.getNome();
		this.historia = esporte.getHistoria();
		this.praticantes = esporte.getPraticantes();
		this.regras = esporte.getRegras();
		this.icon = esporte.getIcon();
		this.criado = esporte.getCriado();
		this.setLigas(new ArrayList<>());
		this.getLigas().addAll(esporte.getLigas().stream().map(LigasHomeDto::new).collect(Collectors.toList()));
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

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	public String getPraticantes() {
		return praticantes;
	}

	public void setPraticantes(String praticantes) {
		this.praticantes = praticantes;
	}

	public String getRegras() {
		return regras;
	}

	public void setRegras(String regras) {
		this.regras = regras;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

	public List<LigasHomeDto> getLigas() {
		return ligas;
	}

	public void setLigas(List<LigasHomeDto> ligas) {
		this.ligas = ligas;
	}

}
