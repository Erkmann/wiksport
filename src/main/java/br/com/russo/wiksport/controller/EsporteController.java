package br.com.russo.wiksport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.russo.wiksport.controller.dto.EsporteHomeDto;
import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.repository.EsporteRepository;

@RestController
@RequestMapping("/esportes")
public class EsporteController {

	@Autowired
	private EsporteRepository esporteRepository;

	@GetMapping
	public Page<EsporteHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {
		if (name == null) {
			Page<Esportes> esportes = esporteRepository.findAll(paginacao);
			return EsporteHomeDto.converter(esportes);
		} else {
			Page<Esportes> topicos = esporteRepository.findByNome(name, paginacao);
			return EsporteHomeDto.converter(topicos);
		}
	}

}
