package com.gameplay.data.masterdata.data.master;

import com.gameplay.data.masterdata.data.master.loader.move.ItemMoveLoader;
import com.gameplay.data.masterdata.data.master.loader.move.PokemonMoveLoader;
import com.gameplay.data.masterdata.data.master.loader.pokemon.PokemonLoader;
import com.gameplay.data.masterdata.data.master.loader.trainer.TrainerLoader;
import com.gameplay.data.masterdata.data.master.loader.unit.enemy.StarEnemyLoader;
import com.gameplay.data.masterdata.data.master.loader.unit.starpartner.StandbyStarPartnerLoader;
import com.gameplay.data.masterdata.data.master.registry.move.combat.PokemonMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.combat.ResonanceMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.TrainerMoveRegistry;
import com.gameplay.data.masterdata.data.master.registry.pokemon.PokemonRegistry;
import com.gameplay.data.masterdata.data.master.registry.trainer.TrainerRegistry;
import com.gameplay.data.masterdata.data.master.registry.unit.enemy.StarEnemyRegistry;
import com.gameplay.data.masterdata.data.master.registry.unit.starpartner.StandbyStarPartnerRegistry;

public final class GameMasterDataBootstrap {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Constructor																										*/

	private GameMasterDataBootstrap() {}

	/*------------------------------------------------------------------------------------------------------------------
	2.	Load State																										*/

	private static volatile boolean loaded = false;

	public static boolean isLoaded() {
		return loaded;
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Loader																										*/

	public static synchronized void load() {
		if (loaded) {
			throw new IllegalStateException("GameMasterData is loaded.");
		}

		loadAll();
	}

	public static synchronized void reload() {
		loaded = false;
		loadAll();
	}

	private static void loadAll() {
		resetAll();

		try {
			PokemonLoader.loadAll();
			TrainerLoader.loadAll();

			PokemonMoveLoader.loadAll();
			ItemMoveLoader.loadAll();

			StandbyStarPartnerLoader.loadAll();
			StarEnemyLoader.loadAll();

			freezeAll();
			loaded = true;
		} catch (RuntimeException e) {
			resetAll();
			throw e;
		}
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Freeze and Reset																								*/

	private static void freezeAll() {
		PokemonRegistry.freeze();
		TrainerRegistry.freeze();

		PokemonMoveRegistry.freeze();
		ItemMoveRegistry.freeze();
		ResonanceMoveRegistry.freeze();
		TrainerMoveRegistry.freeze();

		StandbyStarPartnerRegistry.freeze();
		StarEnemyRegistry.freeze();
	}

	private static void resetAll() {
		PokemonRegistry.reset();
		TrainerRegistry.reset();

		PokemonMoveRegistry.reset();
		ItemMoveRegistry.reset();
		ResonanceMoveRegistry.reset();
		TrainerMoveRegistry.reset();

		StandbyStarPartnerRegistry.reset();
		StarEnemyRegistry.reset();
	}
}
