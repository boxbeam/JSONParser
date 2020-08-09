package redempt.jsonparser;

public class JSONParser {
	
	public static Object parse(String string) {
		if (string.startsWith("{") && string.endsWith("}")) {
			return parseMap(string);
		}
		if (string.startsWith("[") && string.endsWith("]")) {
			return parseList(string);
		}
		if (string.startsWith("\"") && string.endsWith("\"")) {
			return string.substring(1, string.length() - 1);
		}
		if (string.indexOf('.') != -1) {
			try {
				return Double.parseDouble(string);
			} catch (NumberFormatException e) {
			}
		}
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
		}
		return string;
	}
	
	public static JSONMap parseMap(String string) {
		JSONMap map = new JSONMap();
		if (!(string.startsWith("{") && string.endsWith("}"))) {
			throw new ParseException("Input is not a valid JSON map");
		}
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		int split = -1;
		boolean quote = false;
		char[] chars = string.toCharArray();
		loop:
		for (int i = 1; i < chars.length - 1; i++) {
			if (depth > 0 && quote && chars[i] == '\\' && chars[i + 1] == '\"') {
				i++;
				builder.append('\\').append('\"');
				continue;
			}
			switch (chars[i]) {
				case '\\':
					if (depth == 0) {
						if (chars[i + 1] == 'n') {
							builder.append('\n');
							i++;
							continue loop;
						}
						builder.append(chars[i + 1]);
						i++;
						continue loop;
					}
				case ':':
					if (!quote && depth == 0) {
						split = builder.length();
					}
					break;
				case ',':
					if (!quote && depth == 0) {
						String key = trim(builder, true, 0, split);
						String value = trim(builder, false, split + 1, builder.length());
						map.put(key, parse(value));
						builder.setLength(0);
						split = -1;
						continue;
					}
					break;
				case '{':
				case '[':
					if (!quote) {
						depth++;
					}
					break;
				case '}':
				case ']':
					if (!quote) {
						depth--;
					}
					break;
				case '"':
					quote = !quote;
			}
			builder.append(chars[i]);
		}
		String key = trim(builder, true, 0, split);
		String value = trim(builder, false, split + 1, builder.length());
		map.put(key, parse(value));
		return map;
	}
	
	public static JSONList parseList(String string) {
		JSONList list = new JSONList();
		if (!(string.startsWith("[") && string.endsWith("]"))) {
			throw new ParseException("Input is not a valid JSON list");
		}
		StringBuilder builder = new StringBuilder();
		int depth = 0;
		boolean quote = false;
		char[] chars = string.toCharArray();
		for (int i = 1; i < chars.length - 1; i++) {
			if (depth > 0 && quote && chars[i] == '\\' && chars[i + 1] == '\"') {
				i++;
				builder.append('\\').append('\"');
				continue;
			}
			switch (chars[i]) {
				case '\\':
					if (chars[i + 1] == 'n') {
						builder.append('\n');
						i++;
						continue;
					}
					builder.append(chars[i + 1]);
					i++;
					continue;
				case ',':
					if (!quote && depth == 0) {
						list.add(parse(trim(builder, false, 0, builder.length())));
						builder.setLength(0);
						continue;
					}
					break;
				case '{':
				case '[':
					if (!quote) {
						depth++;
					}
					break;
				case '}':
				case ']':
					if (!quote) {
						depth--;
					}
					break;
				case '"':
					quote = !quote;
			}
			builder.append(chars[i]);
		}
		list.add(parse(trim(builder, false, 0, builder.length())));
		return list;
	}
	
	private static boolean isWhitespace(char c) {
		return c == ' ' || c == '\n' || c == '\t';
	}
	
	public static String trim(StringBuilder builder, boolean trimQuotes, int start, int end) {
		if (builder.length() == 0) {
			return "";
		}
		end--;
		while (isWhitespace(builder.charAt(start)) && start < end) {
			start++;
		}
		while (isWhitespace(builder.charAt(end)) && end > start) {
			end--;
		}
		if (trimQuotes) {
			if (builder.charAt(end) == '"') {
				end--;
			}
			if (builder.charAt(start) == '"') {
				start++;
			}
		}
		return builder.substring(start, end + 1);
	}
	
}