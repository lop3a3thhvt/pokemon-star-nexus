package org.example;

import com.gameplay.data.masterdata.data.master.GameMasterDataBootstrap;
import com.gameplay.data.masterdata.data.master.registry.unit.enemy.StarEnemyRegistry;
import com.gameplay.data.masterdata.template.unit.type.enemy.StarEnemy;

public class Main {
	static void main() {
		GameMasterDataBootstrap.reload();

		for (StarEnemy starEnemy : StarEnemyRegistry.getAllStarEnemies()) {
			System.out.println(starEnemy.getStarUnitId() + " " + starEnemy.getStarUnitCode());
		}
	}
}
