package br.com.russo.wiksport.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.russo.wiksport.controller.dto.AtletaHomeDto;
import br.com.russo.wiksport.controller.dto.EquipesHomeDto;
import br.com.russo.wiksport.controller.dto.EsporteHomeDto;
import br.com.russo.wiksport.controller.dto.HomeDto;
import br.com.russo.wiksport.controller.dto.LigasHomeDto;
import br.com.russo.wiksport.model.Atletas;
import br.com.russo.wiksport.model.Equipes;
import br.com.russo.wiksport.model.Esportes;
import br.com.russo.wiksport.model.Ligas;
import br.com.russo.wiksport.repository.AtletasRepository;
import br.com.russo.wiksport.repository.EquipesRepository;
import br.com.russo.wiksport.repository.EsporteRepository;
import br.com.russo.wiksport.repository.LigasRepository;

@RestController
@RequestMapping("/")
public class HomeController {

	@Autowired
	private EsporteRepository esporteRepository;
	@Autowired
	private LigasRepository ligasRepository;
	@Autowired
	private EquipesRepository equipeRepository;
	@Autowired
	private AtletasRepository atletaRepository;

	@GetMapping
	public ResponseEntity<HomeDto> home() {
		Random rand = new Random();

		List<Esportes> esportes = esporteRepository.findAll();
		List<Ligas> ligas = ligasRepository.findAll();
		List<Equipes> equipes = equipeRepository.findAll();
		List<Atletas> atletas = atletaRepository.findAll();

		System.out.println(esportes);
		System.out.println(ligas);
		System.out.println(equipes);
		System.out.println(atletas);

		if (esportes.size() <= 0 || ligas.size() <= 0 || equipes.size() <= 0 || atletas.size() <= 0) {
			return ResponseEntity.ok(new HomeDto(null, null, null, null));
		}

		Esportes esporte = esportes.get(rand.nextInt(esportes.size()));
		EsporteHomeDto esporteDto = new EsporteHomeDto(esporte.getId(), esporte.getNome(), esporte.getIcon());

		Ligas liga = ligas.get(rand.nextInt(ligas.size()));
		LigasHomeDto ligaDto = new LigasHomeDto(liga.getId(), liga.getNome(), liga.getIcon());

		Equipes equipe = equipes.get(rand.nextInt(equipes.size()));
		EquipesHomeDto equipeDto = new EquipesHomeDto(equipe.getId(), equipe.getNome(), equipe.getIcon());

		Atletas atleta = atletas.get(rand.nextInt(atletas.size()));
		AtletaHomeDto atletaDto = new AtletaHomeDto(atleta.getId(), atleta.getNome(), atleta.getIcon());

		return ResponseEntity.ok(new HomeDto(esporteDto, ligaDto, equipeDto, atletaDto));

	}

}
