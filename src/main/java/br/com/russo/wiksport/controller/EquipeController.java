package br.com.russo.wiksport.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.russo.wiksport.config.EquipeService;
import br.com.russo.wiksport.config.UserService;
import br.com.russo.wiksport.controller.dto.CurtidaEquipeDto;
import br.com.russo.wiksport.controller.dto.EquipesDto;
import br.com.russo.wiksport.controller.dto.EquipesHomeDto;
import br.com.russo.wiksport.controller.dto.UsuarioHomeDto;
import br.com.russo.wiksport.controller.form.CurtidaEquipeForm;
import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Equipes;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.CurtidaRepository;
import br.com.russo.wiksport.repository.EquipesRepository;
import br.com.russo.wiksport.repository.UsuarioRepository;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

	@Autowired
	EquipesRepository equipesRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CurtidaRepository curtidaRepository;

	@Autowired
	EquipeService equipeService;

	@Autowired
	UserService userService;

	@GetMapping
	public Page<EquipesHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {

		Page<EquipesHomeDto> converter = null;

		if (name == null) {
			Page<Equipes> ligas = equipesRepository.findAll(paginacao);
			converter = EquipesHomeDto.converter(ligas);
		} else {
			Page<Equipes> ligas = equipesRepository.findByNome(name, paginacao);
			converter = EquipesHomeDto.converter(ligas);
		}

		if (userService.isLogged()) {
			for (EquipesHomeDto e : converter) {
				Usuario usuario = userService.getLoggedUser();
				Optional<Equipes> eqp = equipesRepository.findById(e.getId());
				Equipes equipe = eqp.get();
				Optional<List<Curtida>> curtidasUsuario = curtidaRepository.findByUsuario(usuario);

				if (curtidasUsuario.isPresent()) {
					for (Curtida c : curtidasUsuario.get()) {
						if (equipe.getCurtidas().contains(c)) {
							e.setCurtido(true);
						}
					}
				}
			}
		}

		return converter;

	}

	@GetMapping("/{name}-{id}")
	public ResponseEntity<EquipesDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Equipes> equipe = equipeService.getEquipeNomeId(name, id);
		if (equipe.isPresent()) {
			EquipesDto equipeDto = new EquipesDto(equipe.get());
			if (userService.isLogged()) {
				Usuario usuario = userService.getLoggedUser();
				Optional<List<Curtida>> curtidasUsuario = curtidaRepository.findByUsuario(usuario);
				if (curtidasUsuario.isPresent()) {
					for (Curtida c : curtidasUsuario.get()) {
						if (equipe.get().getCurtidas().contains(c)) {
							equipeDto.setCurtido(true);
						}
					}
				}
			}

			return ResponseEntity.ok().body(equipeDto);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/curtir")
	@Transactional
	public ResponseEntity<CurtidaEquipeDto> curtir(@RequestBody @Valid CurtidaEquipeForm form) {
		Usuario usario = userService.getLoggedUser();
		Long id = form.getEquipeId();

		Optional<Equipes> equipe = equipesRepository.findById(id);

		if (equipe.isPresent()) {
			Equipes equipeO = equipe.get();
			Optional<List<Curtida>> curtidasUser = curtidaRepository.findByUsuario(usario);
			if (curtidasUser.isPresent()) {
				for (Curtida c : curtidasUser.get()) {
					if (equipeO.getCurtidas().contains(c)) {
						List<Curtida> novasCurtidas = equipeO.getCurtidas();
						novasCurtidas.remove(c);
						equipeO.setCurtidas(novasCurtidas);
						return ResponseEntity.ok().build();
					}
				}
			}

			Curtida curtida = new Curtida();
			curtida.setUsuario(usario);
			curtidaRepository.save(curtida);
			System.out.println(curtida);
			List<Curtida> curtidas = equipeO.getCurtidas();
			curtidas.add(curtida);
			equipeO.setCurtidas(curtidas);

			return ResponseEntity.created(null)
					.body(new CurtidaEquipeDto(curtida, new EquipesHomeDto(equipeO), new UsuarioHomeDto(usario)));

		}

		return ResponseEntity.badRequest().build();
	}

}
