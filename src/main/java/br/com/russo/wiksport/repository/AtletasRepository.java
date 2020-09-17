package br.com.russo.wiksport.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Atletas;

public interface AtletasRepository extends JpaRepository<Atletas, Long>{

	Page<Atletas> findByNome(String name, Pageable paginacao);
	Optional<Atletas> findByNome(String name);

}
