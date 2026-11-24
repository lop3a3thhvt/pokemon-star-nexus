package com.gameplay.data.masterdata.template.pokemon;

import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.utils.IdCodeKeyMap;
import com.utils.StringUtils;

import java.util.Collection;

public class PokemonSpecies {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int pokedexNumber;
	private final String speciesCode;
	private final IdCodeKeyMap<Integer, PokemonForm> pokemonForms;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private PokemonSpecies(Builder builder) {
		this.pokedexNumber = builder.pokedexNumber;
		this.speciesCode = builder.speciesCode;
		this.pokemonForms = builder.pokemonForms.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getPokedexNumber() {
		return pokedexNumber;
	}

	public String getSpeciesCode() {
		return speciesCode;
	}

	public PokemonForm getFormById(int id) {
		return pokemonForms.getById(id);
	}

	public PokemonForm getFormByCode(String code) {
		return pokemonForms.getByCode(code);
	}

	public Collection<PokemonForm> getAllForms() {
		return pokemonForms.getAll();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final int pokedexNumber;
		private final String speciesCode;
		private final IdCodeKeyMap.Builder<Integer, PokemonForm> pokemonForms = new IdCodeKeyMap.Builder<>();

		public Builder(int pokedexNumber, String speciesCode) {
			this.pokedexNumber = pokedexNumber;
			this.speciesCode = StringUtils.requireUpperSnakeCase(speciesCode, "Species Code");
		}

		public void addPokemonForm(PokemonForm pokemonForm) {
			if (pokemonForm != null) {
				this.pokemonForms.put(pokemonForm.getPokemonFormId(), pokemonForm.getPokemonFormCode(), pokemonForm);
			}
		}

		public PokemonSpecies build() {
			return new PokemonSpecies(this);
		}
	}
}
