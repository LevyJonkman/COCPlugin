package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COCBuildings.Collector;
import nl.levy.COCPlugin.InventoryComponents.ProgressBarComponent;
import nl.levy.COCPlugin.InventoryComponents.ResourceComponent;
import nl.levy.COCPlugin.InventoryComponents.LevelComponent;
import org.bukkit.Material;

public class CollectorInventory extends COCInventory {

    public CollectorInventory(Collector resourceItem) {
        super("Elixir Collector");

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
        }, Material.PURPLE_WOOL));

        this.components.add(new ResourceComponent(component, Material.PURPLE_WOOL));
    }
}
