package com.gameplay.data.masterdata.template.unit.type.enemy;

import com.utils.StringUtils;

public enum EnemyRank {
	RETAINER,		// Tùy Tướng
	COMMANDER,		// Chỉ Huy
	MONARCH,		// Quân Chủ
	EMPEROR;		// Đế Vương

	public static EnemyRank fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid EnemyRank : " + code);
		}
	}

	public int getId() {
		return ordinal();
	}
}
