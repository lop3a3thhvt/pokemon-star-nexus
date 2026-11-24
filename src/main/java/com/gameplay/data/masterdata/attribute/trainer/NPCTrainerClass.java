package com.gameplay.data.masterdata.attribute.trainer;

import com.utils.StringUtils;

public enum NPCTrainerClass {
	BUG_CATCHER;

	public static NPCTrainerClass fromCode(String code) {
		if (StringUtils.isNullOrBlank(code)) {
			return null;
		}

		String fixedCode = StringUtils.upperSnakeCase(code);

		try {
			return valueOf(fixedCode);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid TrainerClass : " + code);
		}
	}

	public String getTrainerClassId() {
		return String.format("%08d", ordinal());
	}
}
