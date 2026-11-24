package com.gameplay.data.masterdata.template.move.combat;

import com.gameplay.data.masterdata.attribute.move.MoveCategory;
import com.gameplay.data.masterdata.attribute.move.MoveTag;
import com.gameplay.data.masterdata.attribute.move.MoveTarget;
import com.gameplay.data.masterdata.attribute.move.MoveTier;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.move.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class CombatMove extends Move {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final PokemonType moveType;
	private final MoveCategory moveCategory;
	private final MoveTier moveTier;
	private final List<MoveMasteryRank> moveMasteryRankList;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected CombatMove(Builder<?> builder) {
		super(builder);
		this.moveType = builder.moveType;
		this.moveCategory = builder.moveCategory;
		this.moveTier = builder.moveTier;
		this.moveMasteryRankList = List.copyOf(builder.moveMasteryRankList);
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	@Override
	public PokemonType getMoveType() {
		return moveType;
	}

	@Override
	public MoveCategory getMoveCategory() {
		return moveCategory;
	}

	public MoveTier getMoveTier() {
		return moveTier;
	}

	public List<MoveMasteryRank> getMoveMasteryRankList() {
		return moveMasteryRankList;
	}

	public MoveMasteryRank getMoveMasteryRank(int moveMasteryRank) {
		if (moveMasteryRankList.isEmpty() || moveMasteryRank <= 0 || moveMasteryRank > moveMasteryRankList.size()) {
			throw new IllegalArgumentException("Move Mastery Rank " + moveMasteryRank + " does not exist.");
		}

		return moveMasteryRankList.get(moveMasteryRank - 1);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Move Mastery Rank Data Getters																					*/

	public int getBasePower(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getBasePower();
	}

	public double getAccuracy(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getAccuracy();
	}

	public double getActionSpeed(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getActionSpeed();
	}

	public int getSyncEtherCost(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getSyncEtherCost();
	}

	public int getStardustTuning(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getStardustTuning();
	}

	public MoveTarget getMoveTarget(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getMoveTarget();
	}

	public Set<MoveTag> getMoveTagSet(int moveMasteryRank) {
		return getMoveMasteryRank(moveMasteryRank).getMoveTagSet();
	}

	public boolean hasTag(int moveMasteryRank, MoveTag moveTag) {
		return getMoveMasteryRank(moveMasteryRank).hasTag(moveTag);
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Move Mastery Rank Data Base Getters																				*/

	public int getBasePower() {
		return getMoveMasteryRank(1).getBasePower();
	}

	public double getAccuracy() {
		return getMoveMasteryRank(1).getAccuracy();
	}

	public double getActionSpeed() {
		return getMoveMasteryRank(1).getActionSpeed();
	}

	public int getSyncEtherCost() {
		return getMoveMasteryRank(1).getSyncEtherCost();
	}

	public int getStardustTuning() {
		return getMoveMasteryRank(1).getStardustTuning();
	}

	public MoveTarget getMoveTarget() {
		return getMoveMasteryRank(1).getMoveTarget();
	}

	public Set<MoveTag> getMoveTagSet() {
		return getMoveMasteryRank(1).getMoveTagSet();
	}

	public boolean hasTag(MoveTag moveTag) {
		return getMoveMasteryRank(1).hasTag(moveTag);
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Builder																											*/

	public abstract static class Builder<B extends Builder<B>> extends Move.Builder<B> {
		private PokemonType moveType;
		private MoveCategory moveCategory;
		private MoveTier moveTier;
		private final List<MoveMasteryRank> moveMasteryRankList = new ArrayList<>();

		protected Builder(String moveId, String moveCode) {
			super(moveId, moveCode);
		}

		protected Builder(String moveId, String moveCode, CombatMove movePrototype) {
			super(moveId, moveCode);
			this.moveType = movePrototype.moveType;
			this.moveCategory = movePrototype.moveCategory;
			this.moveTier = movePrototype.moveTier;
		}

		public B setAttribute(String moveType, String moveCategory, int moveTier) {
			PokemonType type = PokemonType.fromCode(moveType);
			MoveCategory category = MoveCategory.fromCode(moveCategory);
			MoveTier tier = MoveTier.fromId(moveTier);
			if (type != null && category != null && tier != null) {
				this.moveType = type;
				this.moveCategory = category;
				this.moveTier = tier;
			}
			return self();
		}

		public void addMoveMasteryRank(MoveMasteryRank moveMasteryRank) {
			if (moveMasteryRank == null) {
				return;
			}

			int masteryRank = moveMasteryRank.getMoveMasteryRank();

			int currentPosition = 0;
			while (currentPosition < moveMasteryRankList.size() && moveMasteryRankList.get(currentPosition).getMoveMasteryRank() < masteryRank) {
				currentPosition++;
			}

			if (currentPosition < moveMasteryRankList.size() && moveMasteryRankList.get(currentPosition).getMoveMasteryRank() == masteryRank) {
				this.moveMasteryRankList.set(currentPosition, moveMasteryRank);
			} else {
				this.moveMasteryRankList.add(currentPosition, moveMasteryRank);
			}
		}

		@Override
		public abstract CombatMove build();
	}
}
