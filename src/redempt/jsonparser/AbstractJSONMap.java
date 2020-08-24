package redempt.jsonparser;

import java.util.HashMap;

abstract class AbstractJSONMap extends HashMap<String, Object> implements JSONStorage {

    protected JSONStorage parent;
    protected String key;
    
    public abstract int getInt(String key);
    public abstract boolean getBoolean(String key);
    public abstract double getDouble(String key);
    public abstract JSONList getList(String key);
    public abstract JSONMap getMap(String key);
    public abstract String getString(String key);
    
    @Override
    public JSONStorage getParent() {
        return parent;
    }
    
    @Override
    public void setParent(JSONStorage obj) {
        this.parent = obj;
    }
    
}
