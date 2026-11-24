package com.gameplay.data.masterdata.data.master.dto.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.gameplay.data.masterdata.template.pokemon.form.gigantamax.GigantamaxForm;
import com.gameplay.data.masterdata.template.pokemon.form.mega.MegaEvolutionForm;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonFormDTO(
		@JsonProperty("id")
		int formId,
		@JsonProperty("code")
		String formCode,
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
		Double height,
		@JsonProperty("weight")
		Double weight,
		@JsonProperty("gender_appearance_difference")
		Boolean genderAppearanceDifference,
		@JsonProperty("mega_origin")
		Integer megaOrigin,
		@JsonProperty("mega_boost")
		List<Integer> megaBoost,
		@JsonProperty("pre_gigantamax")
		Integer preGigantamax
) {
	public PokemonFormDTO {
		formCode = (formCode != null) ? formCode : "BASE";
		formCode = StringUtils.requireUpperSnakeCase(formCode, "Pokemon Form Code");

		if (megaOrigin != null && preGigantamax != null) {
			throw new IllegalArgumentException("A Pokemon Form cannot be part of 2 or more Gimmick : " + formCode);
		}

		if (preGigantamax != null) {
			weight = (double)-1;
		}

		if (genderAppearanceDifference == null) {
			genderAppearanceDifference = false;
		}

//		if (megaOrigin != null && megaBoost.size() != 5) {
//			throw new IllegalArgumentException("Missing Mega Boost Stat.");
//		}
	}

	private static <T> T inherit(T own, T inherited) {
		return own != null ? own : inherited;
	}

	public PokemonForm create(PokemonSpeciesDTO pokemonSpeciesDTO) {
		PokemonForm.Builder builder = new PokemonForm.Builder(
				pokemonSpeciesDTO.pokedexNumber(), pokemonSpeciesDTO.species(),
				formId, formCode)
				.setCategory(inherit(pokemonCategory, pokemonSpeciesDTO.pokemonCategory()))
				.setType(inherit(pokemonTypeList, pokemonSpeciesDTO.pokemonTypeList()))
				.setEggGroups(inherit(eggGroupList, pokemonSpeciesDTO.eggGroupList()))
				.setAppearance(inherit(color, pokemonSpeciesDTO.color()), inherit(shape, pokemonSpeciesDTO.shape()), genderAppearanceDifference)
				.setDimension(inherit(height, pokemonSpeciesDTO.height()), inherit(weight, pokemonSpeciesDTO.weight()));

		if (megaOrigin != null) {
			MegaEvolutionForm.Builder megaBuilder = new MegaEvolutionForm.Builder(pokemonSpeciesDTO.pokedexNumber(), megaOrigin)
					.setMegaBoost(megaBoost);

			builder.setMegaEvolution(megaBuilder.build());
		}

		if (preGigantamax != null) {
			GigantamaxForm.Builder gmaxBuilder = new GigantamaxForm.Builder(pokemonSpeciesDTO.pokedexNumber(), preGigantamax);

			builder.setGigantamax(gmaxBuilder.build());
		}

		return builder.build();
	}
}
