package com.gameplay.data.masterdata.data.master.registry.unit.enemy;

import com.gameplay.data.masterdata.data.master.registry.Registry;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;
import com.utils.StringUtils;

import java.util.Collection;

public final class StarEnemyRegistry {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Registry																									*/

	private static final Registry<String, StarEnemy> STAR_ENEMY_REGISTRY = new Registry<>(
			"StarEnemyRegistry", "StarEnemy",
			StarEnemy::getStarUnitId, StarEnemy::getStarUnitCode
	);

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private StarEnemyRegistry() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry State																								*/

	public static synchronized void freeze() {
		STAR_ENEMY_REGISTRY.freeze();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Register																										*/

	public static synchronized void register(StarEnemy starEnemy) {
		if (starEnemy == null) {
			return;
		}

		STAR_ENEMY_REGISTRY.register(starEnemy);
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Search and Getters																								*/

	public static StarEnemy getStarEnemyById(String id) {
		id = StringUtils.requireUpperSnakeCase(id, "Star Enemy Id");
		if (!id.startsWith("ENM_")) {
			id = "ENM_" + id;
		}
		return STAR_ENEMY_REGISTRY.getById(id);
	}

	public static StarEnemy getStarEnemyByCode(String code) {
		return STAR_ENEMY_REGISTRY.getByCode(code);
	}

	public static Collection<StarEnemy> getAllStarEnemies() {
		return STAR_ENEMY_REGISTRY.getAll();
	}

	public static int getNumberOfStarEnemies() {
		return STAR_ENEMY_REGISTRY.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Clear and Reset																									*/

	public static synchronized void clear() {
		STAR_ENEMY_REGISTRY.clear();
	}

	public static synchronized void reset() {
		STAR_ENEMY_REGISTRY.reset();
	}
}
