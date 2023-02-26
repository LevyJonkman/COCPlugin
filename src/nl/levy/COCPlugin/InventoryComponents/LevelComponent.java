package nl.levy.COCPlugin.InventoryComponents;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.COCManager.COCManager;
import nl.levy.COCPlugin.Inventories.COCInventory;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class LevelComponent implements IInventoryComponent{
    private final COCLevelItem item;
    private final int startRow;

    public LevelComponent(COCLevelItem item) {
        this(item, 2);
    }

    public LevelComponent(COCLevelItem item, int startRow) {
        this.item = item;
        this.startRow = startRow;
    }

    @Override
    public void start(Inventory inventory) {

    }

    @Override
    public void update(Inventory inventory) {
        InventoryHelper.addItem(inventory, startRow, 8, InventoryHelper.createItem(Material.IRON_BLOCK, "Level " + item.level));
        drawUpgradeLevel(inventory, startRow+1, 8);
        InventoryHelper.addItem(inventory, startRow+2, 8, InventoryHelper.createItem(Material.IRON_BLOCK, "Upgrade cost: " + getUpgradeCosts()));
    }

    private String getUpgradeCosts() {
        if (item.getNextLevelPrice().elixir != 0){
            return item.getNextLevelPrice().elixir + " Elixir";
        }
        if (item.getNextLevelPrice().gold != 0){
            return item.getNextLevelPrice().gold + " GOld";
        }
        if (item.getNextLevelPrice().darkElixir != 0){
            return item.getNextLevelPrice().darkElixir + " DarkElixir";
        }

        return "Gratos";
    }


    private void drawUpgradeLevel(Inventory inventory, int row, int colum) {
        if (item.notMaxLevel()) {
            InventoryHelper.addItem(inventory, row, colum, InventoryHelper.createItem(Material.GOLD_BLOCK, "Upgrade to level " + (item.level + 1)));
        } else {
            InventoryHelper.addItem(inventory, row, colum, InventoryHelper.createItem(Material.GRAY_WOOL, "Max level"));
        }
    }

    @Override
    public void clicked(Inventory inventory, COCManager manager, int slot, HumanEntity player) {
        if (COCInventory.isClicked(3, 7, slot)) {
            if (item.notMaxLevel()) {
                if (manager.hasResources(item.getNextLevelPrice())) {
                    manager.removeResources(item.getNextLevelPrice());
                    item.upgrade();
                    update(inventory);
                } else
                    player.sendMessage("Not enough resources");
            } else
                player.sendMessage("Already max level");
        }
    }
}
