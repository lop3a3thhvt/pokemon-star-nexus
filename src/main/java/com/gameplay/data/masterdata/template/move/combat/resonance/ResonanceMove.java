package com.gameplay.data.masterdata.template.move.combat.resonance;

import com.gameplay.data.masterdata.template.move.combat.CombatMove;
import com.gameplay.data.masterdata.template.move.combat.pokemon.PokemonMove;
import com.utils.StringUtils;

public class ResonanceMove extends CombatMove {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String starPartnerId;
	private final PokemonMove movePrototype;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private ResonanceMove(Builder builder) {
		super(builder);
		this.starPartnerId = builder.starPartnerId;
		this.movePrototype = builder.movePrototype;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().length() - 3));
	}

	//	Star Partner Getter

	public PokemonMove getMovePrototype() {
		return movePrototype;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder extends CombatMove.Builder<Builder> {
		private final String starPartnerId;
		private final PokemonMove movePrototype;

		public Builder(String starPartnerId, int moveId, String moveCode, PokemonMove movePrototype) {
			super("RM_" + starPartnerId + movePrototype.getMoveId().substring(3) + String.format("%03d", moveId), moveCode, movePrototype);
			this.starPartnerId = StringUtils.upperSnakeCase(starPartnerId);
			this.movePrototype = movePrototype;
		}

		@Override
		public ResonanceMove build() {
			return new ResonanceMove(this);
		}
	}
}
