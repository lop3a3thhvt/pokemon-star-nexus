package com.gameplay.data.play.collection.ownedstarpartner;

import com.gameplay.data.masterdata.template.unit.type.standby.StandbyStarPartner;

import java.time.Instant;

public abstract class OwnedStarPartner {

	private final StandbyStarPartner standbyStarPartner;
	private final Instant uniteTime;

	private int level;
	private long currentEXP;

	public OwnedStarPartner(StandbyStarPartner standbyStarPartner, int level) {
		this.standbyStarPartner = standbyStarPartner;
		this.uniteTime = Instant.now();

		this.level = level;
		this.currentEXP = 0L;
	}

	public OwnedStarPartner(StandbyStarPartner standbyStarPartner) {
		this(standbyStarPartner, 1);
	}
}
