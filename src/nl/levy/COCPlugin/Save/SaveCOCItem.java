package nl.levy.COCPlugin.Save;

import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.COCBuildings.Collector;
import nl.levy.COCPlugin.COCBuildings.GoldMine;

import java.util.ArrayList;
import java.util.List;

public class SaveCOCItem {
    public final String type;
    public final int level;
    public final int x;
    public final int z;
    public final int extra;

    SaveCOCItem(String type, int level, int x, int z) {
        this.type = type;
        this.level = level;
        this.x = x;
        this.z = z;
        this.extra = 0;
    }

    SaveCOCItem(String type, int level, int x, int z, int extra) {
        this.type = type;
        this.level = level;
        this.x = x;
        this.z = z;
        this.extra = extra;
    }

    public static ArrayList<SaveCOCItem> from(List<COCItem> items) {
        var list = new ArrayList<SaveCOCItem>();

        for (COCItem item : items) {
            list.add(from(item));
        }

        return list;
    }

    public static SaveCOCItem from(COCItem item) {
        if (item instanceof Collector nitem) {
            return new SaveCOCItem(
                    item.getClass().getName(),
                    ((COCLevelItem) item).level,
                    item.location.x,
                    item.location.z,
                    nitem.getResourceGeneratorComponent().getProduction());
        } else if (item instanceof GoldMine nitem) {
            return new SaveCOCItem(
                    item.getClass().getName(),
                    ((COCLevelItem) item).level,
                    item.location.x,
                    item.location.z,
                    nitem.getResourceGeneratorComponent().getProduction()
            );
        } else return new SaveCOCItem(
                item.getClass().getName(),
                ((COCLevelItem) item).level,
                item.location.x,
                item.location.z
        );
    }
}
