package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.COCItems.StorageValues;

import java.util.List;

public class TownHallData extends BaseLevelData{
    public final GoldStorageData goldStorageData;
    public final ElixirTankData elixirStorageData;

    public List<StorageValues> getElixirStorageData() {
        return elixirStorageData.storageData;
    }
    public List<StorageValues> getGoldStorageData() {
        return goldStorageData.storageData;
    }

    public TownHallData(int size, List<ResourceCollection> upgradeCosts, GoldStorageData goldStorageData, ElixirTankData elixirStorageData) {
        super(size, upgradeCosts);
        this.goldStorageData = goldStorageData;
        this.elixirStorageData = elixirStorageData;
    }
}
