package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.Components.ResourceGeneratorComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.CollectorInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.List;

public class Collector extends COCLevelItem {

    private final List<ResourceProductionLevel> productionLevels;
    public Collector(int x, int y, LevelItemBuilder builder) {
        super(x, y, builder.elixirCollectorData);

        this.productionLevels = builder.elixirCollectorData.productionLevels;

        components.add(new ResourceGeneratorComponent(productionLevels.get(0), ResourceType.Elixir));
    }

    @Override
    public void upgrade() {
        super.upgrade();

        getResourceGeneratorComponent().upgradeLevel(productionLevels.get(level-1));
    }

    public ResourceGeneratorComponent getResourceGeneratorComponent() {
        return this.findFirst();
    }

    @Override
    public int getStaging() {
        ResourceGeneratorComponent item = getResourceGeneratorComponent();
        return Math.max(1, Math.min(3, item.getProduction() / item.getCurrentProductionLevel().total * 4));
    }
    @Override
    public COCInventory createInventory() {
        return new CollectorInventory(this);
    }
}
