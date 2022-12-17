package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.Inventories.ArcherTowerInventory;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.ItemBuilder.DefenseBuildingData;

public class ArcherTower extends COCDefenseBuilding{
    public ArcherTower(int x, int y, DefenseBuildingData defenseBuildingData) {
        super(x, y, defenseBuildingData);
    }

    @Override
    public COCInventory createInventory() {
        return new ArcherTowerInventory();
    }
}
