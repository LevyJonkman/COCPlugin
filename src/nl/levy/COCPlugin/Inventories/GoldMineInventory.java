package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.GoldMine;
import nl.levy.COCPlugin.InventoryComponents.ProgressBarComponent;
import nl.levy.COCPlugin.InventoryComponents.ResourceComponent;
import nl.levy.COCPlugin.InventoryComponents.LevelComponent;
import org.bukkit.Material;

public class GoldMineInventory extends COCInventory {

    public GoldMineInventory(GoldMine resourceItem) {
        super("Goldmine");

        this.components.add(new LevelComponent(resourceItem));

        var component = resourceItem.getResourceGeneratorComponent();
        this.components.add(new ProgressBarComponent(new ProgressBarComponent.ProgressBarComponentFunction() {
            @Override
            public double getCurrent() {
                return component.getProduction();
            }

            @Override
            public int getTotal() {
                return component.getCurrentProductionLevel().total;
            }
        }, Material.YELLOW_WOOL));

        this.components.add(new ResourceComponent(component, Material.YELLOW_WOOL));
    }
}
