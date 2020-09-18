package br.com.russo.wiksport.controller.form;

import javax.validation.constraints.NotNull;

public class CurtidaEsporteForm {
	@NotNull
	private Long esporteId;

	public Long getEsporteId() {
		return esporteId;
	}

	public void setEsporteId(Long esporteId) {
		this.esporteId = esporteId;
	}

}
