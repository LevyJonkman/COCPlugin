package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.Components.IComponent;
import nl.levy.COCPlugin.ItemBuilder.BaseLevelData;

import java.util.ArrayList;
import java.util.List;

public abstract class COCLevelItem  extends COCItem{

    public int level = 1;
    private final List<ResourceCollection> priceLevels;

    protected List<IComponent> components;

    public COCLevelItem(int x, int y, BaseLevelData data) {
        super(x, y, data);
        this.priceLevels = data.upgradeCosts;
        components = new ArrayList<>();
    }

    public int maxLevel() {
        return priceLevels.size();
    }

    public boolean notMaxLevel() {
        return maxLevel() != level;
    }

    public ResourceCollection getNextLevelPrice(){
        return priceLevels.get(level-1);
    }

    public void upgrade() {
        level++;
    }

}
