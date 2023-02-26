package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.*;
import nl.levy.COCPlugin.COCItems.COCLocation;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class COCManager {
    public final ResourceManger resourceManger;
    public final List<COCItem> COCItems;
    private final LevelItemBuilder levelItemBuilder;


    public COCManager(LevelItemBuilder levelItemBuilder) {
        resourceManger = new ResourceManger(this);
        this.levelItemBuilder = levelItemBuilder;
        COCItems = new ArrayList<>();
    }

    public COCManager(LevelItemBuilder levelItemBuilder, COCMainManager.SaveManager data) {
        resourceManger = new ResourceManger(this, data);
        this.levelItemBuilder = levelItemBuilder;
        COCItems = new ArrayList<>();

        for (COCMainManager.SaveItem item : data.items) {
            switch (item.type) {
                case "nl.levy.COCPlugin.COCBuildings.Collector" -> {
                    var coll = createCollector(item.x, item.z);
                    coll.level = item.level;
                    coll.getResourceGeneratorComponent().storedResources = item.extra;
                }
                case "nl.levy.COCPlugin.COCBuildings.GoldMine" -> {
                    var coll = createGoldMine(item.x, item.z);
                    coll.level = item.level;
                    coll.getResourceGeneratorComponent().storedResources = item.extra;
                }
                case "nl.levy.COCPlugin.COCBuildings.ElixirTank" -> createElixirTank(item.x, item.z).level = item.level;
                case "nl.levy.COCPlugin.COCBuildings.GoldStorage" -> createGoldStorage(item.x, item.z).level = item.level;
                case "nl.levy.COCPlugin.COCBuildings.TownHall" -> createTownHall(item.x, item.z).level = item.level;
            }
        }
    }

    public boolean hasResources(ResourceCollection nextLevelPrice) {
        return resourceManger.has(ResourceType.Gold, nextLevelPrice.gold) && resourceManger.has(ResourceType.Elixir, nextLevelPrice.elixir) && resourceManger.has(ResourceType.DarkElixir, nextLevelPrice.darkElixir);
    }

    public void removeResources(ResourceCollection nextLevelPrice) {
        resourceManger.remove(ResourceType.Gold, nextLevelPrice.gold);
        resourceManger.remove(ResourceType.Elixir, nextLevelPrice.elixir);
        resourceManger.remove(ResourceType.DarkElixir, nextLevelPrice.darkElixir);
    }

    public void addResource(ResourceCollection col) {
        resourceManger.add(ResourceType.Gold, col.gold);
        resourceManger.add(ResourceType.Elixir, col.elixir);
        resourceManger.add(ResourceType.DarkElixir, col.darkElixir);
    }

    public COCItem getItem(int playerX, int playerZ) {
        for (COCItem cocItem : COCItems) {
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

    public boolean isOccupied(int x, int z, int size) {
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
                        return true;
                    }
                }
            }
        }

        return false;
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
        GoldStorage item = new GoldStorage(i, i1, levelItemBuilder, this);
        COCItems.add(item);

        return item;
    }

    public ElixirTank createElixirTank(int i, int i1) {
        ElixirTank item = new ElixirTank(i, i1, levelItemBuilder, this);
        COCItems.add(item);

        return item;
    }

    public TownHall createTownHall(int i, int i1) {
        TownHall item = new TownHall(i, i1, levelItemBuilder, this);
        COCItems.add(item);

        return item;
    }
}
