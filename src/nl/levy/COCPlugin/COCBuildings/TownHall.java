package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.TownHallInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;
import nl.levy.COCPlugin.ItemBuilder.TownHallData;

import java.util.List;

public class TownHall extends COCLevelItem{

    private final TownHallData townHallData;

    public TownHall(int x, int y, LevelItemBuilder data, COCManager manager) {
        super(x, y, data.townHallData);
        this.townHallData= data.townHallData;

        components.add(new ResourceStorageComponent(ResourceType.Gold, townHallData.getGoldStorageData().get(0), manager.resourceManger));

        components.add(new ResourceStorageComponent(ResourceType.Elixir, townHallData.getElixirStorageData().get(0), manager.resourceManger));
    }

    @Override
    public void upgrade() {
        super.upgrade();

        List<ResourceStorageComponent> storages = find(ResourceStorageComponent.class);
        for (ResourceStorageComponent storage : storages) {
            if (storage.type == ResourceType.Gold) {
                storage.upgradeLevel(townHallData.getGoldStorageData().get(level-1));
            } else if (storage.type == ResourceType.Elixir) {
                storage.upgradeLevel(townHallData.getElixirStorageData().get(level-1));
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
