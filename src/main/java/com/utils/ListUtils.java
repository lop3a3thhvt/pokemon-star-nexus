package com.utils;

import java.util.List;

public final class ListUtils {
	private ListUtils() {}

	public static <T> List<T> normalizedMoveDataList(List<T> src, T defaultValue, String label) {
		List<T> list = (src != null) ? List.copyOf(src) : List.of(defaultValue);
		return switch (list.size()) {
			case 1 -> List.of(list.getFirst(), list.getFirst(), list.getFirst(), list.getFirst());
			case 4 -> List.copyOf(list);
			default -> throw new IllegalArgumentException("Size of " + label + " must be 1 or 4 but was " + list.size());
		};
	}
}
