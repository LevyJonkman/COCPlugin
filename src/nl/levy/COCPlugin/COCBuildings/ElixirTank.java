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
    public ElixirTank(int x, int y, COCManager manager) {
        super(x, y, LevelItemBuilder.getInstance().elixirTankData);

        components.add(new ResourceStorageComponent(ResourceType.Elixir, getStorageValues(), manager.resourceManger));
    }

    private StorageValues getStorageValues() {
        return LevelItemBuilder.getInstance().elixirTankData.storageData.get(level-1);
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

        getResourceStorageComponent().upgradeLevel(getStorageValues());
    }

    @Override
    public int getStaging() {
        var item = getResourceStorageComponent();
        return Math.max(1, Math.min(3, item.getCurrentValue()/item.storageValue.count*4));
    }
}
