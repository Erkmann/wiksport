package br.com.russo.wiksport.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.russo.wiksport.model.Ligas;
import br.com.russo.wiksport.repository.LigasRepository;

@Service
public class LigaService {

	@Autowired
	LigasRepository ligaRepository;

	public Optional<Ligas> getLigaNomeId(String name, Long id) {
		Optional<Ligas> liga = ligaRepository.findById(id);
		Optional<Ligas> byNome = ligaRepository.findByNome(name);

		if (liga.isPresent() && byNome.isPresent() && liga.get().equals(byNome.get())) {
			return liga;
		}

		return Optional.empty();
	}

}
