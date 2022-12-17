package nl.levy.COCPlugin;

import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@SuppressWarnings("unused")
public class MainPlugin extends JavaPlugin {

    private final COCManager manager = new COCManager();
    EventListener listener = new EventListener(manager);

    @Override
    public void onEnable() {
        super.onEnable();

        this.getLogger().log(Level.INFO, "Hallo2!");

        getServer().getPluginManager().registerEvents(listener, this);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, manager.runnable, 0, 20);
    }

    @Override
    public void onDisable() {
        listener.disable();

        super.onDisable();
    }
}
