package com.gameplay.data.masterdata.data.master.loader.trainer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.attribute.region.WorldRegion;
import com.gameplay.data.masterdata.attribute.trainer.NPCTrainerClass;
import com.gameplay.data.masterdata.data.master.dto.trainer.NPCTrainerDTO;
import com.gameplay.data.masterdata.data.master.dto.trainer.TrainerIdentityDTO;
import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.template.trainer.TrainerIdentity;
import com.gameplay.data.masterdata.template.trainer.instance.NPCTrainer;
import com.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public final class TrainerLoader {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Loader Config																								*/

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/trainer/";
	private static final String DEX = PATH + "trainer-dex.json";

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private TrainerLoader() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Loader																											*/

	private static List<List<String>> loadTrainerDex() {
		try (InputStream inputStream = TrainerLoader.class.getClassLoader().getResourceAsStream(DEX)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Load Trainer Dex Failed : " + DEX);
			}

			return MAPPER.readValue(inputStream, new TypeReference<List<List<String>>>() {});
		} catch (IOException e) {
			throw new UncheckedIOException("Error Trainer Dex : " + DEX, e);
		}
	}

	private static void loadTrainer(int region, String trainerPath) {
		try (InputStream inputStream = TrainerLoader.class.getClassLoader().getResourceAsStream(trainerPath)) {
			if (inputStream == null) {
				return;
			}

			TrainerIdentityDTO trainerIdentityDTO = MAPPER.readValue(inputStream, TrainerIdentityDTO.class);
			TrainerIdentity trainerIdentity = trainerIdentityDTO.create(region);
			TrainerRegistry.register(trainerIdentity);
		}  catch (IOException e) {
			throw new UncheckedIOException("Error : " + trainerPath, e);
		}
	}

	private static void loadByRegion(int region, List<String> trainerDexByRegion) {
		if (region < 0 || trainerDexByRegion == null) {
			return;
		}

		WorldRegion worldRegion = WorldRegion.fromId(region);
		if (worldRegion == null) {
			return;
		}
		String trainerPath = PATH + region + "_" + StringUtils.lowerPartedCase(worldRegion.toString()) + "/";

		for (String trainer : trainerDexByRegion) {
			loadTrainer(region, trainerPath + trainer + ".json");
		}
	}

	private static void loadNPC(NPCTrainerClass npcTrainerClass) {
		String npcClassPath = PATH + "npc_trainer/" + StringUtils.lowerSnakeCase(npcTrainerClass.name()) + ".json";
		try (InputStream inputStream = TrainerLoader.class.getClassLoader().getResourceAsStream(npcClassPath)) {
			if (inputStream == null) {
				return;
			}

			List<NPCTrainerDTO> npcTrainerDTOList = MAPPER.readValue(inputStream, new TypeReference<List<NPCTrainerDTO>>() {});

			for (NPCTrainerDTO npcTrainerDTO : npcTrainerDTOList) {
				NPCTrainer npcTrainer = npcTrainerDTO.create(npcTrainerClass);
				TrainerRegistry.register(npcTrainer);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + npcClassPath, e);
		}
	}

	public static void loadAll() {
		int region = 0;

		for (List<String> trainerDexByRegion : loadTrainerDex()) {
			loadByRegion(region, trainerDexByRegion);
			region++;
		}

		for (NPCTrainerClass npcTrainerClass : NPCTrainerClass.values()) {
			loadNPC(npcTrainerClass);
		}
	}
}
