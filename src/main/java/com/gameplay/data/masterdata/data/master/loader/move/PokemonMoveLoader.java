package com.gameplay.data.masterdata.data.master.loader.move;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.data.master.dto.move.combat.PokemonMoveDTO;
import com.gameplay.data.masterdata.data.master.registry.move.combat.PokemonMoveRegistry;
import com.gameplay.data.masterdata.template.move.combat.pokemon.PokemonMove;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public final class PokemonMoveLoader {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Loader Config																								*/

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/move/pokemon/series-";

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PokemonMoveLoader() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Loader																											*/

	private static void loadBySeries(int series) {
		String resource = PATH + series + ".json";

		try (InputStream inputStream = PokemonMoveLoader.class.getClassLoader().getResourceAsStream(resource)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Cannot Load Move From Series " + series + " !");
			}

			List<PokemonMoveDTO> pokemonMoveDTOList = MAPPER.readValue(inputStream, new TypeReference<List<PokemonMoveDTO>>() {});

			for (PokemonMoveDTO pokemonMoveDTO : pokemonMoveDTOList) {
				PokemonMove pokemonMove = pokemonMoveDTO.create();
				PokemonMoveRegistry.register(pokemonMove);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + resource, e);
		}
	}

	public static void loadAll() {
		for (int series = 1; series <= 9; ++series) {
			loadBySeries(series);
		}
	}
}
