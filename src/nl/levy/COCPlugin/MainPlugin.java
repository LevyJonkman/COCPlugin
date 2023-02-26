package nl.levy.COCPlugin;

import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class MainPlugin extends JavaPlugin {


    private COCMainManager manager;
    private EventListener listener;

    public static Plugin plugin;
    @Override
    public void onEnable() {
        super.onEnable();

        MainPlugin.plugin = this;

        this.getLogger().log(Level.INFO, "Hallo2!");

        manager = new COCMainManager(this);
        listener= new EventListener(manager);

        for (Map.Entry<UUID, COCManager> uuidcocManagerEntry : manager.managers.entrySet()) {
            for (COCItem cocItem : uuidcocManagerEntry.getValue().COCItems) {
                manager.buildingManager.build(Bukkit.getWorlds().get(0), cocItem);
            }
        }

        for (Player onlinePlayer : getServer().getOnlinePlayers()) {
            manager.scoreboardManager.addPlayerToDisplay(onlinePlayer);
        }


        getServer().getPluginManager().registerEvents(listener, this);

    }

    @Override
    public void onDisable() {
        listener.disable();

        var data = manager.getSaveData();

        try {
            Files.writeString(Path.of("C:\\Users\\Gebruiker\\Documents\\Server\\plugins\\data\\playerdata.json"), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onDisable();
    }
}
