package com.example.demo.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

	@NotNull
	@NotBlank
	private String nome;
	
	@NotNull
	@Email
	private String email;
	
}
