package nl.levy.COCPlugin.COCBuildings;

import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCManager.ResourceManger;
import nl.levy.COCPlugin.ItemBuilder.StorageResourceData;

import java.util.List;

public abstract class COCResourceStorage extends COCLevelItem{

    public int currentStorage() {
        return storageManager.getCurrent(this);
    }

    List<StorageValues> storageValues;
    ResourceManger storageManager;

    public COCResourceStorage(int x, int y, StorageResourceData data, ResourceManger storageManager) {
        super(x, y, data);
        storageValues = data.storageData;
        this.storageManager = storageManager;
    }

    public int totalStorage() {
        return storageValues.get(level-1).count;
    }

    @Override
    public int getStaging() {
        return Math.max(1, Math.min(3, currentStorage()/totalStorage()*4));
    }
}
