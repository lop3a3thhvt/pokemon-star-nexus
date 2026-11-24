package com.gameplay.data.masterdata.template.move.tactic.trainer;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.move.tactic.TacticMove;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.utils.StringUtils;

public class TrainerMove extends TacticMove {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String trainerAlternateId;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private TrainerMove(Builder builder) {
		super(builder);
		this.trainerAlternateId = builder.trainerAlternateId;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public TrainerAlternate getTrainerAlternate() {
		return TrainerRegistry.getAlternateById(trainerAlternateId);
	}

	@Override
	public int getMoveIdNo() {
		return Integer.parseInt(getMoveId().substring(getMoveId().length() - 4));
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder extends TacticMove.Builder<Builder> {
		private final String trainerAlternateId;

		public Builder(String trainerAlternateId, int moveId, String moveCode) {
			super("TM_" + trainerAlternateId + String.format("%04d", moveId), moveCode);
			this.trainerAlternateId = StringUtils.upperSnakeCase(trainerAlternateId);
		}

		@Override
		public TrainerMove build() {
			return new TrainerMove(this);
		}
	}

}
