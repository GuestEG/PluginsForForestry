package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.Mod.Items.ItemMeta;
import net.minecraft.item.ItemStack;

public class ItemHammer extends ItemMeta {

    public ItemHammer(int par1) {
        super(new String[]{"PluginsForForestry".toLowerCase().concat(":smithing_hammer")}, par1);
        this.setCreativeTab(PfFAPI.tab);
        this.setContainerItem(this);
        this.createItemEntry(0, PfFTranslator.instance.translateKey("item_hammer"));
        PluginLR.stacks.put("hammer", new ItemStack(this));
    }
}
