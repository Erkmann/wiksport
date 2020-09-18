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

import br.com.russo.wiksport.config.LigaService;
import br.com.russo.wiksport.controller.dto.CurtidaLigaDto;
import br.com.russo.wiksport.controller.dto.LigaDto;
import br.com.russo.wiksport.controller.dto.LigasHomeDto;
import br.com.russo.wiksport.controller.dto.UsuarioHomeDto;
import br.com.russo.wiksport.controller.form.CurtidaLigaForm;
import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Ligas;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.CurtidaRepository;
import br.com.russo.wiksport.repository.LigasRepository;
import br.com.russo.wiksport.repository.UsuarioRepository;

@RestController
@RequestMapping("/ligas")
public class LigaController {

	@Autowired
	LigasRepository ligaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CurtidaRepository curtidaRepository;

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
	
	@PostMapping("/curtir")
	@Transactional
	public ResponseEntity<CurtidaLigaDto> curtir(@RequestBody @Valid CurtidaLigaForm form) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		UserDetails user = (UserDetails) principal;
		Long id = form.getLigaId();

		Optional<Ligas> liga = ligaRepository.findById(id);

		if (liga.isPresent()) {
			Ligas ligaO = liga.get();
			Optional<Usuario> usuario = usuarioRepository.findByEmail(user.getUsername());
			if (usuario.isPresent()) {
				Optional<List<Curtida>> curtidasUser = curtidaRepository.findByUsuario(usuario.get());
				if (curtidasUser.isPresent()) {
					for (Curtida c : curtidasUser.get()) {
						if (ligaO.getCurtidas().contains(c)) {
							List<Curtida> novasCurtidas = ligaO.getCurtidas();
							novasCurtidas.remove(c);
							ligaO.setCurtidas(novasCurtidas);
							return ResponseEntity.ok().build();
						}
					}
				}

				Curtida curtida = new Curtida();
				curtida.setUsuario(usuario.get());
				curtidaRepository.save(curtida);
				System.out.println(curtida);
				List<Curtida> curtidas = ligaO.getCurtidas();
				curtidas.add(curtida);
				ligaO.setCurtidas(curtidas);

				return ResponseEntity.created(null).body(
						new CurtidaLigaDto(curtida, new LigasHomeDto(ligaO), new UsuarioHomeDto(usuario.get())));
			}
		}

		return ResponseEntity.badRequest().build();
	}

}
