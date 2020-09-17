package br.com.russo.wiksport.controller.dto;

public class HomeDto {

	private EsporteHomeDto esporte;
	private LigasHomeDto liga;
	private EquipesHomeDto equipe;
	private AtletaHomeDto atleta;

	public HomeDto(EsporteHomeDto esporte, LigasHomeDto liga, EquipesHomeDto equipe, AtletaHomeDto atleta) {
		this.esporte = esporte;
		this.liga = liga;
		this.equipe = equipe;
		this.atleta = atleta;
	}

	public EsporteHomeDto getEsporte() {
		return esporte;
	}

	public LigasHomeDto getLiga() {
		return liga;
	}

	public EquipesHomeDto getEquipe() {
		return equipe;
	}

	public AtletaHomeDto getAtleta() {
		return atleta;
	}

}
