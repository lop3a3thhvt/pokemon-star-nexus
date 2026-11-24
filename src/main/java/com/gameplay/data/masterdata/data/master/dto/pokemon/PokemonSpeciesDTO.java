package com.gameplay.data.masterdata.data.master.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.pokemon.PokemonSpecies;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonSpeciesDTO(
		@JsonProperty("pokedex_number")
		int pokedexNumber,
		@JsonProperty("species")
		String species,
		@JsonProperty("category")
		String pokemonCategory,
		@JsonProperty("types")
		List<String> pokemonTypeList,
		@JsonProperty("egg_groups")
		List<String> eggGroupList,
		@JsonProperty("color")
		String color,
		@JsonProperty("shape")
		String shape,
		@JsonProperty("height")
		double height,
		@JsonProperty("weight")
		double weight,
		@JsonProperty("forms")
		List<PokemonFormDTO> pokemonFormDTOList
) {
	public PokemonSpeciesDTO {
		species = StringUtils.requireUpperSnakeCase(species, "Pokemon Species");
		pokemonCategory = StringUtils.requireUpperSnakeCase(pokemonCategory, "Pokemon Category");
		pokemonTypeList = (pokemonTypeList != null) ? List.copyOf(pokemonTypeList) : List.of("TYPELESS");
		eggGroupList = (eggGroupList != null) ? List.copyOf(eggGroupList) : List.of("NO_EGGS_DISCOVERED");
		color = StringUtils.requireUpperSnakeCase(color, "Body Color");
		shape = StringUtils.requireUpperSnakeCase(shape, "Body Shape");
		pokemonFormDTOList = (pokemonFormDTOList != null) ? List.copyOf(pokemonFormDTOList) : List.of();
	}

	public PokemonSpecies create() {
		PokemonSpecies.Builder builder = new PokemonSpecies.Builder(pokedexNumber, species);

		for (PokemonFormDTO pokemonFormDTO : pokemonFormDTOList) {
			builder.addPokemonForm(pokemonFormDTO.create(this));
		}

		return builder.build();
	}
}
