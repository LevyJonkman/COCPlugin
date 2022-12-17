package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public abstract class StorageResourceData extends BaseLevelData {

    public List<StorageValues> storageData;

    public StorageResourceData(int size, List<ResourceCollection> upgradeCosts, List<StorageValues> storageData) {
        super(size, upgradeCosts);
        this.storageData = storageData;
    }
}
