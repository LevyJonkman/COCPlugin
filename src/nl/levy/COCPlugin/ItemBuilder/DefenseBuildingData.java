package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.DefenseDamageValues;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public abstract class DefenseBuildingData extends BaseLevelData {
    public final List<DefenseDamageValues> damageValues;

    public DefenseBuildingData(int size, List<ResourceCollection> upgradeCosts, List<DefenseDamageValues> damageValues) {
        super(size, upgradeCosts);
        this.damageValues = damageValues;
    }
}
