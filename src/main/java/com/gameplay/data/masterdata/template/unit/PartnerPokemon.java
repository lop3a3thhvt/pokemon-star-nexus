package com.gameplay.data.masterdata.template.unit;

import com.gameplay.data.masterdata.attribute.pokemon.gender.PokemonGender;
import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.pokemon.PokemonSpecies;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;

public class PartnerPokemon {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final PokemonSpecies pokemonSpecies;
	private final PokemonForm defaultForm;
	private final PokemonGender gender;
	private final boolean isShiny;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PartnerPokemon(Builder builder) {
		this.pokemonSpecies = builder.pokemonSpecies;
		this.defaultForm = builder.defaultForm;
		this.gender = builder.gender;
		this.isShiny = builder.isShiny;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public PokemonSpecies getPokemonSpecies() {
		return pokemonSpecies;
	}

	public PokemonForm getDefaultForm() {
		return defaultForm;
	}

	public PokemonGender getGender() {
		return gender;
	}

	public boolean isShiny() {
		return isShiny;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final PokemonSpecies pokemonSpecies;
		private final PokemonForm defaultForm;
		private final PokemonGender gender;
		private final boolean isShiny;

		public Builder(String defaultFormCode, String gender, boolean isShiny) {
			PokemonForm pokemonForm = PokemonRegistry.getFormByCode(defaultFormCode);
			this.pokemonSpecies = pokemonForm.getPokemonSpecies();
			this.defaultForm = pokemonForm;
			this.gender = PokemonGender.fromCode(gender);
			this.isShiny = isShiny;
		}

		public PartnerPokemon build() {
			return new PartnerPokemon(this);
		}
	}
}
