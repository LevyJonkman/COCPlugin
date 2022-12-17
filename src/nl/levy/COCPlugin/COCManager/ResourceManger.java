package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.COCResourceStorage;

public class ResourceManger<T extends COCResourceStorage> {

    public final Class<T> Class;
    private final COCManager manager;
    private int current;

    ResourceManger(java.lang.Class<T> aClass, COCManager manager) {
        Class = aClass;
        this.manager = manager;
    }


    int total() {
        int count = 0;
        for (T item : manager.getItems(Class)) {
            count += item.totalStorage();
        }

        return count;
    }

    void add(int amount) {
        current += amount;
        var total = total();
        if (current > total) {
            current = total;
        }
    }

    boolean has(int amount) {
        return current >= amount;
    }

    void remove(int amount) {
        current -= amount;
        if (current < 0) {
            current = 0;
        }
    }


    public int getCurrent(COCResourceStorage item) {
        var items = manager.getItems(Class);

        var toDivide = current;
        var divided = toDivide / items.size();

        for (int i = 0; i <= items.size(); i++) {
            for (int i1 = 0; i1 < items.size(); i1++) {
                var currentItem = items.get(i1);
                if (currentItem.totalStorage() <= divided) {
                    if (currentItem == item) {
                        return currentItem.totalStorage();
                    }
                    toDivide -= currentItem.totalStorage();
                    items.remove(currentItem);
                }
            }
            divided = toDivide / items.size();
        }

        var extra = toDivide - divided * items.size();

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == item) {
                return i < extra ? 1 + divided : divided;
            }
        }

        throw new RuntimeException("not possible");
    }
}
