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

import br.com.russo.wiksport.config.LigaService;
import br.com.russo.wiksport.controller.dto.LigaDto;
import br.com.russo.wiksport.controller.dto.LigasHomeDto;
import br.com.russo.wiksport.model.Ligas;
import br.com.russo.wiksport.repository.LigasRepository;

@RestController
@RequestMapping("/ligas")
public class LigaController {

	@Autowired
	LigasRepository ligaRepository;

	@Autowired
	LigaService ligaService;

	@GetMapping
	public Page<LigasHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {
		if (name == null) {
			Page<Ligas> ligas = ligaRepository.findAll(paginacao);
			return LigasHomeDto.converter(ligas);
		} else {
			Page<Ligas> ligas = ligaRepository.findByNome(name, paginacao);
			return LigasHomeDto.converter(ligas);
		}
	}

	@GetMapping("/{name}-{id}")
	public ResponseEntity<LigaDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Ligas> liga = ligaService.getLigaNomeId(name, id);
		if (liga.isPresent()) {
			return ResponseEntity.ok().body(new LigaDto(liga.get()));
		}

		return ResponseEntity.notFound().build();
	}

}
