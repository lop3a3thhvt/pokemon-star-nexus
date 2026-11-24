package com.gameplay.data.masterdata.template.unit.type.enemy;

import com.gameplay.data.masterdata.template.trainer.Trainer;
import com.gameplay.data.masterdata.template.unit.StarUnit;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewel;

public class StarEnemy extends StarUnit<Trainer, StarJewel> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final EnemyRank enemyRank;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarEnemy(Builder builder) {
		super(builder);
		this.enemyRank = builder.enemyRank;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Getters																											*/

	public <T extends Trainer> T getPartnerTrainer(Class<T> trainerType) {
		return trainerType.cast(getPartnerTrainer());
	}

	public <SJ extends StarJewel> SJ getStarJewel(Class<SJ> starJewelRefinementQuality) {
		return starJewelRefinementQuality.cast(getStarJewel());
	}

	public EnemyRank getEnemyRank() {
		return enemyRank;
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Builder																											*/

	public static class Builder extends StarUnit.Builder<Builder, Trainer, StarJewel> {
		private final EnemyRank enemyRank;

		public Builder(String starEnemyId, String starEnemyCode, Trainer trainer, EnemyRank enemyRank) {
			super("ENM_" + starEnemyId, starEnemyCode, trainer);
			this.enemyRank = enemyRank;
		}

		@Override
		public StarEnemy build() {
			return new StarEnemy(this);
		}
	}
}
