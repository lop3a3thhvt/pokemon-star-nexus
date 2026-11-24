package com.gameplay.data.masterdata.template.trainer;

import com.utils.StringUtils;

public abstract class Trainer {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String trainerId;
	private final String trainerCode;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected Trainer(Builder<?> builder) {
		this.trainerId = builder.trainerId;
		this.trainerCode = builder.trainerCode;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public String getTrainerId() {
		return trainerId;
	}

	public String getTrainerIdWithoutCategoryLabel() {
		return trainerId.substring(trainerId.indexOf('_') + 1);
	}

	public String getTrainerCode() {
		return trainerCode;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public abstract static class Builder<B extends Builder<B>> {
		private final String trainerId;
		private final String trainerCode;

		protected Builder(String trainerId, String trainerCode) {
			this.trainerId = StringUtils.requireUpperSnakeCase(trainerId, "Trainer Id");
			this.trainerCode = StringUtils.requireUpperSnakeCase(trainerCode, "Trainer Code");
		}

		public abstract Trainer build();
	}
}
