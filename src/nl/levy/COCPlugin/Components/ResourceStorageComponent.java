package nl.levy.COCPlugin.Components;

import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCItems.StorageValues;
import nl.levy.COCPlugin.COCManager.ResourceManger;

public class ResourceStorageComponent implements IComponent {
    public final ResourceType type;
    public StorageValues storageValue;
    public final ResourceManger manger;

    public ResourceStorageComponent(ResourceType type, StorageValues storageValue, ResourceManger manger) {
        this.type = type;
        this.storageValue = storageValue;
        this.manger = manger;
    }

    public void upgradeLevel(StorageValues storageValues) {
        this.storageValue = storageValues;
    }

    public int getCurrentValue() {
        return manger.getCurrent(this);
    }

}
