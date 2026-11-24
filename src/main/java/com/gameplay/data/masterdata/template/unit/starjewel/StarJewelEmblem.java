package com.gameplay.data.masterdata.template.unit.starjewel;

import com.gameplay.data.masterdata.attribute.starpartner.Starsoul;

public class StarJewelEmblem extends StarJewel {

	private final Starsoul starsoul;

	public StarJewelEmblem(String starType, String starsoul) {
		super(starType);
		this.starsoul = Starsoul.fromCode(starsoul);
	}

	public Starsoul getStarsoul() {
		return starsoul;
	}
}
