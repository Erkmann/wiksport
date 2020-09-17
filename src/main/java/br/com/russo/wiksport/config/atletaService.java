package br.com.russo.wiksport.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.russo.wiksport.model.Atletas;
import br.com.russo.wiksport.repository.AtletasRepository;

@Service
public class atletaService {

	@Autowired
	AtletasRepository atletaRepository;

	public Optional<Atletas> getAtletaNomeId(String name, Long id) {
		Optional<Atletas> atleta = atletaRepository.findById(id);
		Optional<Atletas> byNome = atletaRepository.findByNome(name);

		if (atleta.isPresent() && byNome.isPresent() && atleta.get().equals(byNome.get())) {
			return atleta;
		}

		return Optional.empty();
	}

	
}
