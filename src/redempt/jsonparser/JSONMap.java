package redempt.jsonparser;

import java.util.HashMap;

public class JSONMap extends HashMap<String, Object> implements JSONStorage {
	
	private JSONStorage parent;
	protected String key;
	
	public int getInt(String key) {
		return (int) get(key);
	}
	
	public boolean getBoolean(String key) {
		return (boolean) get(key);
	}
	
	public double getDouble(String key) {
		return (double) get(key);
	}
	
	public JSONList getList(String key) {
		return (JSONList) get(key);
	}
	
	public JSONMap getMap(String key) {
		return (JSONMap) get(key);
	}
	
	public String getString(String key) {
		return (String) get(key);
	}
	
	@Override
	public String toString() {
		if (size() == 0) {
			return "{}";
		}
		StringBuilder builder = new StringBuilder("{");
		for (Entry<String, Object> entry : this.entrySet()) {
			builder.append('"').append(entry.getKey()).append('"').append(':');
			Object o = entry.getValue();
			if (o instanceof String) {
				builder.append('"').append(((String) o).replace("\\", "\\\\").replace("\"", "\\\"")).append("\", ");
				continue;
			}
			builder.append(o.toString()).append(", ");
		}
		return builder.replace(builder.length() - 2, builder.length(), "}").toString();
	}
	
	@Override
	public JSONStorage getParent() {
		return parent;
	}
	
	@Override
	public void setParent(JSONStorage obj) {
		this.parent = obj;
	}
	
}
