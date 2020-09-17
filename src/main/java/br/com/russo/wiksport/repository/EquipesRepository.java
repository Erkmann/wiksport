package br.com.russo.wiksport.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Equipes;

public interface EquipesRepository extends JpaRepository<Equipes, Long> {

	Page<Equipes> findByNome(String name, Pageable paginacao);
	Optional<Equipes> findByNome(String name);

}
