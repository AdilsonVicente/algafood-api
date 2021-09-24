package com.example.demo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.filter.VendaDiariaFilter;
import com.example.demo.domain.model.dto.VendaDiaria;
import com.example.demo.domain.service.VendaQueryService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;

	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendaDiarias(VendaDiariaFilter filtro, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
}