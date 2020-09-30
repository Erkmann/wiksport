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

import br.com.russo.wiksport.config.EsporteService;
import br.com.russo.wiksport.config.UserService;
import br.com.russo.wiksport.controller.dto.CurtidaEsporteDto;
import br.com.russo.wiksport.controller.dto.EsporteDto;
import br.com.russo.wiksport.controller.dto.EsporteHomeDto;
import br.com.russo.wiksport.controller.dto.LigasHomeDto;
import br.com.russo.wiksport.controller.dto.UsuarioHomeDto;
import br.com.russo.wiksport.controller.form.CurtidaEsporteForm;
import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.model.Ligas;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.CurtidaRepository;
import br.com.russo.wiksport.repository.EsporteRepository;

@RestController
@RequestMapping("/esportes")
public class EsporteController {

	@Autowired
	private EsporteRepository esporteRepository;

	@Autowired
	private EsporteService esporteService;

	@Autowired
	private CurtidaRepository curtidaRepository;

	@Autowired
	private UserService userService;

	@GetMapping
	public Page<EsporteHomeDto> listar(@RequestParam(required = false) String name,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 15) Pageable paginacao) {

		Page<EsporteHomeDto> converter = null;

		if (name == null) {
			Page<Esportes> esportes = esporteRepository.findAll(paginacao);
			converter = EsporteHomeDto.converter(esportes);
		} else {
			Page<Esportes> esportes = esporteRepository.findByNome(name, paginacao);
			converter = EsporteHomeDto.converter(esportes);
		}

		if (userService.isLogged()) {
			for (EsporteHomeDto e : converter) {
				Usuario usuario = userService.getLoggedUser();
				Optional<Esportes> esp = esporteRepository.findById(e.getId());
				Esportes esporte = esp.get();
				Optional<List<Curtida>> curtidasUsuario = curtidaRepository.findByUsuario(usuario);

				if (curtidasUsuario.isPresent()) {
					for (Curtida c : curtidasUsuario.get()) {
						if (esporte.getCurtidas().contains(c)) {
							e.setCurtido(true);
						}
					}
				}
			}
		}

		return converter;

	}

	@GetMapping("/{name}-{id}")
	public ResponseEntity<EsporteDto> detalhar(@PathVariable String name, @PathVariable Long id) {
		Optional<Esportes> esporte = esporteService.getEsporteNomeId(name, id);
		if (esporte.isPresent()) {
			EsporteDto esporteDto = new EsporteDto(esporte.get());
			if (userService.isLogged()) {
				Usuario usuario = userService.getLoggedUser();
				Optional<List<Curtida>> curtidasUsuario = curtidaRepository.findByUsuario(usuario);
				if (curtidasUsuario.isPresent()) {
					for (Curtida c : curtidasUsuario.get()) {
						if (esporte.get().getCurtidas().contains(c)) {
							esporteDto.setCurtido(true);
						}
						else {
							for (Ligas l : esporte.get().getLigas()) {
								if (l.getCurtidas().contains(c)){
									List<LigasHomeDto> ligasEsporte = esporteDto.getLigas();
									for (LigasHomeDto ll : ligasEsporte) {
										if (ll.getId().equals(l.getId())) {
											ll.setCurtido(true);
										}
									}
								}
							}
						}
					}
				}
			}

			return ResponseEntity.ok().body(esporteDto);
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping("/curtir")
	@Transactional
	public ResponseEntity<CurtidaEsporteDto> curtir(@RequestBody @Valid CurtidaEsporteForm form) {
		Usuario usuario = userService.getLoggedUser();
		Long id = form.getEsporteId();
		Optional<Esportes> esporte = esporteRepository.findById(id);

		if (esporte.isPresent()) {
			Esportes esporteO = esporte.get();
			Optional<List<Curtida>> curtidasUser = curtidaRepository.findByUsuario(usuario);
			if (curtidasUser.isPresent()) {
				for (Curtida c : curtidasUser.get()) {
					if (esporteO.getCurtidas().contains(c)) {
						List<Curtida> novasCurtidas = esporteO.getCurtidas();
						novasCurtidas.remove(c);
						esporteO.setCurtidas(novasCurtidas);
						return ResponseEntity.ok().build();
					}
				}
			}

			Curtida curtida = new Curtida();
			curtida.setUsuario(usuario);
			curtidaRepository.save(curtida);
			System.out.println(curtida);
			List<Curtida> curtidas = esporteO.getCurtidas();
			curtidas.add(curtida);
			esporteO.setCurtidas(curtidas);

			return ResponseEntity.created(null)
					.body(new CurtidaEsporteDto(curtida, new EsporteHomeDto(esporteO), new UsuarioHomeDto(usuario)));
		}

		return ResponseEntity.badRequest().build();
	}

}
