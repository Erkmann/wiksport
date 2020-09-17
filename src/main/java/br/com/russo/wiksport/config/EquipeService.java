package br.com.russo.wiksport.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.russo.wiksport.model.Equipes;
import br.com.russo.wiksport.repository.EquipesRepository;

@Service
public class EquipeService {

	@Autowired
	EquipesRepository equipeRepository;

	public Optional<Equipes> getEquipeNomeId(String name, Long id) {
		Optional<Equipes> equipe = equipeRepository.findById(id);
		Optional<Equipes> byNome = equipeRepository.findByNome(name);

		if (equipe.isPresent() && byNome.isPresent() && equipe.get().equals(byNome.get())) {
			return equipe;
		}

		return Optional.empty();
	}

}
