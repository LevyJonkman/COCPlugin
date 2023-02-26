package nl.levy.COCPlugin;

import nl.levy.COCPlugin.COC.BuildHelper;
import nl.levy.COCPlugin.COCBuildings.ArcherTower;
import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCEntity.Entity;
import nl.levy.COCPlugin.COCItems.ArcherTowerDamage;
import nl.levy.COCPlugin.COCItems.COCMainManager;
import nl.levy.COCPlugin.ItemBuilder.ArcherTowerData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

record BlockChange(Location location, Material material) {
}

public class EventListener implements Listener {

    private final COCMainManager manager;

    public void disable() {
        for (BlockChange blockChange : change) {
            drawSection(blockChange.location(), blockChange.material());
        }

        change.clear();
    }

    public EventListener(COCMainManager manager) {
        this.manager = manager;
    }

    private final List<BlockChange> change = new ArrayList<>();
    private int size = 0;

    private void drawSection(Location location, Material mat) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                location.clone().add(i, 0, j).getBlock().setType(mat);
            }
        }
    }

    @EventHandler
    public void looking(PlayerMoveEvent event) {
        disable();

        if (event.getPlayer().getLocation().getBlockX() < 500 && event.getPlayer().getLocation().getBlockZ() < 500)
            return;

        var target = event.getPlayer().getTargetBlockExact(100);
        if (target != null && target.getLocation().getBlockY() == 100 && (target.getType() == Material.GREEN_CONCRETE || target.getType() == Material.LIME_CONCRETE)) {
            var loc = target.getLocation();
            var start = new Location(loc.getWorld(), (int) (loc.getBlockX() / 3.0) * 3, 100, (int) (loc.getBlockZ() / 3.0) * 3);

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    var location = start.clone().add(i * 3, 0, j * 3);
                    change.add(new BlockChange(location, location.getBlock().getType()));
                    var isOC = manager.getManager(event.getPlayer()).isOccupied(location.getBlockX() / 3, location.getBlockZ() / 3, 1);
                    var mat = (isOC) ? Material.RED_WOOL : Material.WHITE_WOOL;
                    drawSection(location, mat);
                }
            }
        }
    }

    @EventHandler
    public void tower(PlayerChatEvent e) {
        var list = new ArrayList<ArcherTowerDamage>();
        list.add(new ArcherTowerDamage(1, 1));
        if (e.getMessage().equalsIgnoreCase("tower")) {
        } else if (e.getMessage().startsWith("zombie")){
            var t = new Entity(e.getPlayer().getLocation());
            var tower = new ArcherTower(1, 1, new ArcherTowerData(3, new ArrayList<>(), 10, list));

            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MainPlugin.plugin, () -> {
                t.update();
                tower.defenseUpdate(t);
            }, 1, 1);
        }
    }

    @EventHandler
    public void asdf2(PlayerItemHeldEvent event) {
        var item = event.getPlayer().getInventory().getItem(event.getNewSlot());

        if (item == null) return;

        String name = switch (item.getType()) {
            case PURPLE_DYE -> {
                size = 3;
                yield "collector";
            }
            case YELLOW_DYE -> {
                size = 3;
                yield "goldmine";
            }
            case PURPLE_WOOL -> {
                size = 3;
                yield "elixirtank";
            }
            case YELLOW_WOOL -> {
                size = 3;
                yield "goldstrorage";
            }
            case HAY_BLOCK -> {
                size = 4;
                yield "townhall";
            }
            default -> {
                size = 0;
                yield null;
            }
        };

        if (name == null) return;

        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "title " + event.getPlayer().getName() + " actionbar {\"text\":\"" + name + "\", \"bold\":true, \"italic\":true, \"color\":\"red\"}");
    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR && change.size() > 0) {
            var item = event.getPlayer().getInventory().getItemInMainHand();
            var location = change.get(0).location();
            //from real pos to coc position
            var x = location.getBlockX() / 3;
            var z = location.getBlockZ() / 3;

            if (manager.getManager(event.getPlayer()).isOccupied(x, z, 3)) {
                event.getPlayer().sendMessage("Not enough space");
                return;
            }

            var world = location.getWorld();

            switch (item.getType()) {
                case PURPLE_DYE ->
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createCollector(x, z));
                case YELLOW_DYE ->
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createGoldMine(x, z));
                case PURPLE_WOOL ->
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createElixirTank(x, z));
                case YELLOW_WOOL ->
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createGoldStorage(x, z));
                case HAY_BLOCK ->
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createTownHall(x, z));
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void chatEvent(PlayerChatEvent event) {
        try {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("coc")) {
                var world = event.getPlayer().getWorld();
                if (args[1].equalsIgnoreCase("add")) {

                    if (manager.getManager(event.getPlayer()).isOccupied(Integer.parseInt(args[3]), Integer.parseInt(args[4]), 3)) {
                        event.getPlayer().sendMessage("Not enough space");
                        return;
                    }

                    switch (args[2]) {
                        case "collector" -> {
                            manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createCollector(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                        }
                        case "goldmine" -> {
                            manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createGoldMine(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                        }
                        case "goldstorage" -> {
                            manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createGoldStorage(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                        }
                        case "elixirtank" -> {
                            manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createElixirTank(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                        }
                    }
                } else if (args[1].equalsIgnoreCase("generatebase")) {
                    BuildHelper.GenerateBase(event.getPlayer(), Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                            Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                } else if (args[1].equalsIgnoreCase("generateplayer")) {
                    BuildHelper.GenerateBase(event.getPlayer(), 1000 + Integer.parseInt(args[2]), 1000 + Integer.parseInt(args[3]),
                            1000 + Integer.parseInt(args[4]), 1000 + Integer.parseInt(args[5]));
                } else if (args[1].equalsIgnoreCase("data")) {
                    if (manager.getManager(event.getPlayer()).COCItems.size() == 0) {
                        manager.buildingManager.build(world, manager.getManager(event.getPlayer()).createCollector(1000, 1000));
                    }

                    var item = manager.getManager(event.getPlayer()).getItem(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockZ());

                    if (item != null) {
                        manager.inventoryManager.OpenInventory(event.getPlayer(), item);
                    }
                } else if (args[1].equalsIgnoreCase("render")) {
                    for (int x = 0; x < 200; x++) {
                        for (int z = 0; z < 200; z++) {
                            for (int y = 0; y < 9; y++) {
                                world.getBlockAt(3000 + x, 101 + y, 3000 + z).setType(Material.AIR);
                            }
                        }
                    }
                    for (COCItem cocItem : manager.getManager(event.getPlayer()).COCItems) {
                        manager.buildingManager.build(world, cocItem);
                    }
                } else if (args[1].equalsIgnoreCase("display")) {
                    manager.scoreboardManager.addPlayerToDisplay(event.getPlayer());
                }
            }
        } catch (Exception value) {
            var a = value.toString();
            System.out.println(a);
        }
    }// -60, 90, -420

    @EventHandler
    public void join(PlayerJoinEvent event) {
        manager.scoreboardManager.addPlayerToDisplay(event.getPlayer());
    }

    @EventHandler
    public void clickItemInventory(InventoryClickEvent event) {
        if (manager.inventoryManager.clickedInventoryItem(event.getClickedInventory(), event.getSlot(), event.getWhoClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        manager.inventoryManager.closeInventory(event.getInventory());
    }
}
