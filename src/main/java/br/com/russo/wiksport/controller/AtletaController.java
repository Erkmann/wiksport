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

import br.com.russo.wiksport.config.atletaService;
import br.com.russo.wiksport.controller.dto.AtletaDto;
import br.com.russo.wiksport.controller.dto.AtletaHomeDto;
import br.com.russo.wiksport.model.Atletas;
import br.com.russo.wiksport.repository.AtletasRepository;

@RestController
@RequestMapping("/atletas")
public class AtletaController {
	
	@Autowired
	AtletasRepository atletaRepository;
	
	@Autowired
	atletaService atletaService;

	@GetMapping
	public Page<AtletaHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {
		if (name == null) {
			Page<Atletas> atletas = atletaRepository.findAll(paginacao);
			return AtletaHomeDto.converter(atletas);
		} else {
			Page<Atletas> atletas = atletaRepository.findByNome(name, paginacao);
			return AtletaHomeDto.converter(atletas);
		}
	}
	
	@GetMapping("/{name}-{id}")
	public ResponseEntity<AtletaDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Atletas> atleta = atletaService.getAtletaNomeId(name, id);
		if (atleta.isPresent()) {
			return ResponseEntity.ok().body(new AtletaDto(atleta.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
}
