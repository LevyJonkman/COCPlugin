package nl.levy.COCPlugin;

import nl.levy.COCPlugin.COC.BuildHelper;
import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.YELLOW_WOOL;

class BlockChange {
    public final Location location;
    public final Material material;

    BlockChange(Location location, Material material) {
        this.location = location;
        this.material = material;
    }
}

public class EventListener implements Listener {

    private final COCManager manager;

    public void disable() {
        if (change != null) {
            drawSection(change.location, change.material);
        }
        change = null;
    }


    public EventListener(COCManager manager) {
        this.manager = manager;
    }

    private BlockChange change = null;

    private void drawSection(Location location, Material mat) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                location.clone().add(i, 0, j).getBlock().setType(mat);
            }
        }
    }

    @EventHandler
    public void looking(PlayerMoveEvent event) {
        if (change != null) {
            drawSection(change.location, change.material);
        }

        change = null;

        var target = event.getPlayer().getTargetBlockExact(100);
        if (target != null && target.getLocation().getBlockY() == 100 && (target.getType() == Material.GREEN_CONCRETE || target.getType() == Material.LIME_CONCRETE)) {
            var loc = target.getLocation();
            var start = new Location(loc.getWorld(), (int) (loc.getBlockX() / 3.0) * 3, 100, (int) (loc.getBlockZ() / 3.0) * 3);
            change = new BlockChange(start, start.getBlock().getType());

            drawSection(start, Material.WHITE_WOOL);
        }
    }

    @EventHandler
    public void bar(PlayerChangedMainHandEvent event) {
        var item = event.getPlayer().getInventory().getItemInMainHand();
        System.out.println(item);
        System.out.println(item.getType());

        String name = null;
        switch (item.getType()) {
            case PURPLE_DYE -> name = "collector";
            case YELLOW_DYE -> name = "goldmine";
            case PURPLE_WOOL -> name = "elixirtank";
            case YELLOW_WOOL -> name = "goldstrorage";
        }

        if (name!=null) {
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"title "+ event.getPlayer().getName() +" actionbar {\"text\":\""+name+"\", \"bold\":true, \"italic\":true, \"color\":\"red\"}");
        }

    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        System.out.println(event.getAction());
        System.out.println(event.getPlayer().getItemOnCursor());
        System.out.println(event.getPlayer().getItemOnCursor().getType());
        if (event.getAction() == Action.RIGHT_CLICK_AIR && change != null) {
            var item = event.getPlayer().getInventory().getItemInMainHand();

            if (!manager.checkSpace(change.location.getBlockX(), change.location.getBlockZ(), 3)) {
                event.getPlayer().sendMessage("Not enough space");
                return;
            }

            switch (item.getType()) {
                case PURPLE_DYE ->
                        manager.build(change.location.getWorld(), manager.createCollector(change.location.getBlockX() / 3, change.location.getBlockZ() / 3));
                case YELLOW_DYE ->
                        manager.build(change.location.getWorld(), manager.createGoldMine(change.location.getBlockX() / 3, change.location.getBlockZ() / 3));
                case PURPLE_WOOL ->
                        manager.build(change.location.getWorld(), manager.createElixirTank(change.location.getBlockX() / 3, change.location.getBlockZ() / 3));
                case YELLOW_WOOL ->
                        manager.build(change.location.getWorld(), manager.createGoldStorage(change.location.getBlockX() / 3, change.location.getBlockZ() / 3));
            }
        }
    }

    @EventHandler
    public void chatEvent(PlayerChatEvent event) {
        try {
            String[] args = event.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("coc")) {
                var world = event.getPlayer().getWorld();
                System.out.println(args[1]);
                System.out.println("add");
                if (args[1].equalsIgnoreCase("add")) {

                    if (!manager.checkSpace(Integer.parseInt(args[3]), Integer.parseInt(args[4]), 3)) {
                        event.getPlayer().sendMessage("Not enough space");
                        return;
                    }

                    switch (args[2]) {
                        case "collector" -> {
                            manager.build(world, manager.createCollector(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                            System.out.println("ADD collector");
                        }
                        case "goldmine" -> {
                            manager.build(world, manager.createGoldMine(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                            System.out.println("ADD goldmine");
                        }
                        case "goldstorage" -> {
                            System.out.println("goldstorage");
                            manager.build(world, manager.createGoldStorage(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                            System.out.println("ADD goldstorage");
                        }
                        case "elixirtank" -> {
                            manager.build(world, manager.createElixirTank(1000 + Integer.parseInt(args[3]), 1000 + Integer.parseInt(args[4])));
                            System.out.println("ADD elixirtank");
                        }
                    }
                } else if (args[1].equalsIgnoreCase("generatebase")) {
                    BuildHelper.GenerateBase(event.getPlayer(), Integer.parseInt(args[2]), Integer.parseInt(args[3]),
                            Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                } else if (args[1].equalsIgnoreCase("generateplayer")) {
                    BuildHelper.GenerateBase(event.getPlayer(), 1000 + Integer.parseInt(args[2]), 1000 + Integer.parseInt(args[3]),
                            1000 + Integer.parseInt(args[4]), 1000 + Integer.parseInt(args[5]));
                } else if (args[1].equalsIgnoreCase("data")) {
                    if (manager.COCItems.size() == 0) {
                        manager.createCollector(1000, 1000);
                    }

                    var item = manager.getItem(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockZ());
                    System.out.println("tryopen");
                    if (item != null) {
                        System.out.println("open" + item);
                        manager.OpenInventory(event.getPlayer(), item);
                    }
                } else if (args[1].equalsIgnoreCase("render")) {
                    for (COCItem cocItem : manager.COCItems) {
                        System.out.println("render " + cocItem);
                        manager.build(world, cocItem);
                    }
                }
            }
        } catch (Exception value) {
            var a = value.toString();
            System.out.println(a);
        }
    }// -60, 90, -420

    @EventHandler
    public void clickItemInventory(InventoryClickEvent event) {
        if (manager.clickedInventoryItem(event.getClickedInventory(), event.getSlot(), event.getWhoClicked())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void closeInventory(InventoryCloseEvent event) {
        manager.closeInventory(event.getInventory());
    }
}
