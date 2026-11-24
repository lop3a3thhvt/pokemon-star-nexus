package com.gameplay.data.masterdata.template.pokemon.form.gigantamax;

import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;

public class GigantamaxForm {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String preGigantamax;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private GigantamaxForm(Builder builder) {
		this.preGigantamax = builder.preGigantamax;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public PokemonForm getPreGigantamax() {
		return PokemonRegistry.getFormById(preGigantamax);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final String preGigantamax;

		public Builder(int pokedexNumber, int preGigantamax) {
			this.preGigantamax = String.format("%08d%04d", pokedexNumber, preGigantamax);
		}

		public GigantamaxForm build() {
			return new GigantamaxForm(this);
		}
	}
}
