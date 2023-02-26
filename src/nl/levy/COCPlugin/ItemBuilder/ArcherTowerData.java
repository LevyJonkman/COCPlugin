package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ArcherTowerDamage;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.ArrayList;
import java.util.List;

public class ArcherTowerData extends BaseLevelData {

    public final int range;
    public final ArrayList<ArcherTowerDamage> damageValues;

    public ArcherTowerData(int size, List<ResourceCollection> upgradeCosts, int range, ArrayList<ArcherTowerDamage> damageValues) {
        super(size, upgradeCosts);
        this.range = range;
        this.damageValues = damageValues;
    }
}
