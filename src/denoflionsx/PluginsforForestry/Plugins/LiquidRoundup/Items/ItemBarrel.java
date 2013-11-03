package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.NewConfig.ConfigField;
import net.minecraft.item.ItemStack;

public class ItemBarrel extends ItemContainerBase {

    @ConfigField(category = "barrel")
    public static int barrel_capacity = 10000;

    public ItemBarrel(int itemID) {
        super(itemID, barrel_capacity, "item.pff.barrel.name", "barrel", "barrel");
        PluginLR.stacks.put("barrel", new ItemStack(this));
    }
}
