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

import br.com.russo.wiksport.config.EquipeService;
import br.com.russo.wiksport.controller.dto.EquipesDto;
import br.com.russo.wiksport.controller.dto.EquipesHomeDto;
import br.com.russo.wiksport.model.Equipes;
import br.com.russo.wiksport.repository.EquipesRepository;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

	@Autowired
	EquipesRepository equipesRepository;

	@Autowired
	EquipeService equipeService;

	@GetMapping
	public Page<EquipesHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {
		if (name == null) {
			Page<Equipes> ligas = equipesRepository.findAll(paginacao);
			return EquipesHomeDto.converter(ligas);
		} else {
			Page<Equipes> ligas = equipesRepository.findByNome(name, paginacao);
			return EquipesHomeDto.converter(ligas);
		}
	}

	@GetMapping("/{name}-{id}")
	public ResponseEntity<EquipesDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Equipes> equipe = equipeService.getEquipeNomeId(name, id);
		if (equipe.isPresent()) {
			return ResponseEntity.ok().body(new EquipesDto(equipe.get()));
		}

		return ResponseEntity.notFound().build();
	}

}
