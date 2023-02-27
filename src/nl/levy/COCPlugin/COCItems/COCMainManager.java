package nl.levy.COCPlugin.COCItems;

import nl.levy.COCPlugin.COC.COCScoreboardManager;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.COCManager.InventoryManager;
import nl.levy.COCPlugin.Save.RedisClient;
import nl.levy.COCPlugin.Save.SaveCOCItem;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class COCMainManager {

    //public final HashMap<UUID, COCManager> managers;
    public final InventoryManager inventoryManager;
    public final COCScoreboardManager scoreboardManager;

    public final RedisClient client = new RedisClient();

    private COCManager cach;

    public COCManager getManager(HumanEntity player) {
        if (cach != null && cach.mcPlayerUUID == player.getUniqueId()) {
            return cach;
        }

        cach = client.getManager(player.getUniqueId());
        return cach;

//        var manager = managers.get(player.getUniqueId());
//
//        if (manager == null) {
//            manager = new COCManager(levelItemBuilder);
//            managers.put(player.getUniqueId(), manager);
//        }
//
//        return manager;
    }

    public COCMainManager(Plugin plugin) {

        inventoryManager = new InventoryManager(this);
        scoreboardManager = new COCScoreboardManager(this);

       /* plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            inventoryManager.update();
            scoreboardManager.update();
        }, 0, 20);*/
    }


//    public void getSaveData() {
//        var list = new HashMap<String, SaveManager>();
//
//        for (var key : managers.keySet()) {
//            list.put(key.toString(), SaveManager.from(managers.get(key)));
//        }
//
//        var a = new Gson().toJson(list);
//
//        client.set();
//    }


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
//
//    }
}


