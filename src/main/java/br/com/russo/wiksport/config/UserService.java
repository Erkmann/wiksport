package br.com.russo.wiksport.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.com.russo.wiksport.model.Usuario;
import br.com.russo.wiksport.repository.UsuarioRepository;

@Service
public class UserService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Boolean isLogged() {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			return true;
		}
		return false;
	}

	public Usuario getLoggedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) principal;
		Optional<Usuario> usuario = usuarioRepository.findByEmail(user.getUsername());
		if (usuario.isPresent()) {
			return usuario.get();
		}

		return null;
	}

}
