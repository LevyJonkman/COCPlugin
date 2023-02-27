package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.TownHallInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.List;

public class TownHall extends COCLevelItem{

    public TownHall(int x, int y, COCManager manager) {
        super(x, y, LevelItemBuilder.getInstance().townHallData);

        components.add(new ResourceStorageComponent(ResourceType.Gold, getGoldStorageData(), manager.resourceManger));

        components.add(new ResourceStorageComponent(ResourceType.Elixir, getElixirStorageData(), manager.resourceManger));
    }

    private StorageValues getGoldStorageData() {
        return LevelItemBuilder.getInstance().townHallData.getGoldStorageData().get(level-1);
    }

    private StorageValues getElixirStorageData() {
        return LevelItemBuilder.getInstance().townHallData.getElixirStorageData().get(level-1);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        List<ResourceStorageComponent> storages = find(ResourceStorageComponent.class);
        for (ResourceStorageComponent storage : storages) {
            if (storage.type == ResourceType.Gold) {
                storage.upgradeLevel(getGoldStorageData());
            } else if (storage.type == ResourceType.Elixir) {
                storage.upgradeLevel(getElixirStorageData());
            }
        }
    }

    @Override
    public COCInventory createInventory() {
        return new TownHallInventory(this);
    }

    @Override
    public int getStaging() {
        return 1;
    }
}
