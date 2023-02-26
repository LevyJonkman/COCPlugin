package nl.levy.COCPlugin.COC;

import nl.levy.COCPlugin.COCBuildings.COCItem;
import nl.levy.COCPlugin.COCBuildings.COCLevelItem;
import nl.levy.COCPlugin.Components.IComponent;

import java.util.ArrayList;
import java.util.List;

public class FinderHelper {


    public static <T extends COCItem> List<T> getItems(List<COCItem> items, Class<T> clazz) {
        var list = new ArrayList<T>();
        for (COCItem cocItem : items) {
            if (clazz.isInstance(cocItem)) {
                list.add(clazz.cast(cocItem));
            }
        }

        return list;
    }

    public static <T2 extends IComponent, T extends COCLevelItem> List<T2> getItemsComponents(List<COCItem> items, Class<T> tClassItem, Class<T2> tClassComponent) {
        var list = new ArrayList<T2>();

        var newItems = getItems(items, tClassItem);

        for (T item : newItems) {
            list.addAll(item.find(tClassComponent));
        }

        return list;
    }
}
