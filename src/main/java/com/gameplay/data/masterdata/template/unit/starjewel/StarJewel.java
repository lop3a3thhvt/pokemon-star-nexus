package com.gameplay.data.masterdata.template.unit.starjewel;

import com.gameplay.data.masterdata.attribute.type.PokemonType;

public abstract class StarJewel {

	private final PokemonType starType;

	protected StarJewel(String starType) {
		this.starType = PokemonType.fromCode(starType);
	}

	public PokemonType getStarType() {
		return starType;
	}
}
