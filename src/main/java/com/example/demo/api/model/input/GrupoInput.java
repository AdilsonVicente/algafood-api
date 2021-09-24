package com.example.demo.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrupoInput {

	@NotBlank
	private String nome;
	
//	@Valid
//	@NotNull
//	private Permissao permissao;
}
