package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.Inventories.CanonInventory;
import nl.levy.COCPlugin.ItemBuilder.DefenseBuildingData;

public class Canon extends COCDefenseBuilding {
    public Canon(int x, int y, DefenseBuildingData defenseBuildingData) {
        super(x, y, defenseBuildingData);
    }

    @Override
    public COCInventory createInventory() {
        return new CanonInventory();
    }
}
