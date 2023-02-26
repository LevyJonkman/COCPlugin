package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.GoldStorage;
import nl.levy.COCPlugin.InventoryComponents.LevelComponent;
import nl.levy.COCPlugin.InventoryComponents.ProgressBarComponent;
import org.bukkit.Material;

public class GoldStorageInventory extends COCInventory{
    public GoldStorageInventory(GoldStorage item) {
        super("Gold storage");

        this.components.add(new LevelComponent(item));
        var component = item.getResourceStorageComponent();
        this.components.add(new ProgressBarComponent(new ProgressBarComponent.ProgressBarComponentFunction() {
            @Override
            public double getCurrent() {
                return component.getCurrentValue();
            }

            @Override
            public int getTotal() {
                return component.storageValue.count;
            }
        }, Material.YELLOW_WOOL));
    }
}
