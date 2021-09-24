package com.example.demo.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoInput {

	@NotNull
	private String nome;
	
	@NotNull
	private String descricao;
}
