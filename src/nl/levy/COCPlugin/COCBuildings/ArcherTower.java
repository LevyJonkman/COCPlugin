package nl.levy.COCPlugin.COCBuildings;

import com.sun.tools.javac.Main;
import nl.levy.COCPlugin.COCEntity.Entity;
import nl.levy.COCPlugin.COCItems.ArcherTowerDamage;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.ItemBuilder.ArcherTowerData;
import nl.levy.COCPlugin.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.util.ArrayList;
import java.util.Date;

public class ArcherTower extends COCDefenceItem {

    private final ArrayList<ArcherTowerDamage> archerTowerData;

    private Date lastShot;

    public ArcherTower(int x, int y, ArcherTowerData data) {
        super(x, y, data);
        archerTowerData = data.damageValues;
        lastShot = new Date();
    }

    private ArcherTowerDamage getData() {
        return archerTowerData.get(level - 1);
    }

    @Override
    public COCInventory createInventory() {
        return null;
    }

    @Override
    public int getStaging() {
        return 1;
    }

    public void defenseUpdate(Entity zombie) {
        var data = getData();
        if (lastShot.getTime() + data.fireRate * 1000L < new Date().getTime()) {

            var target = zombie.getLocation();
            var start = new Location(target.getWorld(), 3006, 110, 3006);

            System.out.println("Shoot");
            var arrow = zombie.getLocation().getWorld().spawnArrow(start, new Vector(target.getBlockX() - start.getBlockX(), target.getBlockY() + 1 - start.getBlockY(), target.getBlockZ() - start.getBlockZ()), 1, 1);
            arrow.setGlowing(true);
            arrow.setGravity(false);

            var a = new ObjectRunner();
            a.start(() -> {
                var newstart = arrow.getLocation();
                var vel = new Vector(target.getBlockX() - newstart.getBlockX(), target.getBlockY() + 1 - newstart.getBlockY(), target.getBlockZ() - newstart.getBlockZ()).normalize();

                arrow.setVelocity(vel);
                if (arrow.isInBlock() || arrow.getLocation().distance(target) < 1) {
                    System.out.println("done");

                    a.stop();
                }
            }, arrow::remove);

            lastShot = new Date();
        }
    }

    @Override
    public void defenseUpdate(World w, Player player) {

    }
}

class ObjectRunner {
    public int num;
    private int timer = 0;

    public void start(Runnable o) {
        num = MainPlugin.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(MainPlugin.plugin, () -> {
            timer++;
            if (timer > 100) {
                stop();
            } else {
                o.run();
            }
        }, 0, 20);
    }

    Runnable onStop;

    public void start(Runnable o, Runnable onStop) {
        this.onStop = onStop;
        start(o);
    }


    public void stop() {
        MainPlugin.plugin.getServer().getScheduler().cancelTask(num);
        if (onStop != null) {
            onStop.run();
        }
    }
}
