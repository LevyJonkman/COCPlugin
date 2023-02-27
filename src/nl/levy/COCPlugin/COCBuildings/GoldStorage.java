package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.GoldStorageInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.List;

public class GoldStorage extends COCLevelItem {
    public GoldStorage(int x, int y, COCManager manager) {
        super(x, y, LevelItemBuilder.getInstance().goldStorageData);

        components.add(new ResourceStorageComponent(ResourceType.Gold, getStorageValues(), manager.resourceManger));
    }

    private StorageValues getStorageValues() {
        return LevelItemBuilder.getInstance().goldStorageData.storageData.get(level-1);
    }

    @Override
    public COCInventory createInventory() {
        return new GoldStorageInventory(this);
    }

    public ResourceStorageComponent getResourceStorageComponent() {
        return this.findFirst();
    }

    @Override
    public void upgrade() {
        super.upgrade();

        getResourceStorageComponent().upgradeLevel(getStorageValues());
    }

    @Override
    public int getStaging() {
        var item = getResourceStorageComponent();
        return Math.max(1, Math.min(3, item.getCurrentValue()/item.storageValue.count*4));
    }
}
