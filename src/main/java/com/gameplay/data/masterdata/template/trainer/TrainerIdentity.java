package com.gameplay.data.masterdata.template.trainer;

import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.utils.IdCodeKeyMap;
import com.utils.StringUtils;

import java.util.Collection;

public class TrainerIdentity {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String trainerIdentityId;
	private final String trainerIdentityCode;
	private final IdCodeKeyMap<Integer, TrainerAlternate> trainerAlternateVariety;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private TrainerIdentity(Builder builder) {
		this.trainerIdentityId = builder.trainerIdentityId;
		this.trainerIdentityCode = builder.trainerIdentityCode;
		this.trainerAlternateVariety = builder.trainerAlternateVariety.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public String getTrainerIdentityId() {
		return trainerIdentityId;
	}

	public String getTrainerIdentityCode() {
		return trainerIdentityCode;
	}

	public TrainerAlternate getAlternateById(int id) {
		return trainerAlternateVariety.getById(id);
	}

	public TrainerAlternate getAlternateByCode(String code) {
		return trainerAlternateVariety.getByCode(code);
	}

	public Collection<TrainerAlternate> getAllAlternates() {
		return trainerAlternateVariety.getAll();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final String trainerIdentityId;
		private final String trainerIdentityCode;
		private final IdCodeKeyMap.Builder<Integer, TrainerAlternate> trainerAlternateVariety = new IdCodeKeyMap.Builder<>();

		public Builder(int regionId, int trainerIdentityId, String trainerIdentityCode) {
			this.trainerIdentityId = "TRI_" + String.format("%03d%05d", regionId, trainerIdentityId);
			this.trainerIdentityCode = StringUtils.requireUpperSnakeCase(trainerIdentityCode, "Trainer Identity Code");
		}

		public Builder(String trainerIdentityId, String trainerIdentityCode) {
			this.trainerIdentityId = StringUtils.requireUpperSnakeCase(trainerIdentityId, "Trainer Identity Id");
			this.trainerIdentityCode = StringUtils.requireUpperSnakeCase(trainerIdentityCode, "Trainer Identity Code");
		}

		public void addAlternate(TrainerAlternate trainerAlternate) {
			if (trainerAlternate != null) {
				this.trainerAlternateVariety.put(
						trainerAlternate.getTrainerAlternateIdNumber(),
						trainerAlternate.getTrainerAlternateCode(),
						trainerAlternate);
			}
		}

		public TrainerIdentity build() {
			return new TrainerIdentity(this);
		}
	}
}
