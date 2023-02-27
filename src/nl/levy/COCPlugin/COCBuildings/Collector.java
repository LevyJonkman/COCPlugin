package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.Components.ResourceGeneratorComponent;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.CollectorInventory;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.List;

public class Collector extends COCLevelItem {

    public Collector(int x, int y) {
        super(x, y, LevelItemBuilder.getInstance().elixirCollectorData);

        components.add(new ResourceGeneratorComponent(getProductionLevel(), ResourceType.Elixir));
    }

    private ResourceProductionLevel getProductionLevel() {
        return LevelItemBuilder.getInstance().elixirCollectorData.productionLevels.get(level-1);
    }

    @Override
    public void upgrade() {
        super.upgrade();

        getResourceGeneratorComponent().upgradeLevel(getProductionLevel());
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
