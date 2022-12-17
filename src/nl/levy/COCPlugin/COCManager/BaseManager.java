package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.*;
import nl.levy.COCPlugin.COCItems.COCLocation;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;
import nl.levy.COCPlugin.COCItems.COCPlayer;

import java.util.ArrayList;
import java.util.List;

public class BaseManager {
    public final List<COCItem> COCItems;
    public final COCPlayer player;
    public final LevelItemBuilder levelItemBuilder;

    public BaseManager() {
        COCItems = new ArrayList<>();
        player = new COCPlayer();
        levelItemBuilder = LevelItemBuilder.create();
    }

    public <T extends COCItem> List<T> getItems(Class<T> clazz) {
        var list = new ArrayList<T>();
        for (COCItem cocItem : COCItems) {
            if (clazz.isInstance(cocItem)) {
                list.add(clazz.cast(cocItem));
            }
        }

        return list;
    }

    public COCItem getItem(int playerX, int playerZ) {
        for (COCItem cocItem : COCItems) {
            System.out.println(cocItem);
            int startX = cocItem.location.x * 3;
            int startZ = cocItem.location.z * 3;

            if (playerX > startX && playerX < startX + cocItem.size * 3) {
                if (playerZ > startZ && playerZ < startZ + cocItem.size * 3) {
                    return cocItem;
                }
            }
        }

        return null;
    }

    public boolean checkSpace(int x, int z, int size) {
        List<COCLocation> locations = new ArrayList<>();
        for (COCItem cocItem : COCItems) {
            for (int i = 0; i < cocItem.size; i++) {
                for (int j = 0; j < cocItem.size; j++) {
                    locations.add(new COCLocation(cocItem.location.x + i, cocItem.location.z + j));
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (COCLocation location : locations) {
                    if (location.x == x + i && location.z == z + j) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Collector createCollector(int x, int y) {
        Collector item = new Collector(x, y, levelItemBuilder);
        COCItems.add(item);

        return item;
    }

    public GoldMine createGoldMine(int i, int i1) {
        GoldMine item = new GoldMine(i, i1, levelItemBuilder);
        COCItems.add(item);

        return item;
    }

    public GoldStorage createGoldStorage(int i, int i1) {
        GoldStorage item = new GoldStorage(i, i1, levelItemBuilder, (COCManager) this);
        COCItems.add(item);

        return item;
    }

    public ElixirTank createElixirTank(int i, int i1) {
        ElixirTank item = new ElixirTank(i, i1, levelItemBuilder, (COCManager) this);
        COCItems.add(item);

        return item;
    }
}
