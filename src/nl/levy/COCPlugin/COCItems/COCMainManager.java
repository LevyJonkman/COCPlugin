package nl.levy.COCPlugin.COCItems;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.levy.COCPlugin.COC.COCScoreboardManager;
import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.COCBuildings.Collector;
import nl.levy.COCPlugin.COCBuildings.GoldMine;
import nl.levy.COCPlugin.COCManager.BuildingManager;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.COCManager.InventoryManager;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class COCMainManager {

    public final HashMap<UUID, COCManager> managers;
    public final InventoryManager inventoryManager;
    public final COCScoreboardManager scoreboardManager;
    public final BuildingManager buildingManager;
    public final LevelItemBuilder levelItemBuilder;

    public COCManager getManager(HumanEntity player) {
        var manager = managers.get(player.getUniqueId());

        if (manager == null) {
            manager = new COCManager(levelItemBuilder);
            managers.put(player.getUniqueId(), manager);
        }

        return manager;
    }

    public COCMainManager(Plugin plugin) {
        levelItemBuilder = LevelItemBuilder.create();
        managers = new HashMap<>();

        try {
            var path = Path.of("C:\\Users\\Gebruiker\\Documents\\Server\\plugins\\data\\playerdata.json");
            if (path.toFile().exists()) {
                String s = Files.readString(path);

                HashMap<String, SaveManager> data = new Gson().fromJson(s, new TypeToken<HashMap<String, SaveManager>>() {
                }.getType());

                for (Map.Entry<String, SaveManager> stringSaveManagerEntry : data.entrySet()) {
                    var manager = new COCManager(levelItemBuilder, stringSaveManagerEntry.getValue());

                    managers.put(UUID.fromString(stringSaveManagerEntry.getKey()), manager);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        inventoryManager = new InventoryManager(this);
        scoreboardManager = new COCScoreboardManager(this);
        buildingManager = new BuildingManager();

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            inventoryManager.update();
            scoreboardManager.update();

        }, 0, 20);
    }


    public static class SaveManager {
        public final int Gold;
        public final int Elixir;
        public final int DarkElixir;
        public final ArrayList<SaveItem> items;

        SaveManager(int gold, int elixir, int darkElixir, ArrayList<SaveItem> items) {
            Gold = gold;
            Elixir = elixir;
            DarkElixir = darkElixir;
            this.items = items;
        }

        public static SaveManager from(COCManager manager) {
            var rm = manager.resourceManger;
            return new SaveManager(
                    rm.getCurrent(ResourceType.Gold),
                    rm.getCurrent(ResourceType.Elixir),
                    rm.getCurrent(ResourceType.DarkElixir),
                    SaveItem.from(manager.COCItems)
            );
        }
    }

    public static class SaveItem {

        public final String type;
        public final int level;
        public final int x;
        public final int z;
        public final int extra;

        SaveItem(String type, int level, int x, int z) {
            this.type = type;
            this.level = level;
            this.x = x;
            this.z = z;
            this.extra = 0;
        }

        SaveItem(String type, int level, int x, int z, int extra) {
            this.type = type;
            this.level = level;
            this.x = x;
            this.z = z;
            this.extra = extra;
        }

        public static ArrayList<SaveItem> from(List<COCItem> items) {
            var list = new ArrayList<SaveItem>();

            for (COCItem item : items) {
                list.add(from(item));
            }

            return list;
        }

        public static SaveItem from(COCItem item) {
            if (item instanceof Collector nitem) {
                return new SaveItem(
                        item.getClass().getName(),
                        ((COCLevelItem) item).level,
                        item.location.x,
                        item.location.z,
                        nitem.getResourceGeneratorComponent().getProduction());
            } else if (item instanceof GoldMine nitem) {
                return new SaveItem(
                        item.getClass().getName(),
                        ((COCLevelItem) item).level,
                        item.location.x,
                        item.location.z,
                        nitem.getResourceGeneratorComponent().getProduction()
                );
            } else return new SaveItem(
                    item.getClass().getName(),
                    ((COCLevelItem) item).level,
                    item.location.x,
                    item.location.z
            );
        }
    }

    public String getSaveData() {
        var list = new HashMap<String, SaveManager>();

        for (var key : managers.keySet()) {
            list.put(key.toString(), SaveManager.from(managers.get(key)));
        }

        return new Gson().toJson(list);


//        StringBuilder managersData = new StringBuilder("[");
//
//        for (HumanEntity humanEntity : managers.keySet()) {
//            var manager = managers.get(humanEntity);
//
//
//            StringBuilder items = new StringBuilder("[");
//
//            for (int i = 0; i < manager.COCItems.size(); i++) {
//                if (i + 1 != manager.COCItems.size()) {
//                    items.append(((COCLevelItem) manager.COCItems.get(i)).toSaveString()).append(",");
//                } else {
//                    items.append(((COCLevelItem)manager.COCItems.get(i)).toSaveString());
//                }
//            }
//
//            items.append("]");
//
//            String data = "{" +
//                    "\"Gold\": \"" + manager.resourceManger.getCurrent(ResourceType.Gold) + "\"," +
//                    "\"Elixir\": \"" + manager.resourceManger.getCurrent(ResourceType.Elixir) + "\"," +
//                    "\"DarkElixir\": \"" + manager.resourceManger.getCurrent(ResourceType.DarkElixir) + "\"," +
//                    "\"items\": " + items +
//                    "},";
//
//            managersData.append(data);
//        }
//
//        return managersData.substring(0, managersData.length()-1) + "]";

    }
}


