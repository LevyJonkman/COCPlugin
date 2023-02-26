package nl.levy.COCPlugin.COCEntity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private final List<Location> trace;
    private int index;
    private final Zombie zombie;

    public Entity(Location location) {
        index = 0;
        zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        trace = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            trace.add(new Location(location.getWorld(), 3030, 101, 3030));
            trace.add(new Location(location.getWorld(), 3050, 101, 3030));
            trace.add(new Location(location.getWorld(), 3050, 101, 3050));
            trace.add(new Location(location.getWorld(), 3030, 101, 3050));
        }
    }

    public Location getLocation() {
        return zombie.getLocation();
    }

    public void update() {
        if (zombie.getLocation().distance(trace.get(index)) < 0.1f) {
            index++;
        }

        if (index == trace.size()) {
            return;
        }

        zombie.setVelocity(new Vector(trace.get(index).getX() - zombie.getLocation().getX(), 0, trace.get(index).getZ() - zombie.getLocation().getZ()).normalize().multiply(.1));
    }

}
