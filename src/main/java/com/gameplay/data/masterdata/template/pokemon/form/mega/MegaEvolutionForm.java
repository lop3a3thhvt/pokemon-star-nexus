package com.gameplay.data.masterdata.template.pokemon.form.mega;

import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.template.pokemon.form.PokemonForm;
import com.utils.StringUtils;

import java.util.List;

public class MegaEvolutionForm {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String megaOrigin;
	private final String megaStone;
	private final List<Integer> megaBoost;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private MegaEvolutionForm(Builder builder) {
		this.megaOrigin = builder.megaOrigin;
		this.megaStone = builder.megaStone;
		this.megaBoost = List.copyOf(builder.megaBoost);
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public PokemonForm getMegaOrigin() {
		return PokemonRegistry.getFormById(megaOrigin);
	}

	public String getMegaStone() {
		return megaStone;
	}

	public List<Integer> getMegaBoost() {
		return megaBoost;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final String megaOrigin;
		private String megaStone;
		private List<Integer> megaBoost;

		public Builder(int pokedexNumber, int megaOrigin) {
			this.megaOrigin = String.format("%08d%04d", pokedexNumber, megaOrigin);
		}

		public Builder setMegaStone(String megaStone) {
			if (megaStone != null) {
				this.megaStone = StringUtils.upperSnakeCase(megaStone);
			}
			return this;
		}

		public Builder setMegaBoost(List<Integer> megaBoost) {
			this.megaBoost = List.copyOf(megaBoost);
			return this;
		}

		public MegaEvolutionForm build() {
			return new MegaEvolutionForm(this);
		}
	}
}
