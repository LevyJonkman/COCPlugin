package nl.levy.COCPlugin.ItemBuilder;

import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCItems.ResourceCollection;

import java.util.List;

public class GoldStorageData extends StorageResourceData{
    public GoldStorageData(int size, List<ResourceCollection> upgradeCosts, List<StorageValues> storageData) {
        super(size, upgradeCosts, storageData);
    }
}
