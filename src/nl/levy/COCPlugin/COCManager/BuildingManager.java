package nl.levy.COCPlugin.COCManager;

import nl.levy.COCPlugin.COC.BuildHelper;
import nl.levy.COCPlugin.COC.ItemBuilder;
import nl.levy.COCPlugin.COCBuildings.*;
import nl.levy.COCPlugin.COCItems.COCLocation;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class BuildingManager extends BaseManager {

    public List<List<ItemBuilder>> collectors;
    public List<List<ItemBuilder>> goldMines;
    public List<List<ItemBuilder>> goldStorages;
    public List<List<ItemBuilder>> elixirTanks;

    public BuildingManager() {
        collectors = createSelect(5, 3, 0, 3);
        goldMines = createSelect(5, 3, 3, 3);
        goldStorages = createSelect(5, 3, 6, 3);
        elixirTanks = createSelect(5, 3, 9, 3);
    }

    private List<List<ItemBuilder>> createSelect(int levels, int size, int z, int stages) {
        var list = new ArrayList<List<ItemBuilder>>();
        for (int i = 0; i < levels; i++) {
            list.add(fromStaging(i + 1, size, i * size, z, stages));
        }
        return list;
    }

    private List<ItemBuilder> fromStaging(int level, int size, int x, int z, int stages) {
        var list = new ArrayList<ItemBuilder>();
        for (int i = 0; i < stages; i++) {
            list.add(new ItemBuilder(level, size, new COCLocation(x, z, i * 3)));
        }
        return list;
    }

    public void build(World world, COCItem item2) {
        System.out.println("Build:" + item2);
        if (item2 instanceof Collector item) {
            buildItem(world, item);
        } else if (item2 instanceof GoldMine item) {
            buildItem(world, item);
        } else if (item2 instanceof GoldStorage item) {
            buildItem(world, item);
        } else if (item2 instanceof ElixirTank item) {
            buildItem(world, item);
        }
    }

    private void buildItem(World world, Collector item) {
        buildItem(world, item, collectors);
    }

    private void buildItem(World world, GoldMine item) {
        buildItem(world, item, goldMines);
    }

    private void buildItem(World world, GoldStorage item) {
        buildItem(world, item, goldStorages);
    }

    private void buildItem(World world, ElixirTank item) {
        buildItem(world, item, elixirTanks);
    }

    private void buildItem(World world, COCLevelItem item, List<List<ItemBuilder>> collection) {
        buildItem(world, collection.get(item.level - 1).get(item.getStaging() - 1), item.location);
    }

    private void buildItem(World world, ItemBuilder item, COCLocation location) {
        BuildHelper.clone(world, item.location, location, item.size);
    }
}

