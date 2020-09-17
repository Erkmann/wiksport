package br.com.russo.wiksport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.russo.wiksport.model.Esportes;

public interface EsporteRepository extends JpaRepository<Esportes, Long>{

	Page<Esportes> findByNome(String name, Pageable paginacao);
		
}
