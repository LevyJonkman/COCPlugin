package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCItems.COCLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BuildHelper {
    public static void clone(World world, COCLocation loc, COCLocation loc2, int size) {
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                clone(world, new COCLocation(loc.x + x, loc.z + z), new COCLocation(loc2.x + x, loc2.z + z));
            }
        }
    }

    public static void clone(World world, COCLocation loc, COCLocation loc2) {
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                for (int y = 0; y < 9; y++) {
                    clone(world, loc.x * 3 + x, 101 + y, loc.z * 3 + z, loc2.x * 3 + x, 101 + y, loc2.z * 3 + z);
                }
            }
        }
    }

    public static void clone(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        if (world == null) return;
        world.getBlockAt(x2, y2, z2).setType(world.getBlockAt(x1, y1, z1).getType());
    }

    public static void GenerateBase(Player player, int startX, int startY, int width, int height) {
        boolean dark = true;
        for (int i = startX; i < width; i++) {
            for (int j = startY; j < height; j++) {
                dark = !dark;
                FillBlock(player.getWorld(), i * 3, j * 3, dark ? Material.GREEN_CONCRETE : Material.LIME_CONCRETE);
            }
            dark = !dark;
        }
    }

    public static void FillBlock(World world, int startX, int startY, Material material) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int y = 100;
                world.getBlockAt(startX + i, y, startY + j).getLocation().getBlock().setType(material);
            }
        }
    }
}
