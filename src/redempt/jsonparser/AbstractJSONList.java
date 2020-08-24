package redempt.jsonparser;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractJSONList extends ArrayList<Object> implements JSONStorage {
    
    protected JSONStorage parent;
    protected boolean add = false;
    
    public abstract int getInt(int key);
    public abstract boolean getBoolean(int key);
    public abstract double getDouble(int key);
    public abstract JSONList getList(int key);
    public abstract JSONMap getMap(int key);
    public abstract String getString(int key);
    public abstract <T> List<T> cast(Class<T> clazz);
    
    @Override
    public JSONStorage getParent() {
        return parent;
    }
    
    @Override
    public void setParent(JSONStorage obj) {
        this.parent = obj;
    }
    
}
