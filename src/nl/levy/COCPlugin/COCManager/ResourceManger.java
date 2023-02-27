package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.Save.SaveCOCManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static nl.levy.COCPlugin.COC.FinderHelper.getItemsComponents;

public class ResourceManger {

    private final COCManager manager;
    public final HashMap<ResourceType, Integer> currentValues;

    ResourceManger(COCManager manager, SaveCOCManager data) {
        this.manager = manager;
        currentValues = new HashMap<>();
        currentValues.put(ResourceType.Gold, data.Gold);
        currentValues.put(ResourceType.Elixir, data.Elixir);
        currentValues.put(ResourceType.DarkElixir, data.DarkElixir);
    }
    ResourceManger(COCManager manager) {
        this.manager = manager;
        currentValues = new HashMap<>();
        currentValues.put(ResourceType.Gold, 0);
        currentValues.put(ResourceType.Elixir, 0);
        currentValues.put(ResourceType.DarkElixir, 0);
    }

    public int getCurrent(ResourceType type) {
        return currentValues.get(type);
    }

    private List<ResourceStorageComponent> getItems(ResourceType type) {
        List<ResourceStorageComponent> items = getItemsComponents(manager.COCItems, COCLevelItem.class, ResourceStorageComponent.class);
        List<ResourceStorageComponent> filterdItems = new ArrayList<>();
        for (ResourceStorageComponent item : items) {
            if (item.type == type) {
                filterdItems.add(item);
            }
        }
        return filterdItems;
    }

    public int total(ResourceType type) {
        int count = 0;
        var items = getItems(type);
        for (var item : items) {
            count += item.storageValue.count;
        }

        return count;
    }

    public void add(ResourceType type, int amount) {
        var current = currentValues.get(type);
        current += amount;
        var total = total(type);
        if (current > total) {
            current = total;
        }
        currentValues.put(type, current);
    }

    public boolean has(ResourceType type,int amount) {
        return currentValues.get(type) >= amount;
    }

    public void remove(ResourceType type,int amount) {
        var current = currentValues.get(type);
        current -= amount;
        if (current < 0) {
            current = 0;
        }
        currentValues.put(type, current);
    }


    public int getCurrent(ResourceStorageComponent item) {
        var items = getItems(item.type);

        var toDivide = currentValues.get(item.type);
        var divided = toDivide / items.size();

        for (int i = 0; i <= items.size(); i++) {
            for (int i1 = 0; i1 < items.size(); i1++) {
                var currentItem = items.get(i1);
                if (currentItem.storageValue.count <= divided) {
                    if (currentItem == item) {
                        return currentItem.storageValue.count;
                    }
                    toDivide -= currentItem.storageValue.count;
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
