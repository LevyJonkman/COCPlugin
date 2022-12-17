package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.DefenseDamageValues;
import nl.levy.COCPlugin.ItemBuilder.DefenseBuildingData;

import java.util.List;

public abstract class COCDefenseBuilding extends COCLevelItem {

    private final List<DefenseDamageValues> damageValues;

    public COCDefenseBuilding(int x, int y, DefenseBuildingData data) {
        super(x, y, data);
        this.damageValues = data.damageValues;
    }

    private int getDamage() {
        return damageValues.get(level-1).damage;
    }

    private int getShootingSpeed() {
        return damageValues.get(level-1).shootingSpeed;
    }

    @Override
    public int getStaging() {
        return 1;
    }

}
