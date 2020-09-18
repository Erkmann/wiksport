package br.com.russo.wiksport.controller.form;

import javax.validation.constraints.NotNull;

public class CurtidaAtletaForm {
	@NotNull
	private Long atletaId;

	public Long getAtletaId() {
		return atletaId;
	}

	public void setAtletaId(Long atletaId) {
		this.atletaId = atletaId;
	}

}
