package com.utils;

import java.util.*;

public final class IdCodeKeyMap<ID, T> {
	private final Map<ID, T> byId;
	private final Map<String, T> byCode;

	private IdCodeKeyMap(Builder<ID, T> builder) {
		this.byId = Collections.unmodifiableMap(new LinkedHashMap<>(builder.byId));
		this.byCode = Map.copyOf(builder.byCode);
	}

	public T getById(ID id) {
		return byId.get(normalizeId(id));
	}

	public T getByCode(String code) {
		String key = StringUtils.requireUpperSnakeCase(code, "Code");
		return byCode.get(key);
	}

	public Collection<T> getAll() {
		return byId.values();
	}

	public int size() {
		return byId.size();
	}

	public boolean isEmpty() {
		return byId.isEmpty();
	}

	public static final class Builder<ID, T> {
		private final Map<ID, T> byId = new LinkedHashMap<>();
		private final Map<String, T> byCode = new HashMap<>();

		public void put(ID id, String code, T value) {
			Objects.requireNonNull(id);
			Objects.requireNonNull(code);
			Objects.requireNonNull(value);

			String key = StringUtils.requireUpperSnakeCase(code, "Code");
			id = normalizeId(id);

			if (byId.containsKey(id)) {
				throw new IllegalArgumentException("Duplicate ID : " + id);
			}

			if (byCode.containsKey(code)) {
				throw new IllegalArgumentException("Duplicate Code : " + code);
			}

			byId.put(id, value);
			byCode.put(key, value);
		}

		public IdCodeKeyMap<ID, T> build() {
			return new IdCodeKeyMap<>(this);
		}
	}

	/*------------------------------------------------------------------------------------------------------------------
	*.	Utils																											*/

	@SuppressWarnings("unchecked")
	private static <ID> ID normalizeId(ID id) {
		if (!(id instanceof String idString)) {
			return id;
		}

		String normalized = StringUtils.upperSnakeCase(idString);
		if (StringUtils.isNullOrBlank(normalized)) {
			throw new IllegalArgumentException("Missing Id.");
		}

		return (ID) normalized;
	}
}
