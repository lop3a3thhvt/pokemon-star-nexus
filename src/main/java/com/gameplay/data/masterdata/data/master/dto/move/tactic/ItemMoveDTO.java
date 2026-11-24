package com.gameplay.data.masterdata.data.master.dto.move.tactic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.move.tactic.item.ItemMove;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemMoveDTO(
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
	public ItemMoveDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Item Move Code");

		target = StringUtils.requireUpperSnakeCase(target, "Item Move Target");
		tags = (tags != null) ? List.copyOf(tags) : List.of();
	}

	public ItemMove create() {
		return new ItemMove.Builder(id, code)
				.setData(tacticPoint, syncEtherCost, stardustTuning)
				.setTarget(target)
				.setTags(tags)
				.build();
	}
}
