package nl.levy.COCPlugin.Inventories;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import org.bukkit.Material;

public abstract class COCProgressBarInventory extends COCLevelInventory {
    private final Material material;

    public COCProgressBarInventory(COCLevelItem item, String name, Material material) {
        super(item, name);
        this.material = material;
    }

    protected abstract int getTotal();

    protected abstract int getCurrent();

    @Override
    public void update() {
        super.update();

        double current = getCurrent();
        var total = getTotal();
        int fillPercentage = (int) (current / total * 7 * 64);
        var title = ((int) current) + "/" + total;

        int count = 0;
        for (var i = 0; i < 7; i++) {
            if (fillPercentage > count) {
                if (fillPercentage > count + 64) {
                    InventoryHelper.addItem(inventory, 5, 2 + i, InventoryHelper.createItem(material, title, 64));
                    count += 64;
                } else {
                    var dif = fillPercentage - count;
                    InventoryHelper.addItem(inventory, 5, 2 + i, InventoryHelper.createItem(material, title, dif));
                    count += dif;
                }
            } else {
                InventoryHelper.addItem(inventory, 5, 2 + i,
                        InventoryHelper.createItem(Material.WHITE_WOOL, title, 64));
            }
        }
    }
}
