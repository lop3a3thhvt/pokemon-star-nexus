package com.gameplay.data.masterdata.template.move;

import com.gameplay.data.masterdata.attribute.move.MoveCategory;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.utils.StringUtils;

public abstract class Move {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String moveId;
	private final String moveCode;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected Move(Builder<?> builder) {
		this.moveId = builder.moveId;
		this.moveCode = builder.moveCode;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public String getMoveId() {
		return moveId;
	}

	public abstract int getMoveIdNo();

	public String getMoveCode() {
		return moveCode;
	}

	public abstract PokemonType getMoveType();

	public abstract MoveCategory getMoveCategory();

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public abstract static class Builder<B extends Builder<B>> {
		private final String moveId;
		private final String moveCode;

		protected Builder(String moveId, String moveCode) {
			this.moveId = StringUtils.requireUpperSnakeCase(moveId, "Move Id");
			this.moveCode = StringUtils.requireUpperSnakeCase(moveCode, "Move Code");
		}

		@SuppressWarnings("unchecked")
		protected B self() {
			return (B) this;
		}

		public abstract Move build();
	}
}
