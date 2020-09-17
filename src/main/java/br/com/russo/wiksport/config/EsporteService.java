package br.com.russo.wiksport.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.repository.EsporteRepository;

@Service
public class EsporteService {

	@Autowired
	EsporteRepository esporteRepository;

	public Optional<Esportes> getEsporteNomeId(String name, Long id) {
		Optional<Esportes> esporte = esporteRepository.findById(id);
		Optional<Esportes> byNome = esporteRepository.findByNome(name);

		if (esporte.isPresent() && byNome.isPresent() && esporte.get().equals(byNome.get())) {
			return esporte;
		}

		return Optional.empty();
	}

}
