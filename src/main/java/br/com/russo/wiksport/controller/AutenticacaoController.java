package br.com.russo.wiksport.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.russo.wiksport.config.MailService;
import br.com.russo.wiksport.config.security.TokenService;
import br.com.russo.wiksport.controller.dto.TokenDto;
import br.com.russo.wiksport.controller.dto.UsuarioCreateDto;
import br.com.russo.wiksport.controller.form.CreateForm;
import br.com.russo.wiksport.controller.form.LoginForm;
import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Value("${br.com.russo.address}")
	private String address;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	MailService mailService;

	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();

		try {
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping("/create")
	@Transactional
	public ResponseEntity<UsuarioCreateDto> criar(@RequestBody @Valid CreateForm form, UriComponentsBuilder uriBuilder,
			HttpServletRequest request) {
		Usuario usuario = form.converter();
		Optional<Usuario> jaCadastrado = usuarioRepository.findByEmail(usuario.getEmail());
		if (jaCadastrado.isPresent()) {
			return ResponseEntity.badRequest().body(new UsuarioCreateDto(jaCadastrado.get()));
		}
		usuarioRepository.save(usuario);
		String origin = URI.create(request.getRequestURL().toString()).getHost();
		String link = String.format("%s/auth/confirm?code=%s&origin=%s", address, usuario.getConfirmacaoString(),
				origin);
		mailService.enviarEmail(usuario.getEmail(), "Confirme seu cadastro",
				String.format("<h1><a href='%s'>Confirme seu email</a></h1>", link));
		URI uri = uriBuilder.path("/auth").build().toUri();

		return ResponseEntity.created(uri).body(new UsuarioCreateDto(usuario));
	}

	@GetMapping("/confirm")
	@Transactional
	ResponseEntity<?> redirect(@RequestParam String code, @RequestParam String origin) throws URISyntaxException {
		Optional<Usuario> optional = usuarioRepository.findByConfirmacaoString(code);
		if (optional.isPresent()) {
			Usuario usuario = usuarioRepository.getOne(optional.get().getId());
			usuario.setVerificado(true);
			usuario.setEditado(LocalDateTime.now());
			URI url = new URI(origin);
			HttpHeaders httpHeaders = new HttpHeaders();
		    httpHeaders.setLocation(url);
		    return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		}

		return ResponseEntity.badRequest().build();

	}

}
