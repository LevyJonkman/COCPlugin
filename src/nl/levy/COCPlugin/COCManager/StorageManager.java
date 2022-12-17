package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.ElixirTank;
import nl.levy.COCPlugin.COCBuildings.GoldStorage;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

public class StorageManager extends InventoryManager {

    public final ResourceManger<GoldStorage> Gold;
    public final ResourceManger<ElixirTank> Elixir;

    public StorageManager() {
        Gold = new ResourceManger<>(GoldStorage.class, (COCManager) this);
        Elixir = new ResourceManger<>(ElixirTank.class, (COCManager) this);
    }

    public void addGold(int amount) {
        Gold.add(amount);
    }

    public void addElixir(int amount) {
        Elixir.add(amount);
    }

    public boolean hasResources(ResourceCollection nextLevelPrice) {
        return (Gold.has(nextLevelPrice.gold) && Elixir.has(nextLevelPrice.elixir));
    }

    public void removeResources(ResourceCollection nextLevelPrice) {
        Gold.remove(nextLevelPrice.gold);
        Elixir.remove(nextLevelPrice.elixir);
    }

    public void addResource(ResourceCollection col) {
        Gold.add(col.gold);
        Elixir.add(col.elixir);
        //DarkElixir.add(col.darkElixir);
    }
}

