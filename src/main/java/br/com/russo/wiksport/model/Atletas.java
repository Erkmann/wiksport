package br.com.russo.wiksport.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Atletas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDateTime nascimento = LocalDateTime.now();
	private LocalDateTime falescimento = LocalDateTime.now();
	private String titulos;
	private Long partidas;
	private String icon = "";
	@Column(columnDefinition = "boolean default true")
	private Boolean ativo;
	private LocalDateTime criado = LocalDateTime.now();
	private LocalDateTime editado = LocalDateTime.now();
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "equipes_atletas", joinColumns = {
			@JoinColumn(name = "atletas_id", referencedColumnName = "id", nullable = false, updatable = true) },
				inverseJoinColumns = {
						@JoinColumn(name = "equipes_id", referencedColumnName = "id", nullable = false, updatable = true) })
	private List<Equipes> equipes = new ArrayList<>();
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Curtida> curtidas;
	

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

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getCriado() {
		return criado;
	}

	public void setCriado(LocalDateTime criado) {
		this.criado = criado;
	}

	public LocalDateTime getEditado() {
		return editado;
	}

	public void setEditado(LocalDateTime editado) {
		this.editado = editado;
	}

	public List<Equipes> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipes> equipes) {
		this.equipes = equipes;
	}

	public List<Curtida> getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(List<Curtida> curtidas) {
		this.curtidas = curtidas;
	}

}
