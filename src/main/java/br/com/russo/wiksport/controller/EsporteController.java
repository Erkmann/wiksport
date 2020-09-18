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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.russo.wiksport.config.EsporteService;
import br.com.russo.wiksport.controller.dto.CurtidaEsporteDto;
import br.com.russo.wiksport.controller.dto.EsporteDto;
import br.com.russo.wiksport.controller.dto.EsporteHomeDto;
import br.com.russo.wiksport.controller.dto.UsuarioHomeDto;
import br.com.russo.wiksport.controller.form.CurtidaEsporteForm;
import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.CurtidaRepository;
import br.com.russo.wiksport.repository.EsporteRepository;
import br.com.russo.wiksport.repository.UsuarioRepository;

@RestController
@RequestMapping("/esportes")
public class EsporteController {

	@Autowired
	private EsporteRepository esporteRepository;

	@Autowired
	private EsporteService esporteService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	CurtidaRepository curtidaRepository;

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

	@PostMapping("/curtir")
	@Transactional
	public ResponseEntity<CurtidaEsporteDto> curtir(@RequestBody @Valid CurtidaEsporteForm form) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		UserDetails user = (UserDetails) principal;
		Long id = form.getEsporteId();

		Optional<Esportes> esporte = esporteRepository.findById(id);

		if (esporte.isPresent()) {
			Esportes esporteO = esporte.get();
			Optional<Usuario> usuario = usuarioRepository.findByEmail(user.getUsername());
			if (usuario.isPresent()) {
				Optional<List<Curtida>> curtidasUser = curtidaRepository.findByUsuario(usuario.get());
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
				curtida.setUsuario(usuario.get());
				curtidaRepository.save(curtida);
				System.out.println(curtida);
				List<Curtida> curtidas = esporteO.getCurtidas();
				curtidas.add(curtida);
				esporteO.setCurtidas(curtidas);

				return ResponseEntity.created(null).body(new CurtidaEsporteDto(curtida, new EsporteHomeDto(esporteO),
						new UsuarioHomeDto(usuario.get())));
			}
		}

		return ResponseEntity.badRequest().build();
	}

}
