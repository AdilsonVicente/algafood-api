package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

//	List<FormaPagamento> listar();
//	FormaPagamento buscar(Long id);
//	FormaPagamento salvar(FormaPagamento formaPagamento);
//	void remover(Long id);
}
