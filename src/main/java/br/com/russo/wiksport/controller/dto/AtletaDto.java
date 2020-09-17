package br.com.russo.wiksport.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.russo.wiksport.model.Atletas;

public class AtletaDto {

	private Long id;
	private String nome;
	private LocalDateTime nascimento = LocalDateTime.now();
	private LocalDateTime falescimento = LocalDateTime.now();
	private String titulos;
	private Long partidas;
	private String icon = "";
	private LocalDateTime criado;
	private List<EquipesHomeDto> equipes;

	public AtletaDto(Atletas atleta) {
		this.id = atleta.getId();
		this.nome = atleta.getNome();
		this.nascimento = atleta.getNascimento();
		this.falescimento = atleta.getFalescimento();
		this.titulos = atleta.getTitulos();
		this.partidas = atleta.getPartidas();
		this.icon = atleta.getIcon();
		this.criado = atleta.getCriado();
		this.equipes = new ArrayList<>();
		this.equipes.addAll(atleta.getEquipes().stream().map(EquipesHomeDto::new).collect(Collectors.toList()));
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

	public LocalDateTime getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDateTime nascimento) {
		this.nascimento = nascimento;
	}

	public LocalDateTime getFalescimento() {
		return falescimento;
	}

	public void setFalescimento(LocalDateTime falescimento) {
		this.falescimento = falescimento;
	}

	public String getTitulos() {
		return titulos;
	}

	public void setTitulos(String titulos) {
		this.titulos = titulos;
	}

	public Long getPartidas() {
		return partidas;
	}

	public void setPartidas(Long partidas) {
		this.partidas = partidas;
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

	public List<EquipesHomeDto> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<EquipesHomeDto> equipes) {
		this.equipes = equipes;
	}

}
