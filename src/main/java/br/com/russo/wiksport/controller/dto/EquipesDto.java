package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.russo.wiksport.model.Equipes;

public class EquipesDto {

	private Long id;
	private String nome;
	private String historia;
	private LocalDateTime fundacao;
	private String titulos;
	private String regulamento;
	private String icon = "";
	private LocalDateTime criado = LocalDateTime.now();
	private List<LigasHomeDto> ligas;
	private List<AtletaHomeDto> atletas;

	public EquipesDto(Equipes equipe) {
		this.id = equipe.getId();
		this.nome = equipe.getNome();
		this.historia = equipe.getHistoria();
		this.fundacao = equipe.getFundacao();
		this.titulos = equipe.getTitulos();
		this.regulamento = equipe.getRegulamento();
		this.icon = equipe.getIcon();
		this.criado = equipe.getCriado();
		this.ligas = new ArrayList<>();
		this.ligas.addAll(equipe.getLigas().stream().map(LigasHomeDto::new).collect(Collectors.toList()));
		this.atletas = new ArrayList<>();
		this.atletas.addAll(equipe.getAtletas().stream().map(AtletaHomeDto::new).collect(Collectors.toList()));
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

	public LocalDateTime getFundacao() {
		return fundacao;
	}

	public void setFundacao(LocalDateTime fundacao) {
		this.fundacao = fundacao;
	}

	public String getTitulos() {
		return titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}

	public String getRegulamento() {
		return regulamento;
	}

	public void setRegulamento(String regulamento) {
		this.regulamento = regulamento;
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

	public List<AtletaHomeDto> getAtletas() {
		return atletas;
	}

	public void setAtletas(List<AtletaHomeDto> atletas) {
		this.atletas = atletas;
	}

}
