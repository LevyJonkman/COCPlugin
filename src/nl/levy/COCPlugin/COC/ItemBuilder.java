package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCItems.COCLocation;

public class ItemBuilder {
    int level;
    public int size;
    public COCLocation location;

    public ItemBuilder(int level, int size, COCLocation location) {
        this.level = level;
        this.size = size;
        this.location = location;
    }
}
