package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public class ElixirCollectorData extends BaseResourceData {
    public ElixirCollectorData(int size, List<ResourceCollection> upgradeCosts, List<ResourceProductionLevel> productionLevels) {
        super(size, upgradeCosts, productionLevels);
    }
}
