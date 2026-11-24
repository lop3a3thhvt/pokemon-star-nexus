package com.gameplay.data.masterdata.template.move.tactic.item;

import com.gameplay.data.masterdata.template.move.tactic.TacticMove;

public class ItemMove extends TacticMove {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private ItemMove(Builder builder) {
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

	public static class Builder extends TacticMove.Builder<Builder> {
		public Builder(int moveId, String moveCode) {
			super("IM_" + String.format("%08d", moveId), moveCode);
		}

		@Override
		public ItemMove build() {
			return new ItemMove(this);
		}
	}
}
