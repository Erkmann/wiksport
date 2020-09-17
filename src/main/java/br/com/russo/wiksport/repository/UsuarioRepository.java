package br.com.russo.wiksport.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByConfirmacaoString(String code);

}
