package br.com.russo.wiksport.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Ligas;

public interface LigasRepository extends JpaRepository<Ligas, Long> {

	Page<Ligas> findByNome(String name, Pageable paginacao);
	Optional<Ligas> findByNome(String name);

}
