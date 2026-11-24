package com.gameplay.data.masterdata.data.master.dto.trainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.attribute.trainer.NPCTrainerClass;
import com.gameplay.data.masterdata.template.trainer.instance.NPCTrainer;
import com.utils.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NPCTrainerDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("name")
		String name
) {
	public NPCTrainerDTO {
		name = StringUtils.requireUpperSnakeCase(name, "NPC Trainer Name");
	}

	public NPCTrainer create(NPCTrainerClass npcTrainerClass) {
		return new NPCTrainer.Builder(id, npcTrainerClass, name).build();
	}
}
