package com.gameplay.data.masterdata.template.move.tactic;

import com.gameplay.data.masterdata.attribute.move.MoveCategory;
import com.gameplay.data.masterdata.attribute.move.MoveTag;
import com.gameplay.data.masterdata.attribute.move.MoveTarget;
import com.gameplay.data.masterdata.attribute.type.PokemonType;
import com.gameplay.data.masterdata.template.move.Move;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class TacticMove extends Move {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final int tacticPoint;
	private final int syncEtherCost;
	private final int stardustTuning;
	private final MoveTarget moveTarget;
	private final Set<MoveTag> moveTagSet;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected TacticMove(Builder<?> builder) {
		super(builder);
		this.tacticPoint = builder.tacticPoint;
		this.syncEtherCost = builder.syncEtherCost;
		this.stardustTuning = builder.stardustTuning;
		this.moveTarget = builder.moveTarget;
		this.moveTagSet = Collections.unmodifiableSet(EnumSet.copyOf(builder.moveTagSet));
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	@Override
	public PokemonType getMoveType() {
		return PokemonType.TYPELESS;
	}

	@Override
	public MoveCategory getMoveCategory() {
		return MoveCategory.STATUS;
	}

	public int getTacticPoint() {
		return tacticPoint;
	}

	public int getStardustTuning() {
		return stardustTuning;
	}

	public int getSyncEtherCost() {
		return syncEtherCost;
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

	public abstract static class Builder<B extends Builder<B>> extends Move.Builder<B> {
		private int tacticPoint;
		private int syncEtherCost;
		private int stardustTuning;
		private MoveTarget moveTarget;
		private final EnumSet<MoveTag> moveTagSet = EnumSet.noneOf(MoveTag.class);

		protected Builder(String moveId, String moveCode) {
			super(moveId, moveCode);
		}

		public B setData(int tacticPoint, int syncEtherCost, int stardustTuning) {
			this.tacticPoint = tacticPoint;
			this.syncEtherCost = syncEtherCost;
			this.stardustTuning = stardustTuning;
			return self();
		}

		public B setTarget(String moveTarget) {
			MoveTarget target = MoveTarget.fromCode(moveTarget);
			if (target != null) {
				this.moveTarget = target;
			}
			return self();
		}

		public B setTags(List<String> moveTagList) {
			this.moveTagSet.clear();
			if (moveTagList != null) {
				for (String moveTag : moveTagList) {
					MoveTag tag = MoveTag.fromCode(moveTag);
					if (tag != null) {
						this.moveTagSet.add(tag);
					}
				}
			}
			return self();
		}

		@Override
		public abstract TacticMove build();
	}
}
