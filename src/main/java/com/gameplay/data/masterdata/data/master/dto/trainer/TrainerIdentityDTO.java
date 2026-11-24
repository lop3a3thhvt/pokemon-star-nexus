package com.gameplay.data.masterdata.data.master.dto.trainer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.template.trainer.TrainerIdentity;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrainerIdentityDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("alternate_variety")
		List<TrainerAlternateDTO> alternateVariety
) {
	public TrainerIdentityDTO {
		code = StringUtils.requireUpperSnakeCase(code, "Trainer Identity Code");
		alternateVariety = (alternateVariety != null) ? List.copyOf(alternateVariety) : List.of();
	}

	public TrainerIdentity create(int region) {
		TrainerIdentity.Builder builder = new TrainerIdentity.Builder(region, id, code);

		for (TrainerAlternateDTO trainerAlternateDTO : alternateVariety) {
			builder.addAlternate(trainerAlternateDTO.create(String.format("%03d%05d", region, id), code));
		}

		return builder.build();
	}
}
