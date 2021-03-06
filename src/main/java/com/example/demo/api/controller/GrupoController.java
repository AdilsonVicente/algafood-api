package com.example.demo.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.GrupoInputDisassembler;
import com.example.demo.api.assembler.GrupoModelAssembler;
import com.example.demo.api.model.GrupoModel;
import com.example.demo.api.model.input.GrupoInput;
import com.example.demo.domain.model.Grupo;
import com.example.demo.domain.repository.GrupoRepository;
import com.example.demo.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	@GetMapping
	public List<GrupoModel> listar(){
		List<Grupo> todosGrupos = grupoRepository.findAll();
		return grupoModelAssembler.toCollectModel(todosGrupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@Valid @RequestBody GrupoInput grupoInput) {
		Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
		
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
	}
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@Valid @RequestBody GrupoInput grupoInput, @PathVariable Long grupoId) {
		Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoAtual, grupoInput);
		
		return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
		
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long grupoId) {
		cadastroGrupoService.excluir(grupoId);
	}
	
}
