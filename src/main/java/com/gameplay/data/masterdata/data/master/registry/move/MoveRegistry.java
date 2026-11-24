package com.gameplay.data.masterdata.data.master.registry.move;

import com.gameplay.data.masterdata.data.master.registry.Registry;
import com.utils.StringUtils;

import java.util.Collection;
import java.util.function.Function;

// protected final giúp các lớp con ko thể override chúng

public abstract class MoveRegistry<ID, M> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final Registry<ID, M> registry;
	private final String labelCode;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	protected MoveRegistry(String registryLabel, String label, String labelCode, Function<M, ID> idFunction, Function<M, String> codeFunction) {
		this.registry = new Registry<>(registryLabel, label, idFunction, codeFunction);
		this.labelCode = StringUtils.upperSnakeCase(labelCode) + "_";
	}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry State																								*/

	protected final void freezeMoveStorage() {
		registry.freeze();
	}

	protected final boolean isMoveStorageFrozen() {
		return registry.isFrozen();
	}

	/*------------------------------------------------------------------------------------------------------------------
	4.	Register																										*/

	protected final void registerMove(M move) {
		registry.register(move);
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Search and Getters																											*/

	protected final M getMoveById(ID moveId) {
		return registry.getById(normalizeId(moveId));
	}

	protected final M getMoveByCode(String moveCode) {
		return registry.getByCode(moveCode);
	}

	protected final Collection<M> getAllMoves() {
		return registry.getAll();
	}

	protected final int getMovesSize() {
		return registry.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Clear and Reset																											*/

	protected final void clearMoves() {
		registry.clear();
	}

	protected final void resetMoves() {
		registry.reset();
	}

	/*------------------------------------------------------------------------------------------------------------------
	*.	Utils																											*/

	@SuppressWarnings("unchecked")
	private ID normalizeId(ID id) {
		if (!(id instanceof String idString)) {
			return id;
		}

		String normalized = StringUtils.upperSnakeCase(idString);
		if (StringUtils.isNullOrBlank(normalized)) {
			return null;
		}

		if (!normalized.startsWith(labelCode)) {
			normalized = labelCode + normalized;
		}

		return (ID) normalized;
	}
}
