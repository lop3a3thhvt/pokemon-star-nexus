package com.gameplay.data.masterdata.template.move.combat.pokemon;

import com.gameplay.data.masterdata.template.move.combat.CombatMove;

public class PokemonMove extends CombatMove {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private PokemonMove(Builder builder) {
		super(builder);
	}
	/*------------------------------------------------------------------------------------------------------------------
	2.	Getters																											*/

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().indexOf('_') + 1));
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Builder																											*/

	public static class Builder extends CombatMove.Builder<Builder> {
		public Builder(int moveId, String moveCode) {
			super("PM_" + String.format("%08d", moveId), moveCode);
		}

		@Override
		public PokemonMove build() {
			return new PokemonMove(this);
		}
	}

}
