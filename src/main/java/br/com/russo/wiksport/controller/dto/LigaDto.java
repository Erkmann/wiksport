package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.russo.wiksport.model.Ligas;

public class LigaDto {

	private Long id;
	private String nome;
	private String historia;
	private LocalDateTime fundacao = LocalDateTime.now();
	private String regulamento;
	private String icon = "";
	private LocalDateTime criado = LocalDateTime.now();
	private EsporteHomeDto esporte;
	private List<EquipesHomeDto> equipes;

	public LigaDto(Ligas liga) {
		super();
		this.id = liga.getId();
		this.nome = liga.getNome();
		this.historia = liga.getHistoria();
		this.fundacao = liga.getFundacao();
		this.regulamento = liga.getRegulamento();
		this.icon = liga.getIcon();
		this.criado = liga.getCriado();
		this.esporte = new EsporteHomeDto(liga.getEsporte());
		this.setEquipes(new ArrayList<>());
		this.getEquipes().addAll(liga.getEquipes().stream().map(EquipesHomeDto::new).collect(Collectors.toList()));
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

	public EsporteHomeDto getEsporte() {
		return esporte;
	}

	public void setEsporte(EsporteHomeDto esporte) {
		this.esporte = esporte;
	}

	public List<EquipesHomeDto> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<EquipesHomeDto> equipes) {
		this.equipes = equipes;
	}

}
