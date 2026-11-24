package com.gameplay.data.masterdata.data.master.dto.trainer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gameplay.data.masterdata.data.master.dto.move.tactic.TrainerMoveDTO;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.data.masterdata.template.move.tactic.trainer.TrainerMove;
import com.gameplay.data.masterdata.template.trainer.instance.TrainerAlternate;
import com.utils.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrainerAlternateDTO(
		@JsonProperty("id")
		int id,
		@JsonProperty("code")
		String code,
		@JsonProperty("region")
		String region,
		@JsonProperty("classes") @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
		List<String> classes,
		@JsonProperty("moves")
		List<TrainerMoveDTO> trainerMoveDTOList,
		@JsonProperty("partner")
		List<String> partnerPokemon
) {
	public TrainerAlternateDTO {
		code = (code != null) ? StringUtils.requireUpperSnakeCase(code, "Trainer Alternate Code") : "CASUAL";
		region = StringUtils.requireUpperSnakeCase(region, "Affiliated Region");
		classes = (classes != null) ? List.copyOf(classes) : List.of();
		trainerMoveDTOList = (trainerMoveDTOList != null) ? List.copyOf(trainerMoveDTOList) : List.of();
		partnerPokemon = (partnerPokemon != null) ? List.copyOf(partnerPokemon) : List.of();
	}

	public TrainerAlternate create(String trainerIdentityId, String trainerIdentityCode) {
		TrainerAlternate.Builder builder = new TrainerAlternate.Builder(trainerIdentityId, trainerIdentityCode, id, code)
				.setAffiliatedRegion(region)
				.setTrainerClasses(classes);

		for (TrainerMoveDTO trainerMoveDTO : trainerMoveDTOList) {
			TrainerMove trainerMove = trainerMoveDTO.create(trainerIdentityId + String.format("%03d", id));
			builder.addTrainerMove(trainerMove);
			TrainerMoveRegistry.register(trainerMove);
		}

		return builder.build();
	}
}
