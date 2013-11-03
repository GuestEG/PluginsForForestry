package denoflionsx.PluginsforForestry.Config;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import denoflionsx.PluginsforForestry.Utils.PfFLib;
import denoflionsx.denLib.Lib.denLib;
import java.io.File;
import net.minecraft.item.Item;

public class ItemNameBridge {

    /*
     * This class is a bridge for 1.7 item names. All items will be referenced by a string tag, so the switch to 1.7 is easier.
     */
    private static final int startID = 4344;
    private static BiMap<String, Integer> map = HashBiMap.create();
    private static File saveFile;

    public static void setupBridge(File file) {
        saveFile = file;
        if (saveFile.exists()) {
            map = denLib.FileUtils.readBiMapFromFile(saveFile);
        } else {
            map.put("base", startID);
        }
    }

    public static Item registerItem(String name, Class clazz) {
        int id;
        if (map.containsKey(name)) {
            id = map.get(name);
            int id2 = PfFTuning.config.get("item", name + "_ItemID", id).getInt();
            if (id != id2) {
                map.put(name, id2);
            }
        } else {
            map.put(name, PfFTuning.config.get("item", name + "_ItemID", PfFLib.MathUtils.getLastID(map.inverse())).getInt());
            id = map.get(name);
        }
        Item i = null;
        try {
            i = (Item) clazz.getConstructor(new Class[]{int.class}).newInstance(new Object[]{id});
        } catch (Throwable t) {
            t.printStackTrace();
        }
        denLib.FileUtils.saveBiMapToFile(map, saveFile);
        return i;
    }
}
