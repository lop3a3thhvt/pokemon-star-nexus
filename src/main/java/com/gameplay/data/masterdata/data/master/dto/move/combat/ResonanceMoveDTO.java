package com.gameplay.data.masterdata.data.master.dto.move.combat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.gameplay.data.masterdata.data.master.registry.move.combat.PokemonMoveRegistry;
import com.gameplay.data.masterdata.template.move.combat.MoveMasteryRank;
import com.gameplay.data.masterdata.template.move.combat.pokemon.PokemonMove;
import com.gameplay.data.masterdata.template.move.combat.resonance.ResonanceMove;
import com.utils.JsonParse;
import com.utils.ListUtils;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResonanceMoveDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("prototype")
		String prototype,
		@JsonProperty("base_power") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> basePower,
		@JsonProperty("accuracy") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Double> accuracy,
		@JsonProperty("action_speed") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Double> actionSpeed,
		@JsonProperty("sync_ether_cost") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> syncEtherCost,
		@JsonProperty("stardust_tuning") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<Integer> stardustTuning,
		@JsonProperty("target") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<String> target,
		@JsonProperty("tags") @JsonDeserialize(using = JsonParse.TagsDeserializer.class)
		List<List<String>> tags
) {
	public ResonanceMoveDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Resonance Move Code");

		prototype = StringUtils.requireUpperSnakeCase(prototype, "Resonance Move Prototype");

		basePower = ListUtils.normalizedMoveDataList(basePower, -1, "BasePower");
		accuracy = ListUtils.normalizedMoveDataList(accuracy, -1.0, "Accuracy");
		actionSpeed = ListUtils.normalizedMoveDataList(actionSpeed, 0.0, "ActionSpeed");
		syncEtherCost = ListUtils.normalizedMoveDataList(syncEtherCost, 0, "SyncEtherCost");
		stardustTuning = ListUtils.normalizedMoveDataList(stardustTuning, 0, "StardustTuning");
		target = ListUtils.normalizedMoveDataList(target, "SELF", "Target");
		tags = ListUtils.normalizedMoveDataList(tags, List.of(), "Tags");
	}

	public ResonanceMove create(String starPartnerId) {
		PokemonMove movePrototype = PokemonMoveRegistry.getByCode(prototype);
		ResonanceMove.Builder builder = new ResonanceMove.Builder(starPartnerId, id, code, movePrototype);

		for (int masteryRank = 0; masteryRank <= MoveMasteryRank.MAX_MOVE_MASTERY_RANK; ++masteryRank) {
			MoveMasteryRank moveMasteryRank = new MoveMasteryRank.Builder(masteryRank)
					.setData(
							basePower.get(masteryRank),
							accuracy.get(masteryRank),
							actionSpeed.get(masteryRank),
							syncEtherCost.get(masteryRank),
							stardustTuning.get(masteryRank)
					)
					.setTarget(target.get(masteryRank))
					.setTags(tags.get(masteryRank))
					.build();

			builder.addMoveMasteryRank(moveMasteryRank);
		}

		return builder.build();
	}
}
