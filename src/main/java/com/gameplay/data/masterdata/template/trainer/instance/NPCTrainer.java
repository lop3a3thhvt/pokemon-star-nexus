package com.gameplay.data.masterdata.template.trainer.instance;

import com.gameplay.data.masterdata.attribute.trainer.NPCTrainerClass;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.utils.StringUtils;

public class NPCTrainer extends Trainer {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final NPCTrainerClass trainerClass;
	private final String trainerName;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private NPCTrainer(Builder builder) {
		super(builder);
		this.trainerClass = builder.trainerClass;
		this.trainerName = builder.trainerName;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public NPCTrainerClass getTrainerClass() {
		return trainerClass;
	}

	public String getTrainerName() {
		return trainerName;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder extends Trainer.Builder<Builder> {
		private final NPCTrainerClass trainerClass;
		private final String trainerName;

		public Builder(int trainerId, NPCTrainerClass npcTrainerClass, String trainerName) {
			super("NPC_10" + npcTrainerClass.getTrainerClassId() + String.format("%04d", trainerId), npcTrainerClass.name() + "_" + trainerName);
			this.trainerClass = npcTrainerClass;
			this.trainerName = StringUtils.requireUpperSnakeCase(trainerName, "NPC Trainer Name");
		}

		public NPCTrainer build() {
			return new NPCTrainer(this);
		}
	}
}
