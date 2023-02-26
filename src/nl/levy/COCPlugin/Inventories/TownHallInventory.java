package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.FinderHelper;
import nl.levy.COCPlugin.COCBuildings.TownHall;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.InventoryComponents.LevelComponent;
import nl.levy.COCPlugin.InventoryComponents.ProgressBarComponent;
import org.bukkit.Material;

public class TownHallInventory extends COCInventory{

    public TownHallInventory(TownHall townHall) {
        super("Townhall");

        components.add(new LevelComponent(townHall, 1));

        var component = townHall.find(ResourceStorageComponent.class);

        for (ResourceStorageComponent resourceStorageComponent : component) {
            if (resourceStorageComponent.type == ResourceType.Gold) {
                components.add(new ProgressBarComponent(new ProgressBarComponent.ProgressBarComponentFunction() {
                    @Override
                    public double getCurrent() {
                        return resourceStorageComponent.getCurrentValue();
                    }

                    @Override
                    public int getTotal() {
                        return resourceStorageComponent.storageValue.count;
                    }
                }, Material.YELLOW_WOOL, 4));
            } else if (resourceStorageComponent.type == ResourceType.Elixir) {
                components.add(new ProgressBarComponent(new ProgressBarComponent.ProgressBarComponentFunction() {
                    @Override
                    public double getCurrent() {
                        return resourceStorageComponent.getCurrentValue();
                    }

                    @Override
                    public int getTotal() {
                        return resourceStorageComponent.storageValue.count;
                    }
                }, Material.PURPLE_WOOL, 5));
            } else if (resourceStorageComponent.type == ResourceType.DarkElixir) {
                components.add(new ProgressBarComponent(new ProgressBarComponent.ProgressBarComponentFunction() {
                    @Override
                    public double getCurrent() {
                        return resourceStorageComponent.getCurrentValue();
                    }

                    @Override
                    public int getTotal() {
                        return resourceStorageComponent.storageValue.count;
                    }
                }, Material.BLACK_WOOL, 6));
            }
        }
    }
}
