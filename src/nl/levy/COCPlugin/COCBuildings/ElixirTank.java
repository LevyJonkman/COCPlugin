package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.ElixirTankInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.List;

public class ElixirTank extends COCLevelItem {
    private final List<StorageValues> storageData;

    public ElixirTank(int x, int y, LevelItemBuilder data, COCManager manager) {
        super(x, y, data.elixirTankData);
        storageData = data.goldStorageData.storageData;

        components.add(new ResourceStorageComponent(ResourceType.Elixir, data.elixirTankData.storageData.get(0), manager.resourceManger));
    }

    @Override
    public COCInventory createInventory() {
        return new ElixirTankInventory(this);
    }

    public ResourceStorageComponent getResourceStorageComponent() {
        return this.findFirst();
    }

    @Override
    public void upgrade() {
        super.upgrade();

        getResourceStorageComponent().upgradeLevel(storageData.get(level-1));
    }

    @Override
    public int getStaging() {
        var item = getResourceStorageComponent();
        return Math.max(1, Math.min(3, item.getCurrentValue()/item.storageValue.count*4));
    }
}
