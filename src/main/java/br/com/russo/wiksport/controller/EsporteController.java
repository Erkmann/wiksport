package br.com.russo.wiksport.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.russo.wiksport.config.EsporteService;
import br.com.russo.wiksport.controller.dto.EsporteDto;
import br.com.russo.wiksport.controller.dto.EsporteHomeDto;
import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.repository.EsporteRepository;

@RestController
@RequestMapping("/esportes")
public class EsporteController {

	@Autowired
	private EsporteRepository esporteRepository;
	
	@Autowired
	private EsporteService esporteService;

	@GetMapping
	public Page<EsporteHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {
		if (name == null) {
			Page<Esportes> esportes = esporteRepository.findAll(paginacao);
			return EsporteHomeDto.converter(esportes);
		} else {
			Page<Esportes> esportes = esporteRepository.findByNome(name, paginacao);
			return EsporteHomeDto.converter(esportes);
		}
	}

	@GetMapping("/{name}-{id}")
	public ResponseEntity<EsporteDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Esportes> esporte = esporteService.getEsporteNomeId(name, id);
		if (esporte.isPresent()) {
			return ResponseEntity.ok().body(new EsporteDto(esporte.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

}
