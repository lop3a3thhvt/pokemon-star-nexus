package com.gameplay.data.masterdata.data.master.registry;

import com.utils.StringUtils;

import java.util.*;
import java.util.function.Function;

public final class Registry<ID, T> {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Fields																											*/

	private final String registryLabel;
	private final String label;
	private final Function<T, ID> idFunction;
	private final Function<T, String> codeFunction;

	/*------------------------------------------------------------------------------------------------------------------
	2.	Data Storage																									*/

	private final Map<ID, T> byId = new LinkedHashMap<>();
	private final Map<String, T> byCode = new LinkedHashMap<>();

	/*------------------------------------------------------------------------------------------------------------------
	3.	Data Registry State																								*/

	private volatile boolean frozen = false;

	/*------------------------------------------------------------------------------------------------------------------
	4.	Constructor																										*/

	public Registry(String registryLabel, String label, Function<T, ID> idFunction, Function<T, String> codeFunction) {
		this.registryLabel = registryLabel;
		this.label = label;
		this.idFunction = idFunction;
		this.codeFunction = codeFunction;
	}

	/*------------------------------------------------------------------------------------------------------------------
	5.	Data Registry State Management																					*/

	public synchronized void freeze() {
		frozen = true;
	}

	public boolean isFrozen() {
		return frozen;
	}

	/*------------------------------------------------------------------------------------------------------------------
	6.	Register																										*/

	public synchronized void register(T entryData) {
		if (entryData == null) {
			return;
		}

		String rawCode = codeFunction.apply(entryData);

		if (frozen) {
			throw new IllegalStateException(registryLabel + " is frozen. Cannot register : " + rawCode);
		}

		String code = StringUtils.requireUpperSnakeCase(rawCode, "Code");
		ID id = idFunction.apply(entryData);
		id = normalizeId(id);

		if (id == null || (id instanceof String idString && StringUtils.isNullOrBlank(idString))) {
			throw new IllegalArgumentException("Missing Id : " + rawCode);
		}

		boolean idKeyExist = byId.containsKey(id);
		boolean codeKeyExist = byCode.containsKey(code);

		if (idKeyExist || codeKeyExist) {
			throw new IllegalArgumentException("Duplicate Key : " + rawCode + " " + idKeyExist + " " + codeKeyExist);
		}

		byId.put(id, entryData);
		byCode.put(code, entryData);
	}

	/*------------------------------------------------------------------------------------------------------------------
	7.	Search and Getters																								*/

	public T getById(ID id) {
		if (id == null) {
			throw new IllegalArgumentException("Missing " + label + " ID.");
		}

		T result = byId.get(normalizeId(id));
		if (result == null) {
			throw new IllegalArgumentException(label + " does not exist : " + id.toString());
		}

		return result;
	}

	public T getByCode(String code) {
		code = StringUtils.requireUpperSnakeCase(code, label + " Code");

		T result = byCode.get(code);
		if (result == null) {
			throw new IllegalArgumentException(label + " does not exist : " + code);
		}

		return result;
	}

	public Collection<T> getAll() {
		if (byId.isEmpty()) {
			return Collections.unmodifiableCollection(byCode.values());
		}
		return Collections.unmodifiableCollection(byId.values());
	}

	public int size() {
		return byId.size();
	}

	/*------------------------------------------------------------------------------------------------------------------
	8.	Clear and Reset																									*/

	public synchronized void clear() {
		if (frozen) {
			throw new IllegalStateException(registryLabel + " is frozen. Cannot clear.");
		}

		byId.clear();
		byCode.clear();
	}

	public synchronized void reset() {
		frozen = false;
		clear();
	}

	/*------------------------------------------------------------------------------------------------------------------
	*.	Utils																											*/

	@SuppressWarnings("unchecked")
	private ID normalizeId(ID id) {
		if (!(id instanceof String idString)) {
			return id;
		}

		String normalized = StringUtils.requireUpperSnakeCase(idString, label + " Id");

		return (ID) normalized;
	}
}
