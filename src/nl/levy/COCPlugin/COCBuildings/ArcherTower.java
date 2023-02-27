package nl.levy.COCPlugin.COCBuildings;

import com.sun.tools.javac.Main;
import nl.levy.COCPlugin.COC.COCAttack;
import nl.levy.COCPlugin.COCEntity.Entity;
import nl.levy.COCPlugin.COCItems.ArcherTowerDamage;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import nl.levy.COCPlugin.ItemBuilder.ArcherTowerData;
import nl.levy.COCPlugin.ItemBuilder.LevelItemBuilder;
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

    private Date lastShot;

    public ArcherTower(int x, int y) {
        super(x, y, LevelItemBuilder.getInstance().archerTowerData);
        lastShot = new Date();
    }

    private ArcherTowerDamage getData() {
        return LevelItemBuilder.getInstance().archerTowerData.damageValues.get(level - 1);
    }

    @Override
    public COCInventory createInventory() {
        return null;
    }

    @Override
    public int getStaging() {
        return 1;
    }


    @Override
    public void defenseUpdate(COCAttack attack) {
        var data = getData();
        if (lastShot.getTime() + data.fireRate * 1000L < new Date().getTime()) {
            Entity zombie = getNearest(attack);
            if (zombie == null) {
                return;
            }

            Location target = zombie.getLocation();
            var start = new Location(target.getWorld(), 3006, 110, 3006);

            System.out.println("Shoot");
            Arrow arrow = zombie.getLocation().getWorld().spawnArrow(start, new Vector(target.getBlockX() - start.getBlockX(), target.getBlockY() + 1 - start.getBlockY(), target.getBlockZ() - start.getBlockZ()), 1, 1);
            arrow.setGlowing(true);
            arrow.setGravity(false);

            new ArrowRunner(arrow, zombie).start();

            lastShot = new Date();
        }

    }

    private Entity getNearest(COCAttack attack) {
        var dist = Double.MAX_VALUE;
        Entity near = null;
        for (Entity entity : attack.entities) {
            var curDist = entity.getLocation().distance(new Location(entity.getLocation().getWorld(), this.location.x * 3 + 3000, 100, this.location.z * 3 + 3000));
            if (curDist < dist) {
                dist = curDist;
                near = entity;
            }
        }

        return near;
    }
}

class ArrowRunner extends ObjectRunner {
    private Arrow arrow;
    private Entity target;

    public ArrowRunner(Arrow arrow, Entity target) {
        this.arrow = arrow;
        this.target = target;
    }

    public void start() {
        super.start(() -> {
            var newstart = arrow.getLocation();
            var vel = new Vector(target.getLocation().getBlockX() - newstart.getBlockX(), target.getLocation().getBlockY() + 1 - newstart.getBlockY(), target.getLocation().getBlockZ() - newstart.getBlockZ()).normalize();

            arrow.setVelocity(vel);
            if (arrow.isInBlock() || arrow.getLocation().distance(target.getLocation()) < 2.5) {
                System.out.println("done");

                target.dealDamage(1);
                stop();
            }
        }, arrow::remove);
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
        }, 0, 1);
    }

    protected Runnable onStop;

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
