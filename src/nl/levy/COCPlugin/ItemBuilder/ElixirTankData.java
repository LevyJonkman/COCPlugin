package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public class ElixirTankData extends StorageResourceData {
    public ElixirTankData(int size, List<ResourceCollection> upgradeCosts, List<StorageValues> storageData) {
        super(size, upgradeCosts, storageData);
    }
}
