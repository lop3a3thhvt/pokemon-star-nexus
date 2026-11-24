package com.gameplay.data.masterdata.template.unit.type.standby;

import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.gameplay.data.masterdata.template.unit.StarUnit;
import com.gameplay.data.masterdata.template.unit.starjewel.StarJewelEmblem;

public class StandbyStarPartner extends StarUnit<TrainerAlternate, StarJewelEmblem> {

	private StandbyStarPartner(Builder builder) {
		super(builder);
	}

	public static class Builder extends StarUnit.Builder<Builder, TrainerAlternate, StarJewelEmblem> {
		public Builder(int starPartnerId, String starPartnerCode, String trainerAlternateCode) {
			TrainerAlternate trainerAlternate = TrainerRegistry.getAlternateByCode(trainerAlternateCode);
			super("SSP_" + trainerAlternate.getTrainerAlternateId() + String.format("%04d", starPartnerId),
					starPartnerCode, trainerAlternate);
		}

		@Override
		public StandbyStarPartner build() {
			return new StandbyStarPartner(this);
		}
	}
}
