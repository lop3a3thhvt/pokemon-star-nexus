package com.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class JsonParse {
	/*------------------------------------------------------------------------------------------------------------------
	*.	Move Tag Parse																									*/

	public static class TagsDeserializer extends JsonDeserializer<List<List<String>>> {
		@Override
		public List<List<String>> deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
			JsonNode node = p.getCodec().readTree(p);
			if (node == null || node.isNull() || !node.isArray() || node.isEmpty()) {
				return List.of(List.of());
			}

			if (node.get(0).isArray()) {
				List<List<String>> result = new ArrayList<>();
				for (JsonNode group : node) {
					result.add(toList(group));
				}
				return result;
			}

			return List.of(toList(node));
		}

		private static List<String> toList(JsonNode arr) {
			List<String> list = new ArrayList<>();
			for (JsonNode n : arr) {
				list.add(n.asText());
			}
			return List.copyOf(list);
		}
	}
}
