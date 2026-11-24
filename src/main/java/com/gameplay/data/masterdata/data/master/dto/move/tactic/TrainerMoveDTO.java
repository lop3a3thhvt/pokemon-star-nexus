package com.gameplay.data.masterdata.data.master.dto.move.tactic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.move.tactic.trainer.TrainerMove;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrainerMoveDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("TP")
		int tacticPoint,
		@JsonProperty("sync_ether_cost")
		int syncEtherCost,
		@JsonProperty("stardust_tuning")
		int stardustTuning,
		@JsonProperty("target")
		String target,
		@JsonProperty("tags")
		List<String> tags
) {
	public TrainerMoveDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Trainer Move Code");

		target = StringUtils.requireUpperSnakeCase(target, "Trainer Move Target");
		tags = (tags != null) ? List.copyOf(tags) : List.of();
	}

	public TrainerMove create(String trainerAlternateId) {
		return new TrainerMove.Builder(trainerAlternateId, id, code)
				.setData(tacticPoint, syncEtherCost, stardustTuning)
				.setTarget(target)
				.setTags(tags)
				.build();
	}
}
