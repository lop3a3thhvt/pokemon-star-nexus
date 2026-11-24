package com.gameplay.data.masterdata.template.move.combat;

import com.gameplay.data.masterdata.attribute.move.MoveTag;
import com.gameplay.data.masterdata.attribute.move.MoveTarget;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MoveMasteryRank {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	public static final int MAX_MOVE_MASTERY_RANK = 3;

	private final int moveMasteryRank;
	private final int basePower;
	private final double accuracy;
	private final double actionSpeed;
	private final int syncEtherCost;
	private final int stardustTuning;
	private final MoveTarget moveTarget;
	private final Set<MoveTag> moveTagSet;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	public MoveMasteryRank(Builder builder) {
		this.moveMasteryRank = builder.moveMasteryRank;
		this.basePower = builder.basePower;
		this.accuracy = builder.accuracy;
		this.actionSpeed = builder.actionSpeed;
		this.syncEtherCost = builder.syncEtherCost;
		this.stardustTuning = builder.stardustTuning;
		this.moveTarget = builder.moveTarget;
		this.moveTagSet = Collections.unmodifiableSet(EnumSet.copyOf(builder.moveTagSet));
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public int getMoveMasteryRank() {
		return moveMasteryRank;
	}

	public int getBasePower() {
		return basePower;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getActionSpeed() {
		return actionSpeed;
	}

	public int getSyncEtherCost() {
		return syncEtherCost;
	}

	public int getStardustTuning() {
		return stardustTuning;
	}

	public MoveTarget getMoveTarget() {
		return moveTarget;
	}

	public Set<MoveTag> getMoveTagSet() {
		return moveTagSet;
	}

	public boolean hasTag(MoveTag moveTag) {
		return moveTagSet.contains(moveTag);
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder {
		private final int moveMasteryRank;
		private int basePower;
		private double accuracy;
		private double actionSpeed;
		private int syncEtherCost;
		private int stardustTuning;
		private MoveTarget moveTarget;
		private final EnumSet<MoveTag> moveTagSet = EnumSet.noneOf(MoveTag.class);

		public Builder(int moveMasteryRank) {
			if (moveMasteryRank < 0 || moveMasteryRank > MAX_MOVE_MASTERY_RANK) {
				throw new IllegalArgumentException("Move Mastery Rank is out of range : " + moveMasteryRank);
			}
			this.moveMasteryRank = moveMasteryRank;
		}

		public Builder setData(int basePower, double accuracy, double actionSpeed, int syncEtherCost, int stardustTuning) {
			this.basePower = basePower;
			this.accuracy = accuracy;
			this.actionSpeed = actionSpeed;
			this.syncEtherCost = syncEtherCost;
			this.stardustTuning = stardustTuning;
			return this;
		}

		public Builder setTarget(String moveTarget) {
			MoveTarget target = MoveTarget.fromCode(moveTarget);
			if (target != null) {
				this.moveTarget = target;
			}
			return this;
		}

		public Builder setTags(List<String> moveTagList) {
			this.moveTagSet.clear();
			if (moveTagList != null) {
				for (String moveTag : moveTagList) {
					MoveTag tag = MoveTag.fromCode(moveTag);
					if (tag != null) {
						this.moveTagSet.add(tag);
					}
				}
			}
			return this;
		}

		public MoveMasteryRank build() {
			return new MoveMasteryRank(this);
		}
	}
}
