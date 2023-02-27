package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COCBuildings.*;
import nl.levy.COCPlugin.COCItems.COCLocation;
import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.Save.RedisClient;
import nl.levy.COCPlugin.Save.SaveCOCItem;
import nl.levy.COCPlugin.Save.SaveCOCManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class COCManager {
    public final ResourceManger resourceManger;
    public final List<COCItem> COCItems;
    public final UUID mcPlayerUUID;

    public void Save() {
        new RedisClient().save(mcPlayerUUID, this);
    }

    public COCManager(UUID mcPlayerUUID) {
        this.mcPlayerUUID = mcPlayerUUID;
        resourceManger = new ResourceManger(this);
        COCItems = new ArrayList<>();
    }

    public COCManager(SaveCOCManager data, UUID mcPlayerUUID) {
        this.mcPlayerUUID = mcPlayerUUID;
        resourceManger = new ResourceManger(this, data);
        COCItems = new ArrayList<>();


        for (SaveCOCItem item : data.items) {
            switch (item.type) {
                case "nl.levy.COCPlugin.COCBuildings.Collector" -> {
                    var coll = new Collector(item.x, item.z);
                    coll.level = item.level;
                    coll.getResourceGeneratorComponent().storedResources = item.extra;
                    COCItems.add(coll);
                }
                case "nl.levy.COCPlugin.COCBuildings.GoldMine" -> {
                    var coll = new GoldMine(item.x, item.z);
                    coll.level = item.level;
                    coll.getResourceGeneratorComponent().storedResources = item.extra;
                }
                case "nl.levy.COCPlugin.COCBuildings.ElixirTank" -> {
                    var tank = new ElixirTank(item.x, item.z, this);
                    tank.level = item.level;
                    COCItems.add(tank);
                }
                case "nl.levy.COCPlugin.COCBuildings.GoldStorage" -> {
                    var tank = new GoldStorage(item.x, item.z, this);
                    tank.level = item.level;
                    COCItems.add(tank);
                }
                case "nl.levy.COCPlugin.COCBuildings.TownHall" -> {
                    TownHall item2 = new TownHall(item.x, item.z, this);
                    item2.level = item.level;
                    COCItems.add(item2);
                }
                case "nl.levy.COCPlugin.COCBuildings.ArcherTower" -> {
                    ArcherTower item2 = new ArcherTower(item.x, item.z);
                    item2.level = item.level;
                    COCItems.add(item2);
                }
            }
        }
    }


    ///resources

    public boolean hasResources(ResourceCollection nextLevelPrice) {
        return resourceManger.has(ResourceType.Gold, nextLevelPrice.gold) && resourceManger.has(ResourceType.Elixir, nextLevelPrice.elixir) && resourceManger.has(ResourceType.DarkElixir, nextLevelPrice.darkElixir);
    }

    public void removeResources(ResourceCollection nextLevelPrice) {
        resourceManger.remove(ResourceType.Gold, nextLevelPrice.gold);
        resourceManger.remove(ResourceType.Elixir, nextLevelPrice.elixir);
        resourceManger.remove(ResourceType.DarkElixir, nextLevelPrice.darkElixir);

        Save();
    }

    public void addResource(ResourceCollection col) {
        resourceManger.add(ResourceType.Gold, col.gold);
        resourceManger.add(ResourceType.Elixir, col.elixir);
        resourceManger.add(ResourceType.DarkElixir, col.darkElixir);

        Save();
    }

    ////items locations mc

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

    //create coc items


    public Collector createCollector(int x, int y) {
        Collector item = new Collector(x, y);
        COCItems.add(item);

        Save();
        return item;
    }

    public GoldMine createGoldMine(int i, int i1) {
        GoldMine item = new GoldMine(i, i1);
        COCItems.add(item);

        Save();
        return item;
    }

    public GoldStorage createGoldStorage(int i, int i1) {
        GoldStorage item = new GoldStorage(i, i1, this);
        COCItems.add(item);

        Save();
        return item;
    }

    public ElixirTank createElixirTank(int i, int i1) {
        ElixirTank item = new ElixirTank(i, i1, this);
        COCItems.add(item);

        Save();
        return item;
    }

    public TownHall createTownHall(int i, int i1) {
        TownHall item = new TownHall(i, i1, this);
        COCItems.add(item);

        Save();
        return item;
    }

    ///attack logic

    public void updateDefences() {
        System.out.println(1);
        for (COCItem cocItem : COCItems) {
            System.out.println(cocItem);
            if (cocItem instanceof COCDefenceItem item) {
                item.defenseUpdate(null);
            }
        }
    }

    public ArcherTower createArcherTower(int i, int i1) {
        System.out.println("create");
        ArcherTower tower = new ArcherTower(i, i1);
        System.out.println("done create");
        COCItems.add(tower);
        return tower;
    }
}
