package nl.levy.COCPlugin.COCEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Runnable destroy;
    private final List<Location> trace;
    private int index;
    private final Zombie zombie;
    public int health = 10;

    public void setDestroy(Runnable destroy){
        this.destroy=destroy;
    }

    public Entity(Location location) {
        index = 0;
        zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.setCustomNameVisible(true);
        setName();
        trace = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            trace.add(new Location(location.getWorld(), 3030, 0, 3030));
            trace.add(new Location(location.getWorld(), 3050, 0, 3030));
            trace.add(new Location(location.getWorld(), 3050, 0, 3050));
            trace.add(new Location(location.getWorld(), 3030, 0, 3050));
        }
    }

    private void setName() {
        zombie.setCustomName("Health " + health);
    }

    public Location getLocation() {
        return zombie.getLocation();
    }

    public void update() {
        setName();
        if (zombie.getLocation().subtract(0,zombie.getLocation().getBlockY(),0).distance(trace.get(index)) < 0.1f) {
            index++;
        }

        if (index == trace.size()) {
            return;
        }

        zombie.setVelocity(new Vector(trace.get(index).getX() - zombie.getLocation().getX(), 0, trace.get(index).getZ() - zombie.getLocation().getZ()).normalize().multiply(.1));
    }

    public void dealDamage(int i) {
        health -= i;
        if (health <= 0) {
            zombie.remove();
            if (destroy != null) {
                destroy.run();
            }
        }
    }
}
