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
import javax.persistence.ManyToOne;

@Entity
public class Ligas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String historia;
	private LocalDateTime fundacao = LocalDateTime.now();
	private String regulamento;
	private String icon = "";
	@Column(columnDefinition = "boolean default true")
	private Boolean ativo;
	private LocalDateTime criado = LocalDateTime.now();
	private LocalDateTime editado = LocalDateTime.now();

	@ManyToOne
	private Esportes esporte;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ligas_equipes",
    joinColumns = {
            @JoinColumn(name = "ligas_id", referencedColumnName = "id",
                    nullable = false, updatable = true)},
    inverseJoinColumns = {
            @JoinColumn(name = "equipes_id", referencedColumnName = "id",
                    nullable = false, updatable = true)})
	private List<Equipes> equipes = new ArrayList<>();

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

	public Esportes getEsporte() {
		return esporte;
	}

	public void setEsporte(Esportes esporte) {
		this.esporte = esporte;
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

}
