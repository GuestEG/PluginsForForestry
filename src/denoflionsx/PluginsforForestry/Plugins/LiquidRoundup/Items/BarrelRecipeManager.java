package denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.Items;

import denoflionsx.PluginsforForestry.Core.PfF;
import denoflionsx.PluginsforForestry.Plugins.LiquidRoundup.PluginLR;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class BarrelRecipeManager {

    public final void hammer() {
        PfF.Proxy.registerOreRecipe(PluginLR.stacks.get("hammer"), new Object[]{"XiX", "III", "XSX", Character.valueOf('i'), "dyeBlack", Character.valueOf('I'), Item.ingotIron, Character.valueOf('S'), "stickWood"});
    }

    public final void rings() {
        PfF.Proxy.registerRecipe(PluginLR.stacks.get("rings"), new Object[]{"XHX", "III", "XXX", Character.valueOf('H'), PluginLR.stacks.get("hammer"), Character.valueOf('I'), Item.ingotIron});
        PfF.Proxy.registerOreRecipe(PluginLR.stacks.get("ringsx"), new Object[]{"XHX", "III", "XXX", Character.valueOf('H'), PluginLR.stacks.get("hammer"), Character.valueOf('I'), "ingotSteel"});
    }

    public final void barrel() {
        PfF.Proxy.registerOreRecipe(PluginLR.stacks.get("barrel"), new Object[]{"PRP", "PGP", "PRP", Character.valueOf('P'), "plankWood", Character.valueOf('R'), PluginLR.stacks.get("rings"), Character.valueOf('G'), Block.glass});
    }
}
