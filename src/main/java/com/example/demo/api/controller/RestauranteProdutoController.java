package com.example.demo.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.assembler.ProdutoInputDisassembler;
import com.example.demo.api.assembler.ProdutoModelAssembler;
import com.example.demo.api.model.ProdutoModel;
import com.example.demo.api.model.input.ProdutoInput;
import com.example.demo.domain.model.Produto;
import com.example.demo.domain.model.Restaurante;
import com.example.demo.domain.repository.ProdutoRepository;
import com.example.demo.domain.service.CadastroProdutoService;
import com.example.demo.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoModelAssembler produtoModelAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoInputDisassembler;
	
	@GetMapping
	public List<ProdutoModel> listar(@PathVariable Long restauranteId, 
			@RequestParam(required = false) boolean incluirInativos){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		List<Produto> todosProdutos = null;
		
		if (incluirInativos) {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		}
		
		return produtoModelAssembler.toCollectModel(todosProdutos);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoModel adicionar(@PathVariable Long restauranteId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoModel atualizar(@PathVariable Long produtoId, @PathVariable Long restauranteId,
			@RequestBody @Valid ProdutoInput produtoInput) {
		Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		 produtoInputDisassembler.copyToDomainObject(produtoAtual, produtoInput);
		
		return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produtoAtual));
	}
	
	 @GetMapping("/{produtoId}")
	    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
	        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
	        
	        return produtoModelAssembler.toModel(produto);
	    }

}
