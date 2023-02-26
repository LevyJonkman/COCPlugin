package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.ItemBuilder.BaseLevelData;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class COCDefenceItem extends COCLevelItem{

    public COCDefenceItem(int x, int y, BaseLevelData data) {
        super(x, y, data);
    }

    public abstract void defenseUpdate();

}
