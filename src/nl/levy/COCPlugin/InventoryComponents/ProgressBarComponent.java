package nl.levy.COCPlugin.InventoryComponents;

import nl.levy.COCPlugin.COC.InventoryHelper;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;

public class ProgressBarComponent implements IInventoryComponent {

    public interface ProgressBarComponentFunction {
        double getCurrent();
        int getTotal();
    }

    private final ProgressBarComponentFunction function;
    private final Material material;
    public int row;
    public ProgressBarComponent(ProgressBarComponentFunction function, Material material, int row) {
        this.function = function;
        this.material = material;
        this.row = row;
    }

    public ProgressBarComponent(ProgressBarComponentFunction function, Material material) {
        this(function, material, 5);
    }


    @Override
    public void start(Inventory inventory) {
    }

    @Override
    public void update(Inventory inventory) {
        double current = function.getCurrent();
        var total = function.getTotal();
        int fillPercentage = (int) (current / total * 7 * 64);
        var title = ((int) current) + "/" + total;

        int count = 0;
        for (var i = 0; i < 7; i++) {
            if (fillPercentage > count) {
                if (fillPercentage > count + 64) {
                    InventoryHelper.addItem(inventory, row, 2 + i, InventoryHelper.createItem(material, title, 64));
                    count += 64;
                } else {
                    var dif = fillPercentage - count;
                    InventoryHelper.addItem(inventory, row, 2 + i, InventoryHelper.createItem(material, title, dif));
                    count += dif;
                }
            } else {
                InventoryHelper.addItem(inventory, row, 2 + i, InventoryHelper.createItem(Material.WHITE_WOOL, title, 64));
            }
        }
    }

    @Override
    public void clicked(Inventory inventory, COCManager manager, int slot, HumanEntity player) {

    }
}
