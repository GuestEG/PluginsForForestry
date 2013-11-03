package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import denoflionsx.PluginsforForestry.API.PfFAPI;
import denoflionsx.PluginsforForestry.Lang.PfFTranslator;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import denoflionsx.denLib.Mod.Items.ItemMeta;
import denoflionsx.denLib.NewConfig.ConfigField;
import net.minecraft.item.ItemStack;

public class ItemRings extends ItemMeta {

    @ConfigField(category = "rings")
    public static int rings_amountPerCraft = 16;
    @ConfigField(category = "rings")
    public static int rings_steelBonusMultiplier = 2;

    public ItemRings(int par1) {
        super(new String[]{"@NAME@".toLowerCase().concat(":iron_hoop")}, par1);
        this.setCreativeTab(PfFAPI.tab);
        this.createItemEntry(0, PfFTranslator.instance.translateKey("item.pff.rings.name"));
        PluginLR.stacks.put("rings", new ItemStack(this, rings_amountPerCraft, 0));
        PluginLR.stacks.put("ringsx", new ItemStack(this, rings_amountPerCraft * rings_steelBonusMultiplier, 0));
    }
}
