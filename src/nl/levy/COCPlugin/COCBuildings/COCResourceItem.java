package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.ItemBuilder.BaseResourceData;

import java.util.Date;
import java.util.List;

public abstract class COCResourceItem extends COCLevelItem {

    private final List<ResourceProductionLevel> productionLevels;
    public Date lastCleaned;
    private int storedResources = 0;

    public COCResourceItem(int x, int y, BaseResourceData data) {
        super(x, y, data);

        lastCleaned = new Date();
        this.productionLevels = data.productionLevels;
    }

    public ResourceProductionLevel getCurrentLevel() {
        return productionLevels.get(level - 1);
    }

    public int getProduction() {
        long time = new Date().getTime() - lastCleaned.getTime();
        var cur = getCurrentLevel();
        return (int) Math.min(cur.total, (time / 1000) * cur.productionPerSeconde + storedResources);
    }

    @Override
    public void upgrade() {
        storedResources = getProduction();
        lastCleaned = new Date();

        super.upgrade();
    }

    public void collect(COCManager manager) {
        var production = getProduction();
        lastCleaned = new Date();
        storedResources = 0;

        addResourcesToPlayer(manager, production);
    }

    @Override
    public int getStaging() {
        return Math.max(1, Math.min(3, getProduction() / getCurrentLevel().total * 4));
    }

    protected abstract void addResourcesToPlayer(COCManager manager, int amount);
}
