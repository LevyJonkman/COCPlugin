package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

class GoldMineData extends BaseResourceData {
    public GoldMineData(int size, List<ResourceCollection> upgradeCosts, List<ResourceProductionLevel> productionLevels) {
        super(size, upgradeCosts, productionLevels);
    }
}
