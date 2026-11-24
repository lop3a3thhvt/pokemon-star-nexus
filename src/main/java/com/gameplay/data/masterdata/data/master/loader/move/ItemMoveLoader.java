package com.gameplay.data.masterdata.data.master.loader.move;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameplay.data.masterdata.data.master.dto.move.tactic.ItemMoveDTO;
import com.gameplay.data.masterdata.data.master.registry.move.tactic.ItemMoveRegistry;
import com.gameplay.data.masterdata.template.move.tactic.item.ItemMove;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.List;

public final class ItemMoveLoader {

	/*------------------------------------------------------------------------------------------------------------------
	1.	Data Loader Config																								*/

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String PATH = "data/move/item/item-moves.json";

	/*------------------------------------------------------------------------------------------------------------------
	2.	Constructor																										*/

	private ItemMoveLoader() {}

	/*------------------------------------------------------------------------------------------------------------------
	3.	Loader																											*/

	public static void loadAll() {
		try (InputStream inputStream = ItemMoveLoader.class.getClassLoader().getResourceAsStream(PATH)) {
			if (inputStream == null) {
				throw new IllegalArgumentException("Load Failed : " + PATH);
			}

			List<ItemMoveDTO> itemMoveDTOList = MAPPER.readValue(inputStream, new TypeReference<List<ItemMoveDTO>>() {});

			for (ItemMoveDTO itemMoveDTO : itemMoveDTOList) {
				ItemMove itemMove = itemMoveDTO.create();
				ItemMoveRegistry.register(itemMove);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Error : " + PATH, e);
		}
	}
}
