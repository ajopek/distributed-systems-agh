import java.util.HashMap;

public class DistributedMap implements SimpleStringMap {
    private HashMap<String, Integer> storage;

    DistributedMap() {
        this.storage = new HashMap<>();
    }

    public boolean containsKey(String key) {
        return storage.containsKey(key);
    };

    public Integer get(String key) {
        return storage.get(key);
    };

    public void put(String key, Integer value) {
        storage.put(key, value);
    };

    public Integer remove(String key) {
        return storage.remove(key);
    };
}
