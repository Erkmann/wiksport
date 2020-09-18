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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.russo.wiksport.config.AtletaService;
import br.com.russo.wiksport.controller.dto.AtletaDto;
import br.com.russo.wiksport.controller.dto.AtletaHomeDto;
import br.com.russo.wiksport.controller.dto.CurtidaAtletaDto;
import br.com.russo.wiksport.controller.dto.UsuarioHomeDto;
import br.com.russo.wiksport.controller.form.CurtidaAtletaForm;
import br.com.russo.wiksport.model.Atletas;
import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.AtletasRepository;
import br.com.russo.wiksport.repository.UsuarioRepository;
import br.com.russo.wiksport.repository.CurtidaRepository;

@RestController
@RequestMapping("/atletas")
public class AtletaController {

	@Autowired
	AtletasRepository atletaRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	CurtidaRepository curtidaRepository;

	@Autowired
	AtletaService atletaService;

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

	@PostMapping("/curtir")
	@Transactional
	public ResponseEntity<CurtidaAtletaDto> curtir(@RequestBody @Valid CurtidaAtletaForm form) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		UserDetails user = (UserDetails) principal;
		Long id = form.getAtletaId();

		Optional<Atletas> atleta = atletaRepository.findById(id);

		if (atleta.isPresent()) {
			Atletas atletaO = atleta.get();
			Optional<Usuario> usuario = usuarioRepository.findByEmail(user.getUsername());
			if (usuario.isPresent()) {
				Optional<List<Curtida>> curtidasUser = curtidaRepository.findByUsuario(usuario.get());
				if (curtidasUser.isPresent()) {
					for (Curtida c : curtidasUser.get()) {
						if (atletaO.getCurtidas().contains(c)) {
							List<Curtida> novasCurtidas = atletaO.getCurtidas();
							novasCurtidas.remove(c);
							atletaO.setCurtidas(novasCurtidas);
							return ResponseEntity.ok().build();
						}
					}
				}

				Curtida curtida = new Curtida();
				curtida.setUsuario(usuario.get());
				curtidaRepository.save(curtida);
				System.out.println(curtida);
				List<Curtida> curtidas = atletaO.getCurtidas();
				curtidas.add(curtida);
				atletaO.setCurtidas(curtidas);

				return ResponseEntity.created(null).body(
						new CurtidaAtletaDto(curtida, new AtletaHomeDto(atletaO), new UsuarioHomeDto(usuario.get())));
			}
		}

		return ResponseEntity.badRequest().build();
	}

}
