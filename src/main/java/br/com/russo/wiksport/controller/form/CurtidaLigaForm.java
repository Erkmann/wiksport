package br.com.russo.wiksport.controller.form;

import javax.validation.constraints.NotNull;

public class CurtidaLigaForm {
	
	@NotNull
	private Long ligaId;

	public Long getLigaId() {
		return ligaId;
	}

	public void setLigaId(Long ligaId) {
		this.ligaId = ligaId;
	}

}
