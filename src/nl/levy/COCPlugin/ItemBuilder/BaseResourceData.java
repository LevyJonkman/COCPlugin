package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public abstract class BaseResourceData extends BaseLevelData {
    public final List<ResourceProductionLevel> productionLevels;

    public BaseResourceData(int size, List<ResourceCollection> upgradeCosts, List<ResourceProductionLevel> productionLevels) {
        super(size, upgradeCosts);
        this.productionLevels = productionLevels;
    }
}
