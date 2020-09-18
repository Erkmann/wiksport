package br.com.russo.wiksport.controller.form;

import javax.validation.constraints.NotNull;

public class CurtidaEquipeForm {

	@NotNull
	private Long equipeId;

	public Long getEquipeId() {
		return equipeId;
	}

	public void setEquipeId(Long equipeId) {
		this.equipeId = equipeId;
	}

}
