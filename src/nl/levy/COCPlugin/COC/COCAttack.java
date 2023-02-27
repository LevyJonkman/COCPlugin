package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCEntity.Entity;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.MainPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class COCAttack {

    private final COCManager defender;

    public final List<Entity> entities = new ArrayList<>();

    public COCAttack(COCManager defender) {
        this.defender = defender;


        /*MainPlugin.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(MainPlugin.plugin, () -> {
            for (Entity entity : entities) {
                entity.update();
            }
            defender.updateDefences();
        }, 1, 1);*/
    }

    public void spawnZombie(Location location) {
        var t = new Entity(location);
        t.setDestroy(() -> entities.remove(t));
        entities.add(t);
    }



}
