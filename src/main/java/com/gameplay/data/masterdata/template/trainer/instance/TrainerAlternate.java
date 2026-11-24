package com.gameplay.data.masterdata.template.trainer.instance;

import com.gameplay.data.masterdata.attribute.region.WorldRegion;
import com.gameplay.data.masterdata.attribute.trainer.TrainerClass;
import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.gameplay.data.masterdata.template.trainer.TrainerIdentity;
import com.utils.IdCodeKeyMap;
import com.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainerAlternate extends Trainer {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String trainerIdentityId;
	private final int trainerAlternateId;
	private final String trainerAlternateCode;
	private final WorldRegion affiliatedRegion;
	private final List<TrainerClass> trainerClassList;
	private final IdCodeKeyMap<Integer, TrainerMove> trainerMoves;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private TrainerAlternate(Builder builder) {
		super(builder);
		this.trainerIdentityId = builder.trainerIdentityId;
		this.trainerAlternateId = builder.trainerAlternateId;
		this.trainerAlternateCode = builder.trainerAlternateCode;
		this.affiliatedRegion = builder.affiliatedRegion;
		this.trainerClassList = List.copyOf(builder.trainerClassList);
		this.trainerMoves = builder.trainerMoves.build();
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public TrainerIdentity getTrainerIdentity() {
		return TrainerRegistry.getIdentityById(trainerIdentityId);
	}

	public int getTrainerAlternateIdNumber() {
		return trainerAlternateId;
	}

	public String getTrainerAlternateId() {
		return "00" + trainerIdentityId + String.format("%04d", trainerAlternateId);
	}

	public String getTrainerAlternateCode() {
		return trainerAlternateCode;
	}

	public WorldRegion getAffiliatedRegion() {
		return affiliatedRegion;
	}

	public List<TrainerClass> getTrainerClassList() {
		return trainerClassList;
	}

	public TrainerClass getTrainerClass(int ordinal) {
		if (ordinal <= 0 || ordinal > trainerClassList.size()) {
			throw new IllegalArgumentException("Trainer Class Ordinal is out of range.");
		}
		return trainerClassList.get(ordinal - 1);
	}

	public TrainerClass getTrainerClass() {
		return getTrainerClass(1);
	}

	public boolean hasTrainerClass(TrainerClass trainerClass) {
		return trainerClassList.contains(trainerClass);
	}

	public TrainerMove getMoveById(int id) {
		return trainerMoves.getById(id);
	}

	public TrainerMove getMoveByCode(String code) {
		return trainerMoves.getByCode(code);
	}

	public Collection<TrainerMove> getAllTrainerMoves() {
		return trainerMoves.getAll();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder extends Trainer.Builder<Builder> {
		private final String trainerIdentityId;
		private final int trainerAlternateId;
		private final String trainerAlternateCode;
		private WorldRegion affiliatedRegion;
		private final List<TrainerClass> trainerClassList = new ArrayList<>();
		private final IdCodeKeyMap.Builder<Integer, TrainerMove> trainerMoves = new IdCodeKeyMap.Builder<>();

		public Builder(String trainerIdentityId, String trainerIdentityCode, int trainerAlternateId, String trainerAlternateCode) {
			String trainerCode;
			if (trainerAlternateCode.equalsIgnoreCase("CASUAL")) {
				trainerCode = trainerIdentityCode;
			} else {
				trainerCode = trainerIdentityCode + "_" + trainerAlternateCode;
			}
			super("TRA_00" + trainerIdentityId + String.format("%04d", trainerAlternateId), trainerCode);

			this.trainerIdentityId = StringUtils.requireUpperSnakeCase(trainerIdentityId, "Trainer Alternate Id");
			this.trainerAlternateId = trainerAlternateId;
			this.trainerAlternateCode = StringUtils.requireUpperSnakeCase(trainerAlternateCode, "Trainer Alternate Code");
		}

		public Builder setAffiliatedRegion(String region) {
			WorldRegion affiliatedRegion = WorldRegion.fromCode(region);
			if (affiliatedRegion != null) {
				this.affiliatedRegion = affiliatedRegion;
			}
			return this;
		}

		public Builder setTrainerClasses(List<String> trainerClassList) {
			if (trainerClassList != null && !trainerClassList.isEmpty()) {
				for (String trainerClassCode : trainerClassList) {
					TrainerClass trainerClass = TrainerClass.fromCode(trainerClassCode);
					if (trainerClass != null && !this.trainerClassList.contains(trainerClass)) {
						this.trainerClassList.add(trainerClass);
					}
				}
			}
			return this;
		}

		public void addTrainerMove(TrainerMove trainerMove) {
			if (trainerMove != null) {
				this.trainerMoves.put(
						trainerMove.getMoveIdNo(),
						trainerMove.getMoveCode(),
						trainerMove);
			}
		}

		public TrainerAlternate build() {
			return new TrainerAlternate(this);
		}
	}
}
