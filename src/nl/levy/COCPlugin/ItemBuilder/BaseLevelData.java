package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public abstract class BaseLevelData extends BaseData {
    public final List<ResourceCollection> upgradeCosts;

    public BaseLevelData(int size, List<ResourceCollection> upgradeCosts) {
        super(size);
        this.upgradeCosts = upgradeCosts;
    }
}
