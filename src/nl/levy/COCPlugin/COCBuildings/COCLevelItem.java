package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.Components.IComponent;
import nl.levy.COCPlugin.Components.ResourceGeneratorComponent;
import nl.levy.COCPlugin.Components.ResourceStorageComponent;
import nl.levy.COCPlugin.ItemBuilder.BaseLevelData;

import java.util.ArrayList;
import java.util.List;

public abstract class COCLevelItem  extends COCItem{

    public int level = 1;
    private final List<ResourceCollection> priceLevels;

    public List<IComponent> components;

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

    public <T extends IComponent> List<T> find(Class<T> tClass) {
        List<T> list = new ArrayList<>();

        for (IComponent component : components) {
            if (tClass.isInstance(component)) {
                list.add(tClass.cast(component));
            }
        }

        return list;
    }

    public <T extends IComponent> T findFirst() {
        for (IComponent component : components) {
            try {
                var tComponent = (T) component;
                return tComponent;
            } catch (Exception ignored) {
            }
        }

        return null;
    }

    public String toSaveString() {
        return "{" +
                "\"type\":\"" + getClass().getName() + "\"," +
                "\"level\":\"" + level + "\"," +
                "\"x\":\"" + location.x + "\"," +
                "\"z\":\"" + location.z + "\"" +
                "}";
    }
}
