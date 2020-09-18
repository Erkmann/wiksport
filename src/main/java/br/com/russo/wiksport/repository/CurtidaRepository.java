package br.com.russo.wiksport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Curtida;
import br.com.russo.wiksport.model.Usuario;

public interface CurtidaRepository extends JpaRepository<Curtida, Long>{

	Optional<List<Curtida>> findByUsuario(Usuario usuario);
	
}
